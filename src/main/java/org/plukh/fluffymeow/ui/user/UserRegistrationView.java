package org.plukh.fluffymeow.ui.user;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UserRegistrationView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "register";

    public UserRegistrationView() {
        setSizeFull();

        Label label = new Label("Registration View will be here");
        addComponent(label);
        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
