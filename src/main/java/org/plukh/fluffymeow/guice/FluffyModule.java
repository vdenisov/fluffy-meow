package org.plukh.fluffymeow.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.servlet.ServletScopes;
import org.plukh.fluffymeow.Test;
import org.plukh.fluffymeow.dao.FluffyDAO;
import org.plukh.fluffymeow.dao.MyBatisFluffyDAOImpl;
import org.vaadin.addons.guice.uiscope.UIScope;

public class FluffyModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(FluffyDAO.class).to(MyBatisFluffyDAOImpl.class);
        bind(Test.class).annotatedWith(Names.named("uiScoped")).to(Test.class).in(UIScope.getCurrent());
        bind(Test.class).annotatedWith(Names.named("sessionScoped")).to(Test.class).in(ServletScopes.SESSION);
    }
}
