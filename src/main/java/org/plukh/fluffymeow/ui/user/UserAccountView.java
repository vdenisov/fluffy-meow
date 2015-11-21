package org.plukh.fluffymeow.ui.user;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class UserAccountView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "account";

    public UserAccountView() {
        setSizeFull();

        Label label = new Label("Account View will be here");
        addComponent(label);
        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
