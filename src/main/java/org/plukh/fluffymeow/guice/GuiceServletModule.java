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

import com.google.inject.servlet.ServletModule;
import com.vaadin.server.Constants;
import org.vaadin.addons.guice.servlet.VGuiceApplicationServlet;

import java.util.HashMap;
import java.util.Map;

public class GuiceServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        Map<String, String> initParams = new HashMap<>();
        initParams.put(Constants.PARAMETER_WIDGETSET, "org.plukh.fluffymeow.ui.FluffyWidgetSet");

        serve("/*").with(VGuiceApplicationServlet.class, initParams);
    }
}
