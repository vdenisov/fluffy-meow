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

package org.plukh.fluffymeow.ui.login;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Injector;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.webinar.i18n.Translatable;

import javax.inject.Inject;

public class HeaderUserComponent extends CustomComponent implements Translatable {
    private static final Logger log = LogManager.getLogger(HeaderUserComponent.class);

    private Component subcomponent;

    private transient final Injector injector;

    @Inject
    public HeaderUserComponent(Injector injector) {
        log.trace("Creating HeaderUserComponent...");

        setWidth("100%");

        this.injector = injector;
        //TODO: Determine state and create appropriate component here
        subcomponent = injector.getInstance(HeaderLoginForm.class);
        setCompositionRoot(subcomponent);

        log.debug("HeaderUserComponent created");
    }

    @Subscribe
    public void onLoginEvent(LoginEvent e) {
        //TODO: proper handling of logiu events

        log.debug("Processing login event");
        subcomponent = injector.getInstance(HeaderLoggedInComponent.class);
        ((Translatable) subcomponent).updateMessageStrings();

        setCompositionRoot(subcomponent);
    }

    @Subscribe
    public void onLogoutEvent(LogoutEvent e) {
        //TODO: proper handling of logout events

        log.debug("Processing logout event");
        subcomponent = injector.getInstance(HeaderLoginForm.class);

        setCompositionRoot(subcomponent);
    }

    @Override
    public void updateMessageStrings() {
        //Do nothing, no components
    }
}
