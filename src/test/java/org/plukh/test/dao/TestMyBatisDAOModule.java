package org.plukh.test.dao;

import org.plukh.fluffymeow.guice.FluffyMyBatisDAOModule;

import java.util.Properties;

public class TestMyBatisDAOModule extends FluffyMyBatisDAOModule {
    public TestMyBatisDAOModule() {
        super("test", new Properties());
    }
}
