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

import java.util.Locale;

public class Language {
    private final String flagFile;
    private final Locale locale;
    private final String message;

    public Language(String flagFile, Locale locale, String message) {
        this.flagFile = flagFile;
        this.locale = locale;
        this.message = message;
    }

    public String getFlagFile() {
        return flagFile;
    }

    public Locale getLocale() {
        return locale;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Language)) return false;

        Language language = (Language) o;

        if (!flagFile.equals(language.flagFile)) return false;
        if (!locale.equals(language.locale)) return false;
        return message.equals(language.message);

    }

    @Override
    public int hashCode() {
        int result = flagFile.hashCode();
        result = 31 * result + locale.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Language{" +
                "flagFile='" + flagFile + '\'' +
                ", locale=" + locale +
                ", message='" + message + '\'' +
                '}';
    }
}
