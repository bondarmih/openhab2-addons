package org.openhab.binding.bmtemperature.controller;

public interface TemperatureController {

    void setSetpoint(Double setpoint);

    void setAuto(boolean auto);

    void setEnabled(boolean enabled);

    void dispose();
}
