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

import com.google.common.eventbus.EventBus;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.ui.user.UserAccountView;
import org.vaadin.webinar.i18n.Messages;
import org.vaadin.webinar.i18n.Translatable;

import javax.inject.Inject;

public class HeaderLoggedInComponent extends CustomComponent implements Translatable {
    private static final Logger log = LogManager.getLogger(HeaderLoggedInComponent.class);

    protected final Label loggedInLabel;
    protected final Button logoutButton;
    protected final Button manageAccountButton;

    protected transient final EventBus eventBus;

    @Inject
    public HeaderLoggedInComponent(EventBus eventBus) {
        log.trace("Creating HeaderLoggedInComponent...");

        this.eventBus = eventBus;

        HorizontalLayout layout = new HorizontalLayout();

        layout.setSpacing(true);

        loggedInLabel = new Label();
        loggedInLabel.setContentMode(ContentMode.HTML);

        logoutButton = new Button();
        logoutButton.addStyleName(ValoTheme.BUTTON_LINK);
        logoutButton.addClickListener(this::onLogout);

        manageAccountButton = new Button();
        manageAccountButton.addStyleName(ValoTheme.BUTTON_LINK);
        manageAccountButton.addClickListener(this::onManageAccount);

        layout.addComponent(loggedInLabel);
        layout.addComponent(manageAccountButton);
        layout.addComponent(logoutButton);

        setCompositionRoot(layout);

        log.debug("Created HeaderLoggedInComponent");
    }

    private void onLogout(Button.ClickEvent clickEvent) {
        //TODO: proper logout action should be implemented here
        eventBus.post(new LogoutEventImpl());
    }

    private void onManageAccount(Button.ClickEvent clickEvent) {
        UI.getCurrent().getNavigator().navigateTo(UserAccountView.VIEW_NAME);
    }

    @Override
    public void updateMessageStrings() {
        Messages messages = Messages.getInstance();

        loggedInLabel.setCaption(messages.getMessage("header.loggedin.loggedinas", "Plukh"));
        logoutButton.setCaption(messages.getMessage("header.loggedin.logoutlink"));
        manageAccountButton.setCaption(messages.getMessage("header.loggedin.account"));
    }
}
