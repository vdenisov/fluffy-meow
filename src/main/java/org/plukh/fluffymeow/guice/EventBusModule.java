package org.plukh.fluffymeow.guice;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.Matchers;
import com.google.inject.spi.InjectionListener;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EventBusModule extends AbstractModule {
    private static final Logger log = LogManager.getLogger(EventBusModule.class);

    private final EventBus eventBus = new EventBus("FluffyMeow Main EventBus");

    @Override
    protected void configure() {
        log.trace("Creating EventBusModule...");

        bind(EventBus.class).toInstance(eventBus);
        bindListener(Matchers.any(), new TypeListener() {
            public <I> void hear(TypeLiteral<I> typeLiteral, TypeEncounter<I> typeEncounter) {
                typeEncounter.register((InjectionListener<I>) eventBus::register);
            }
        });

        log.debug("EventBusModule created");
    }}
