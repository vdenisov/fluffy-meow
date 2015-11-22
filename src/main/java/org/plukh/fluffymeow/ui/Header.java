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
import org.plukh.fluffymeow.ui.user.UserAccountView;
import org.plukh.fluffymeow.ui.user.UserRegistrationView;

public class Header extends HorizontalLayout {
    private final Navigator navigator;

    public Header() {
        this.navigator = UI.getCurrent().getNavigator();

        setWidth("100%");
        //setSpacing(true);
        setMargin(true);

        addComponent(new Label("Header will be here"));
        addComponent(new Button("To Main View", event -> navigator.navigateTo("")));
        addComponent(new Button("To Account View", event -> navigator.navigateTo(UserAccountView.VIEW_NAME)));
        addComponent(new Button("To Registration View", event -> navigator.navigateTo(UserRegistrationView.VIEW_NAME)));
    }
}
