/*
 * Fluffy Meow - Torrent RSS generator for TV series
 * Copyright (C) 2015 Victor Denisov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.plukh.fluffymeow.aws;

import org.apache.commons.lang3.StringUtils;

public class InstanceInfoProviderFactory {
    public static final String PROVIDER_CLASS_PROPERTY = "org.plukh.fluffymeow.aws.instanceInfoProviderClass";
    private static final Class<? extends InstanceInfoProvider> DEFAULT_PROVIDER_CLASS = AWSInstanceInfoProviderImpl.class;

    public static InstanceInfoProvider getInstance() {
        String clazz = System.getProperty(PROVIDER_CLASS_PROPERTY);
        try {
            Class<? extends InstanceInfoProvider> implClass =
                    StringUtils.isEmpty(clazz) ? DEFAULT_PROVIDER_CLASS : Class.forName(clazz).asSubclass(InstanceInfoProvider.class);
            return implClass.newInstance();
        } catch (ClassCastException e) {
            throw new AWSInstanceInfoException("Class " + clazz + " is not an instance of InstanceInfoProvider!", e);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new AWSInstanceInfoException("Error instantiating AWS info provider class", e);
        }
    }
}
