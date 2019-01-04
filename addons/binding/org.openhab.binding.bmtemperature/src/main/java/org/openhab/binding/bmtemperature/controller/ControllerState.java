package org.openhab.binding.bmtemperature.controller;

public class ControllerState {
    private double actual;
    private boolean heater;

    public ControllerState(double actual, boolean heater) {
        this.actual = actual;
        this.heater = heater;
    }

    public double getActual() {
        return actual;
    }

    public boolean isHeater() {
        return heater;
    }
}
