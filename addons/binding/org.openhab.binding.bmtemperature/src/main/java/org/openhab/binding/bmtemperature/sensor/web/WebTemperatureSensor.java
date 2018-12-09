package org.openhab.binding.bmtemperature.sensor.web;

import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.bmtemperature.sensor.TemperatureSensor;

import java.util.concurrent.CompletableFuture;

public class WebTemperatureSensor implements TemperatureSensor {

    private final HttpClient httpClient;
    private final String address;

    public WebTemperatureSensor(HttpClient httpClient, String address) {
        this.httpClient = httpClient;
        this.address = address;
    }

    @Override
    public CompletableFuture<Double> getValue() {
        return null;
    }
}
