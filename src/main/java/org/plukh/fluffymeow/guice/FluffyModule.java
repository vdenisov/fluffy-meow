package org.plukh.fluffymeow.guice;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import com.vaadin.navigator.View;
import org.plukh.fluffymeow.dao.FluffyDAO;
import org.plukh.fluffymeow.dao.MyBatisFluffyDAOImpl;
import org.plukh.fluffymeow.ui.MainView;
import org.plukh.fluffymeow.ui.user.UserAccountView;
import org.plukh.fluffymeow.ui.user.UserRegistrationView;
import org.vaadin.addons.guice.uiscope.UIScoped;

public class FluffyModule extends AbstractModule {
    protected MapBinder<String, View> mapbinder;

    @Override
    protected void configure() {
        bind(FluffyDAO.class).to(MyBatisFluffyDAOImpl.class);

        //View bindings
        mapbinder = MapBinder.newMapBinder(binder(), String.class, View.class);

        addBinding(UserAccountView.VIEW_NAME, UserAccountView.class);
        addBinding(UserRegistrationView.VIEW_NAME, UserRegistrationView.class);
        addBinding(MainView.VIEW_NAME, MainView.class);
    }

    protected void addBinding(String uriFragment, Class<? extends View> clazz) {
        mapbinder.addBinding(uriFragment).to(clazz).in(UIScoped.class);
    }
}
