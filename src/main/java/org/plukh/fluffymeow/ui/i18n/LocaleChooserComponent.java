/*
 * Fluffy Meow - Torrent RSS generator for TV series
 * Copyright (C) 2015 Victor Denisov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.plukh.fluffymeow.ui.i18n;

import com.google.common.collect.ImmutableList;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.UI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.webinar.i18n.Messages;
import org.vaadin.webinar.i18n.Translatable;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocaleChooserComponent extends CustomComponent implements Translatable {
    private static final Logger log = LogManager.getLogger(LocaleChooserComponent.class);

    protected static final List<Language> languages = ImmutableList.of(
            new Language("flags/RU.png", new Locale("ru"), "header.i18n.ru"),
            new Language("flags/US.png", new Locale("en"), "header.i18n.en"));

    protected final List<Image> flags;

    @Inject
    public LocaleChooserComponent() {
        log.trace("Creating LocaleChooserComponent...");
        setSizeUndefined();

        HorizontalLayout layout = new HorizontalLayout();

        flags = new ArrayList<>();

        languages.forEach(language -> {
            Resource flagResource = new ThemeResource(language.getFlagFile());
            Image flag = new Image(null, flagResource);
            flag.addStyleName("pointer");
            flag.addClickListener(event -> onLanguageChange(language.getLocale()));
            flag.setData(language.getMessage());

            log.debug("Created flag for locale " + language.getLocale());

            flags.add(flag);
            layout.addComponent(flag);
        });

        setCompositionRoot(layout);

        log.debug("LocaleChooserComponent created");
    }

    private void onLanguageChange(Locale locale) {
        if (log.isDebugEnabled()) log.debug("Switching language to " + locale);
        UI.getCurrent().setLocale(locale);
    }

    @Override
    public void updateMessageStrings() {
        Messages messages = Messages.getInstance();
        flags.forEach(image -> {
            String message = (String) image.getData();
            image.setAlternateText(messages.getMessage(message));
            image.setDescription(messages.getMessage(message));
        });
    }
}
