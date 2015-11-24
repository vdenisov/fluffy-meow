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

package org.plukh.fluffymeow.ui;

import com.google.inject.Injector;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.HasComponents;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.dao.FluffyDAO;
import org.plukh.fluffymeow.guice.GuicedViewProvider;
import org.vaadin.addons.guice.ui.ScopedUI;
import org.vaadin.webinar.i18n.Translatable;

import javax.inject.Inject;
import java.util.Locale;

@Theme("fluffy-meow")
public class FluffyUI extends ScopedUI {
    private static final Logger log = LogManager.getLogger(FluffyUI.class);

    private final transient FluffyDAO dao;
    private final Panel navigableContent;

    private transient final Injector injector;

    @Inject
    public FluffyUI(FluffyDAO dao, GuicedViewProvider viewProvider, Injector injector) {
        this.dao = dao;
        this.injector = injector;

        this.navigableContent = new Panel();

        Navigator navigator = new Navigator(this, navigableContent);
        navigator.addProvider(viewProvider);
    }

    @Override
    protected void init(VaadinRequest request) {
        //Create and set root layout
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        //Create and add header
        Header header = injector.getInstance(Header.class);
        root.addComponent(header);

        //Add navigable part
        navigableContent.setSizeFull();
        root.addComponent(navigableContent);
        root.setExpandRatio(navigableContent, 1.0f);

        //Create and add footer
        Footer footer = injector.getInstance(Footer.class);
        root.addComponent(footer);

        //Set locale as the last init action, after all components/views have been created
        setLocale(getPage().getWebBrowser().getLocale());
    }

    @Override
    public void setLocale(Locale locale) {
        super.setLocale(locale);
        updateMessageStrings(getContent());
    }

    private void updateMessageStrings(Component component) {
        if (component instanceof Translatable) {
            ((Translatable) component).updateMessageStrings();
        }
        if (component instanceof HasComponents) {
            ((HasComponents) component).iterator().forEachRemaining(this::updateMessageStrings);
        }
    }
}
