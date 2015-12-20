package org.plukh.test.dbunit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.AbstractOperation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DisableForeignKeyChecksOperation extends AbstractOperation {
    private static final Logger log = LogManager.getLogger(DisableForeignKeyChecksOperation.class);

    @Override
    public void execute(IDatabaseConnection connection, IDataSet dataSet) throws DatabaseUnitException, SQLException {
        if (log.isDebugEnabled()) log.debug("Disabling foreign key checks");

        Connection realConnection = connection.getConnection();

        try (Statement stmt = realConnection.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS=0");
        }
    }

}
