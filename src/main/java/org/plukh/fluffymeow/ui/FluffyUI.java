package org.plukh.fluffymeow.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.Test;
import org.plukh.fluffymeow.dao.FluffyDAO;
import org.vaadin.addons.guice.ui.ScopedUI;

import javax.inject.Inject;
import javax.inject.Named;

@Theme("classifier")
public class FluffyUI extends ScopedUI {
    private static final Logger log = LogManager.getLogger(FluffyUI.class);

    private transient FluffyDAO dao;
    private Test uiScopedValue;
    private Test sessionScopedValue;

    private Label sessionScopedValueLabel;
    private Label uiScopedValueLabel;

    @Inject
    public FluffyUI(FluffyDAO dao, @Named("uiScoped") Test uiScopedValue, @Named("sessionScoped") Test
            sessionScopedValue) {
        this.dao = dao;
        this.uiScopedValue = uiScopedValue;
        this.sessionScopedValue = sessionScopedValue;
    }

    @Override
    protected void init(VaadinRequest request) {
        getPage().setTitle("Tweet Classifier");
        createTestLayout();
    }

    private void createTestLayout() {
        log.debug("Creating Test Layout");

        VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        sessionScopedValueLabel = new Label();
        uiScopedValueLabel = new Label();
        updateLabels();

        layout.addComponent(sessionScopedValueLabel);
        layout.addComponent(uiScopedValueLabel);

        layout.addComponent(new Button("Increment values", event -> {
            sessionScopedValue.setValue(sessionScopedValue.getValue() + 1);
            uiScopedValue.setValue(uiScopedValue.getValue() + 1);
            updateLabels();
        }));
        layout.addComponent(new Button("Refresh values", event -> {
            updateLabels();
        }));

        setContent(layout);

        log.debug("Test Layout created");
    }

    private void updateLabels() {
        sessionScopedValueLabel.setValue("Session: " + sessionScopedValue.getValue());
        uiScopedValueLabel.setValue("UI: " + uiScopedValue.getValue());
    }
}
