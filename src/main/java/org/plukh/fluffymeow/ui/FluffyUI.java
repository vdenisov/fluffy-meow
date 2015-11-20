package org.plukh.fluffymeow.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.Test;
import org.plukh.fluffymeow.dao.FluffyDAO;
import org.vaadin.addons.guice.ui.ScopedUI;

import javax.inject.Inject;
import javax.inject.Named;

@Theme("fluffy-meow")
public class FluffyUI extends ScopedUI {
    private static final Logger log = LogManager.getLogger(FluffyUI.class);

    private final transient FluffyDAO dao;
    private final Navigator navigator;

    @Inject
    public FluffyUI(FluffyDAO dao, Navigator navigator) {
        this.dao = dao;
        this.navigator = navigator;
    }

    @Override
    protected void init(VaadinRequest request) {

    }

}
