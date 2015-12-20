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

package org.plukh.fluffymeow;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.aws.InstanceInfoProvider;
import org.plukh.fluffymeow.aws.InstanceInfoProviderFactory;
import org.plukh.fluffymeow.config.S3Properties;

import java.io.IOException;
import java.util.Properties;

public class Config {
    private static final Logger log = LogManager.getLogger(Config.class);

    public static final String CONFIG_FILE_NAME_PROPERTY = "com.sibylvision.signal.server.configfilename";

    private static final String APPLICATION_NAME = "fluffy-meow";

    private final S3Properties properties = new S3Properties();

    public String getProperty(String property) {
        return properties.getProperty(property);
    }

    public void setProperty(String property, String value) {
        properties.setProperty(property, value);
    }

    public void load(String configFileName) throws IOException {
        InstanceInfoProvider provider = InstanceInfoProviderFactory.getInstance();
        String deploymentId = provider.getInstanceInfo().getDeploymentId();
        properties.load(deploymentId, APPLICATION_NAME, configFileName);
        log.info("Configuration loaded");
    }

    public Properties getProperties() {
        return properties;
    }
}
