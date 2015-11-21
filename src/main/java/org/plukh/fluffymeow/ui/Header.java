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
