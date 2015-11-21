package org.plukh.fluffymeow.guice;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewProvider;

import javax.inject.Inject;
import javax.inject.Provider;
import java.util.Map;
import java.util.Set;

public class GuicedViewProvider implements ViewProvider {
    private static final String DEFAULT_VIEW_NAME = "";

    private Map<String, Provider<View>> viewMapping;
    private Set<String> keys;

    @Inject
    public GuicedViewProvider(Map<String, Provider<View>> viewMapping) {
        this.viewMapping = viewMapping;
        this.keys = viewMapping.keySet();
    }

    public String getViewName(String viewAndParameters) {
        for (String key : keys) {
            if (viewAndParameters.startsWith(key)) {
                return key;
            }
        }
        return DEFAULT_VIEW_NAME;
    }

    public View getView(String viewName) {
        Provider<View> provider = viewMapping.get(viewName);
        if (provider != null) {
            return provider.get();
        }

        return viewMapping.get(DEFAULT_VIEW_NAME).get();
    }
}