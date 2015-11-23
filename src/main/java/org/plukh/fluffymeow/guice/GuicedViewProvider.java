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

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;
import java.util.Set;

public class GuicedViewProvider implements ViewProvider {
    private static final String DEFAULT_VIEW_NAME = "";

    private transient Map<String, Provider<View>> viewMapping;
    private Set<String> keys;

    @Inject
    public GuicedViewProvider(Map<String, Provider<View>> viewMapping) {
        this.viewMapping = viewMapping;
        this.keys = viewMapping.keySet();
    }

    public String getViewName(String viewAndParameters) {
        for (String key : keys) {
            if (viewAndParameters.startsWith(key)) {
                return key;
            }
        }
        return DEFAULT_VIEW_NAME;
    }

    public View getView(String viewName) {
        Provider<View> provider = viewMapping.get(viewName);
        if (provider != null) {
            return provider.get();
        }

        return viewMapping.get(DEFAULT_VIEW_NAME).get();
    }
}