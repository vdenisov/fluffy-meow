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

package org.plukh.fluffymeow.config;

import org.apache.commons.lang3.StringUtils;

public class VersionInfoProviderFactory {
    public static final String PROVIDER_CLASS_PROPERTY = "org.plukh.fluffymeow.config.versionInfoProviderClass";
    private static final Class<? extends VersionInfoProvider> DEFAULT_PROVIDER_CLASS = MavenVersionInfoProviderImpl.class;

    public static VersionInfoProvider getInstance() {
        String clazz = System.getProperty(PROVIDER_CLASS_PROPERTY);
        try {
            Class<? extends VersionInfoProvider> implClass =
                    StringUtils.isEmpty(clazz) ? DEFAULT_PROVIDER_CLASS : Class.forName(clazz).asSubclass(VersionInfoProvider.class);
            return implClass.newInstance();
        } catch (ClassCastException e) {
            throw new ConfigException("Class " + clazz + " is not an instance of VersionInfoProvider!", e);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ConfigException("Error instantiating version info provider class", e);
        }
    }
}
