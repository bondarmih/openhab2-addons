package org.openhab.binding.bmtemperature.sensor;

import java.util.concurrent.CompletableFuture;

public interface TemperatureSensor {

    CompletableFuture<Double> getValue();
}
