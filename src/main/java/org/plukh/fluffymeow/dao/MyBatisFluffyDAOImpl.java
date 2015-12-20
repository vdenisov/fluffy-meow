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

package org.plukh.fluffymeow.dao;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.exceptions.PersistenceException;
import org.plukh.fluffymeow.dao.entities.User;
import org.plukh.fluffymeow.dao.exceptions.UserExistsException;
import org.plukh.fluffymeow.dao.mappers.FluffyMapper;

import javax.inject.Inject;

public class MyBatisFluffyDAOImpl implements FluffyDAO {
    private static final String EMAIL_UNIQUE_INDEX_NAME = "UNIQUE_USR_EMAIL";
    private final FluffyMapper mapper;

    @Inject
    public MyBatisFluffyDAOImpl(FluffyMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void createUser(User user) throws UserExistsException {
        try {
            mapper.createUser(user);
        } catch (PersistenceException e) {
            Throwable cause = e.getCause();
            if (cause instanceof MySQLIntegrityConstraintViolationException) {
                MySQLIntegrityConstraintViolationException cve = (MySQLIntegrityConstraintViolationException) cause;
                if (StringUtils.contains(cve.getMessage(), EMAIL_UNIQUE_INDEX_NAME)) throw new UserExistsException(user.getEmail());
            }
            throw e;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        return mapper.getUserByEmail(email);
    }
}
