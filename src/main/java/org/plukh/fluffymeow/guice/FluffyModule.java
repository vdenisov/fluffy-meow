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

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.vaadin.navigator.View;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.Config;
import org.plukh.fluffymeow.ui.MainView;
import org.plukh.fluffymeow.ui.user.UserAccountView;
import org.plukh.fluffymeow.ui.user.UserRegistrationView;
import org.vaadin.addons.guice.uiscope.UIScoped;

public class FluffyModule extends AbstractModule {
    private static final Logger log = LogManager.getLogger(FluffyModule.class);
    private final Config config;

    protected MapBinder<String, View> mapbinder;

    public FluffyModule(Config config) {
        this.config = config;
    }

    @Override
    protected void configure() {
        log.trace("Creating FluffyModule...");

        //View bindings
        mapbinder = MapBinder.newMapBinder(binder(), String.class, View.class);

        addBinding(UserAccountView.VIEW_NAME, UserAccountView.class);
        addBinding(UserRegistrationView.VIEW_NAME, UserRegistrationView.class);
        addBinding(MainView.VIEW_NAME, MainView.class);

        //Config binding
        bind(Config.class).toInstance(config);

        log.debug("FluffyModule created");
    }

    protected void addBinding(String uriFragment, Class<? extends View> clazz) {
        mapbinder.addBinding(uriFragment).to(clazz).in(UIScoped.class);
        if (log.isTraceEnabled()) log.trace("Bound navigable view " + clazz.getSimpleName() + " to fragment " + uriFragment);
    }
}
