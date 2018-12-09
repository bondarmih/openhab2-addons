package org.openhab.binding.bmtemperature.controller;

import org.openhab.binding.bmtemperature.sensor.TemperatureSensor;

public class TemperatureControllerFactory {

    public TemperatureController getController(TemperatureControllerEventHandler handler, TemperatureSensor sensor) {
        return new PidController(handler, sensor);
    }
}
