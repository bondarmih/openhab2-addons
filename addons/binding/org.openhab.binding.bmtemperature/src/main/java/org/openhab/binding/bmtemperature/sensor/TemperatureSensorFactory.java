package org.openhab.binding.bmtemperature.sensor;

import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.bmtemperature.sensor.web.WebTemperatureSensor;

public class TemperatureSensorFactory {

    public TemperatureSensor getSensor(HttpClient httpClient, String address) {
        return new WebTemperatureSensor(httpClient, address);
    }
}
