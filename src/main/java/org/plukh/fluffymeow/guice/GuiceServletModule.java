package org.plukh.fluffymeow.guice;

import com.google.inject.servlet.ServletModule;
import org.vaadin.addons.guice.servlet.VGuiceApplicationServlet;

public class GuiceServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        serve("/*").with(VGuiceApplicationServlet.class);
    }
}
