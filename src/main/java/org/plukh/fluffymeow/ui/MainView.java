package org.plukh.fluffymeow.ui;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

public class MainView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "main";

    public MainView() {
        setSizeFull();

        Label label = new Label("Main View will be here");
        addComponent(label);
        setComponentAlignment(label, Alignment.MIDDLE_CENTER);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
