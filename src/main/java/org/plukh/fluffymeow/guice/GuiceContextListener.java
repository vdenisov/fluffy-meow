package org.plukh.fluffymeow.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.plukh.fluffymeow.ui.FluffyUI;
import org.vaadin.addons.guice.uiscope.UIScopeModule;

import javax.servlet.annotation.WebListener;

@WebListener
public class GuiceContextListener extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(new UIScopeModule(FluffyUI.class), new GuiceServletModule(),
                new FluffyModule());
    }
}
