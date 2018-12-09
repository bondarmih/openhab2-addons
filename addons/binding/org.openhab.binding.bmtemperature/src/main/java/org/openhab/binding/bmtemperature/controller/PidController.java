package org.openhab.binding.bmtemperature.controller;

import org.openhab.binding.bmtemperature.sensor.TemperatureSensor;

public class PidController implements TemperatureController {

    private MiniPID pid = new MiniPID(1,0.005, 0.1);
    private TemperatureControllerEventHandler handler;
    private TemperatureSensor sensor;

    public PidController(TemperatureControllerEventHandler handler, TemperatureSensor sensor) {
        this.handler = handler;
        this.sensor = sensor;

        this.initializeSensorRequests();
    }


}
