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

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTimeZone;
import org.plukh.fluffymeow.Config;
import org.plukh.fluffymeow.shiro.FluffyShiroModule;
import org.plukh.fluffymeow.ui.FluffyUI;
import org.vaadin.addons.guice.uiscope.UIScopeModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.io.IOException;

import static org.plukh.fluffymeow.utils.DAOUtils.deregisterJdbcDrivers;
import static org.plukh.fluffymeow.utils.DAOUtils.stopAbandonedConnectionCleanupThread;

@WebListener
public class GuiceContextListener extends GuiceServletContextListener {
    private static final Logger log = LogManager.getLogger(GuiceContextListener.class);

    private ServletContext servletContext;
    private final Config config = new Config();

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Initializing Fluffy Meow...");

        //Set DateTime default timezone
        DateTimeZone.setDefault(DateTimeZone.UTC);

        this.servletContext = servletContextEvent.getServletContext();

        try {
            config.load(System.getProperty(Config.CONFIG_FILE_NAME_PROPERTY));
        } catch (IOException e) {
            throw new RuntimeException("Cannot load configuration");
        }

        super.contextInitialized(servletContextEvent);

        log.info("Fluffy Meow initialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        deregisterJdbcDrivers();
        stopAbandonedConnectionCleanupThread();

        super.contextDestroyed(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        return Guice.createInjector(
                new UIScopeModule(FluffyUI.class),
                new GuiceServletModule(),
                new FluffyModule(config),
                new FluffyMyBatisDAOModule("production", config.getProperties()),
                new EventBusModule(),
                new FluffyShiroModule(servletContext));
    }
}
