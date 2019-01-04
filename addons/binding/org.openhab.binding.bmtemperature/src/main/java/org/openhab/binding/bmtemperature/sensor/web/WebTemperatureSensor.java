package org.openhab.binding.bmtemperature.sensor.web;

import org.eclipse.jetty.client.HttpClient;
import org.openhab.binding.bmtemperature.sensor.TemperatureSensor;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class WebTemperatureSensor implements TemperatureSensor {

    private final HttpClient httpClient;
    private final String address;

    public WebTemperatureSensor(HttpClient httpClient, String address) {
        this.httpClient = httpClient;
        this.address = address;
    }

    @Override
    public CompletableFuture<Double> getValue() {
        Supplier<Double> supplier = () -> {
            try {
                String content = httpClient.GET(address).getContentAsString();
                return Double.parseDouble(content);
            } catch (NumberFormatException e) {
                return 0d;
            } catch (Exception e) {
                return +100d;
            }
        };
        return CompletableFuture.supplyAsync(supplier);
    }
}
