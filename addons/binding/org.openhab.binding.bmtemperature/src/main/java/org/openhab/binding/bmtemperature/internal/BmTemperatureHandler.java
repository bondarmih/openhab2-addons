/**
 * Copyright (c) 2014,2018 by the respective copyright holders.
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.openhab.binding.bmtemperature.internal;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.openhab.binding.bmtemperature.controller.ControllerState;
import org.openhab.binding.bmtemperature.controller.TemperatureController;
import org.openhab.binding.bmtemperature.controller.TemperatureControllerEventHandler;
import org.openhab.binding.bmtemperature.controller.TemperatureControllerFactory;
import org.openhab.binding.bmtemperature.sensor.TemperatureSensor;
import org.openhab.binding.bmtemperature.sensor.TemperatureSensorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.openhab.binding.bmtemperature.BmTemperatureBindingConstants.*;

/**
 * The {@link BmTemperatureHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author bondarmih - Initial contribution
 */
@NonNullByDefault
public class BmTemperatureHandler extends BaseThingHandler implements TemperatureControllerEventHandler {

    private final Logger logger = LoggerFactory.getLogger(BmTemperatureHandler.class);
    private HttpClient httpClient;

    @Nullable
    private BmTemperatureConfiguration config;
    private TemperatureController controller;
    private TemperatureSensor sensor;

    public BmTemperatureHandler(Thing thing) {
        super(thing);
    }

    public BmTemperatureHandler(Thing thing, HttpClient httpClient) {
        super(thing);
        this.httpClient = httpClient;

        TemperatureSensorFactory sensorFactory = new TemperatureSensorFactory();
        TemperatureControllerFactory controllerFactory = new TemperatureControllerFactory();

        sensor = sensorFactory.getSensor(httpClient, thing.getConfiguration().get("ipAddress").toString());
        controller = controllerFactory.getController(this, sensor);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        try {
            if (SETPOINT_CHANNEL.equals(channelUID.getId())) {
                if (command instanceof DecimalType) {
                    controller.setSetpoint(((DecimalType) command).doubleValue());
                }
            }
            if (AUTO_CHANNEL.equals(channelUID.getId())) {
                if (command instanceof OnOffType) {
                    controller.setAuto(command.equals(OnOffType.ON));
                }
            }
            if (ENABLED_CHANNEL.equals(channelUID.getId())) {
                if (command instanceof OnOffType) {
                    controller.setEnabled(command.equals(OnOffType.ON));
                }
            }
        } catch (Exception ex) {
            updateStatus(ThingStatus.OFFLINE);
        }
    }

    @Override
    public void initialize() {
        config = getConfigAs(BmTemperatureConfiguration.class);
    }

    @Override
    public void handleControllerState(ControllerState state) {
        updateState(ACTUAL_CHANNEL, new DecimalType(state.getActual()));
        updateState(HEATER_CHANNEL, state.isHeater() ? OnOffType.ON : OnOffType.OFF);
    }
}
