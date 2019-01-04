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
package org.openhab.binding.bmtemperature;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link BmTemperatureBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author bondarmih - Initial contribution
 */
@NonNullByDefault
public class BmTemperatureBindingConstants {

    private static final String BINDING_ID = "bmtemperature";

    // List of all Thing Type UIDs
    public static final ThingTypeUID THING_TYPE_SAMPLE = new ThingTypeUID(BINDING_ID, "sample");

    // List of all Channel ids
    public static final String ACTUAL_CHANNEL = "actual";
    public static final String SETPOINT_CHANNEL = "setpoint";
    public static final String AUTO_CHANNEL = "auto";
    public static final String ENABLED_CHANNEL = "enabled";
    public static final String HEATER_CHANNEL = "heater";
}