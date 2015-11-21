package org.plukh.fluffymeow.guice;

import com.google.inject.servlet.ServletModule;
import com.vaadin.server.Constants;
import org.vaadin.addons.guice.servlet.VGuiceApplicationServlet;

import java.util.HashMap;
import java.util.Map;

public class GuiceServletModule extends ServletModule {
    @Override
    protected void configureServlets() {
        Map<String, String> initParams = new HashMap<>();
        initParams.put(Constants.PARAMETER_WIDGETSET, "org.plukh.fluffymeow.ui.FluffyWidgetSet");

        serve("/*").with(VGuiceApplicationServlet.class, initParams);
    }
}
