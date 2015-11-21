package org.plukh.fluffymeow.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.themes.ValoTheme;
import org.plukh.fluffymeow.ui.user.UserAccountView;
import org.plukh.fluffymeow.ui.user.UserRegistrationView;

public class Footer extends HorizontalLayout {
    private final Navigator navigator;

    public Footer(Navigator navigator) {
        this.navigator = navigator;

        setHeight("0%");
        setWidth("100%");

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
