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

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import org.plukh.fluffymeow.ui.user.UserAccountView;
import org.plukh.fluffymeow.ui.user.UserRegistrationView;

public class Footer extends HorizontalLayout {
    private final Navigator navigator;

    public Footer() {
        this.navigator = UI.getCurrent().getNavigator();

        setWidth("100%");
        setSpacing(true);
        setMargin(true);

        addComponent(new Label("Footer will be here"));

        Button mainViewButton = new Button("To Main View", event -> navigator.navigateTo(""));
        Button accountViewButton = new Button("To Account View", event -> navigator.navigateTo(UserAccountView.VIEW_NAME));
        Button registrationViewButton = new Button("To Registration View", event -> navigator.navigateTo(UserRegistrationView.VIEW_NAME));

        mainViewButton.addStyleName(ValoTheme.BUTTON_LINK);
        accountViewButton.addStyleName(ValoTheme.BUTTON_LINK);
        registrationViewButton.addStyleName(ValoTheme.BUTTON_LINK);

        addComponent(mainViewButton);
        addComponent(accountViewButton);
        addComponent(registrationViewButton);
    }
}
