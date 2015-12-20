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

package org.plukh.fluffymeow.guice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.guice.XMLMyBatisModule;
import org.plukh.fluffymeow.dao.FluffyDAO;
import org.plukh.fluffymeow.dao.MyBatisFluffyDAOImpl;

import java.util.Properties;

public class FluffyMyBatisDAOModule extends XMLMyBatisModule {
    private static final Logger log = LogManager.getLogger(FluffyMyBatisDAOModule.class);

    protected final String environmentId;
    protected final Properties properties;

    public FluffyMyBatisDAOModule(String environmentId, Properties properties) {
        this.environmentId = environmentId;
        this.properties = properties;
    }

    @Override
    protected void initialize() {
        log.debug("DAO Module initializing, environment: " + environmentId);

        //Set environment
        setEnvironmentId(environmentId);
        //Define properties
        addProperties(properties);

        //Bind DAO
        bind(FluffyDAO.class).to(MyBatisFluffyDAOImpl.class);

        log.info("DAO Module initialized");
    }
}