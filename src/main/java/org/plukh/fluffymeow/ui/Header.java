package org.plukh.fluffymeow.ui;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import org.plukh.fluffymeow.ui.user.UserAccountView;
import org.plukh.fluffymeow.ui.user.UserRegistrationView;

public class Header extends HorizontalLayout {
    private final Navigator navigator;

    public Header(Navigator navigator) {
        this.navigator = navigator;

        setHeight("0%");
        setWidth("100%");

        addComponent(new Label("Header will be here"));
        addComponent(new Button("To Main View", event -> navigator.navigateTo("")));
        addComponent(new Button("To Account View", event -> navigator.navigateTo(UserAccountView.VIEW_NAME)));
        addComponent(new Button("To Registration View", event -> navigator.navigateTo(UserRegistrationView.VIEW_NAME)));
    }
}
