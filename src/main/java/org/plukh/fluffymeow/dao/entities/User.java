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

package org.plukh.fluffymeow.dao.entities;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;
import org.plukh.fluffymeow.Equatable;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable, Equatable {
    private static final long serialVersionUID = -6014573696570143836L;

    private long id;
    private String email;
    private String passwordHash;
    private String passwordSalt;
    private DateTime registerDate;
    private DateTime lastLoginDate;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public DateTime getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(DateTime registerDate) {
        this.registerDate = registerDate;
    }

    public DateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(DateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public User withId(final long id) {
        this.id = id;
        return this;
    }

    public User withEmail(final String email) {
        this.email = email;
        return this;
    }

    public User withPasswordHash(final String passwordHash) {
        this.passwordHash = passwordHash;
        return this;
    }

    public User withPasswordSalt(final String passwordSalt) {
        this.passwordSalt = passwordSalt;
        return this;
    }

    public User withRegisterDate(final DateTime registerDate) {
        this.registerDate = registerDate;
        return this;
    }

    public User withLastLoginDate(final DateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
        return this;
    }

    @Override
    public boolean isEqual(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(email, user.email) &&
                Objects.equals(passwordHash, user.passwordHash) &&
                Objects.equals(passwordSalt, user.passwordSalt) &&
                Objects.equals(registerDate, user.registerDate) &&
                Objects.equals(lastLoginDate, user.lastLoginDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("passwordHash", passwordHash)
                .add("passwordSalt", passwordSalt)
                .add("registerDate", registerDate)
                .add("lastLoginDate", lastLoginDate)
                .toString();
    }
}
