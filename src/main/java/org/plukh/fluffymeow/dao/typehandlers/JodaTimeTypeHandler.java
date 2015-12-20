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

package org.plukh.fluffymeow.dao.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.joda.time.DateTime;

import java.sql.*;

@MappedJdbcTypes({JdbcType.DATE, JdbcType.TIME, JdbcType.TIMESTAMP})
public class JodaTimeTypeHandler extends BaseTypeHandler<DateTime>{
    @Override
    public void setNonNullParameter(PreparedStatement stmt, int i, DateTime dateTime, JdbcType jdbcType) throws SQLException {
        switch (jdbcType) {
            case TIMESTAMP:
                stmt.setTimestamp(i, new Timestamp(dateTime.getMillis()));
                break;
            case TIME:
                stmt.setTime(i, new Time(dateTime.getMillis()));
                break;
            case DATE:
                stmt.setDate(i, new Date(dateTime.getMillis()));
                break;
            default:
                throw new SQLException("Unsupported JdbcType: " + jdbcType);
        }
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, String s) throws SQLException {
        Timestamp ts = rs.getTimestamp(s);
        return ts == null ? null : new DateTime(ts.getTime());
    }

    @Override
    public DateTime getNullableResult(ResultSet rs, int i) throws SQLException {
        Timestamp ts = rs.getTimestamp(i);
        return ts == null ? null : new DateTime(ts.getTime());
    }

    @Override
    public DateTime getNullableResult(CallableStatement stmt, int i) throws SQLException {
        Timestamp ts = stmt.getTimestamp(i);
        return ts == null ? null : new DateTime(ts.getTime());
    }
}
