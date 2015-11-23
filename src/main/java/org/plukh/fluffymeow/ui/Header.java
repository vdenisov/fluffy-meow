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

import com.google.inject.Inject;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import org.plukh.fluffymeow.ui.i18n.LocaleChooserComponent;
import org.plukh.fluffymeow.ui.login.HeaderLoginComponent;
import org.vaadin.webinar.i18n.Messages;
import org.vaadin.webinar.i18n.Translatable;

public class Header extends HorizontalLayout implements Translatable {
    private Image logoImage;
    private HeaderLoginComponent loginComponent;
    private LocaleChooserComponent localeChooser;

    @Inject
    public Header(HeaderLoginComponent loginComponent, LocaleChooserComponent localeChooser) {
        this.loginComponent = loginComponent;
        this.localeChooser = localeChooser;

        setWidth("100%");
        setMargin(true);

        Resource resource = new ThemeResource("logo.png");
        logoImage = new Image(null, resource);
        logoImage.addStyleName("pointer");
        logoImage.addClickListener(this::onLogoImageClick);

        addComponent(logoImage);
        addComponent(loginComponent);
        addComponent(localeChooser);

        setExpandRatio(loginComponent, 1.0f);
        setComponentAlignment(loginComponent, Alignment.BOTTOM_LEFT);
    }

    private void onLogoImageClick(MouseEvents.ClickEvent event) {
        UI.getCurrent().getNavigator().navigateTo(MainView.VIEW_NAME);
    }

    @Override
    public void updateMessageStrings() {
        Messages messages = Messages.getInstance();

        logoImage.setAlternateText(messages.getMessage("header.logo.alternatetext"));
    }
}
