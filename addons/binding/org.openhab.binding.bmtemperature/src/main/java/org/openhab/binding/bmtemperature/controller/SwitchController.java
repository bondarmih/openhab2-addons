package org.openhab.binding.bmtemperature.controller;

import org.openhab.binding.bmtemperature.sensor.TemperatureSensor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.DoublePredicate;

public class SwitchController implements TemperatureController {

    private final TemperatureControllerEventHandler handler;
    private final TemperatureSensor sensor;
    private final ScheduledFuture<?> scheduledFuture;

    private AtomicReference<Double> setpoint = new AtomicReference<>();
    private AtomicReference<Double> actual = new AtomicReference<>();

    private DoublePredicate heaterControlStrategy;
    private boolean auto;

    private DoublePredicate setByActual = value -> this.setpoint.get().compareTo(value) > 0;
    private DoublePredicate alwaysOff = any -> false;
    private DoublePredicate alwaysOn = any -> true;


    public SwitchController(TemperatureControllerEventHandler handler, TemperatureSensor sensor) {
        this.handler = handler;
        this.sensor = sensor;

        Runnable runnable = () -> {
            sensor.getValue().thenAccept( value -> {
                this.actual.set(value);
                handler.handleControllerState(new ControllerState(value, this.heaterControlStrategy.test(value)));
            });
        };
        scheduledFuture = Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void setSetpoint(Double setpoint) {
        this.setpoint.set(setpoint);
    }

    @Override
    public void dispose() {
        this.scheduledFuture.cancel(false);
    }

    @Override
    public void setAuto(boolean auto) {
        this.auto = auto;
        this.heaterControlStrategy = auto ? this.setByActual : this.alwaysOff;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (auto) {
            return;
        }
        this.heaterControlStrategy = enabled ? this.alwaysOn : this.alwaysOff;
    }
}
