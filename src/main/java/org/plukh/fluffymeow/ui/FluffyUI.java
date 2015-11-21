package org.plukh.fluffymeow.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.plukh.fluffymeow.dao.FluffyDAO;
import org.plukh.fluffymeow.guice.GuicedViewProvider;
import org.vaadin.addons.guice.ui.ScopedUI;

import javax.inject.Inject;

@Theme("fluffy-meow")
public class FluffyUI extends ScopedUI {
    private static final Logger log = LogManager.getLogger(FluffyUI.class);

    private final transient FluffyDAO dao;
    private final Panel navigableContent;

    @Inject
    public FluffyUI(FluffyDAO dao, GuicedViewProvider viewProvider) {
        this.dao = dao;
        this.navigableContent = new Panel();

        Navigator navigator = new Navigator(this, navigableContent);
        navigator.addProvider(viewProvider);
    }

    @Override
    protected void init(VaadinRequest request) {
        //Create and set root layout
        VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        //Create and add header
        Component header = new Header();
        root.addComponent(header);

        //Add navigable part
        navigableContent.setSizeFull();
        root.addComponent(navigableContent);
        root.setExpandRatio(navigableContent, 1.0f);

        //Create and add footer
        Component footer = new Footer();
        root.addComponent(footer);
    }
}
