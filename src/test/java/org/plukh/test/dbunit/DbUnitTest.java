package org.plukh.test.dbunit;

import com.google.inject.Inject;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dbunit.*;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.DatabaseDataSourceConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.mysql.MySqlDataTypeFactory;
import org.dbunit.ext.mysql.MySqlMetadataHandler;
import org.dbunit.operation.CompositeOperation;
import org.dbunit.operation.DatabaseOperation;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.plukh.test.dao.TestMyBatisDAOModule;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;

@RunWith(GuiceJUnitRunner.class)
@GuiceModules(TestMyBatisDAOModule.class)
public abstract class DbUnitTest {
    private static final Logger log = LogManager.getLogger(DbUnitTest.class);

    private IDatabaseTester tester;
    private IOperationListener operationListener;

    public static final DatabaseOperation DISABLE_KEY_CHECKS = new DisableForeignKeyChecksOperation();
    public static final DatabaseOperation ENABLE_KEY_CHECKS = new EnableForeignKeyChecksOperation();

    public static final DatabaseOperation CLEAN_INSERT_NO_CHECKS = new CompositeOperation(
            new DatabaseOperation[] {DISABLE_KEY_CHECKS, DatabaseOperation.DELETE_ALL, DatabaseOperation.INSERT, ENABLE_KEY_CHECKS});
    public static final DatabaseOperation DELETE_ALL_NO_CHECKS = new CompositeOperation(
            new DatabaseOperation[] {DISABLE_KEY_CHECKS, DatabaseOperation.DELETE_ALL, ENABLE_KEY_CHECKS});

    @Rule
    public DataSetsWatcher dataSetsWatcher = new DataSetsWatcher();

    @Inject
    protected SqlSession session;

    @Before
    public void setUp() throws Exception {
        final IDatabaseTester databaseTester = getDatabaseTester();
        assertNotNull( "DatabaseTester is not set", databaseTester );
        databaseTester.setSetUpOperation( getSetUpOperation() );
        databaseTester.setDataSet( getDataSet() );
        databaseTester.setOperationListener(getOperationListener());
        databaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        try {
            final IDatabaseTester databaseTester = getDatabaseTester();
            assertNotNull( "DatabaseTester is not set", databaseTester );
            databaseTester.setTearDownOperation( getTearDownOperation() );
            databaseTester.setDataSet( getDataSet() );
            databaseTester.setOperationListener(getOperationListener());
            databaseTester.onTearDown();
        } finally {
            tester = null;
        }
    }

    protected IDataSet getDataSet() throws Exception {
        String[] resources = getDatabaseResources();
        return getDataSet(resources);
    }

    private String[] getDatabaseResources() {
        return dataSetsWatcher.getResources();
    }

    protected IDataSet getDataSet(String... resources) throws Exception {
        log.debug("Adding datasets: " + Arrays.toString(resources));

        XmlDataSet[] dataSets = new XmlDataSet[resources.length];

        //Create individual datasets from specified files
        for (int i = 0; i < resources.length; ++i) {
            XmlDataSet set = new XmlDataSet(this.getClass().getResourceAsStream(resources[i]));
            dataSets[i] = set;
        }

        return new CompositeDataSet(dataSets, true);
    }

    protected void setUpDatabaseConfig(DatabaseConfig config) {
        config.setProperty("http://www.dbunit.org/properties/datatypeFactory", new MySqlDataTypeFactory());
        config.setProperty("http://www.dbunit.org/properties/metadataHandler", new MySqlMetadataHandler());
    }

    protected final IDatabaseConnection getConnection() throws Exception {
        final IDatabaseTester databaseTester = getDatabaseTester();
        assertNotNull("DatabaseTester is not set", databaseTester);
        IDatabaseConnection connection = databaseTester.getConnection();
        // Ensure that users have the possibility to configure the connection's configuration
        setUpDatabaseConfig(connection.getConfig());
        return connection;
    }

    protected IDatabaseTester getDatabaseTester() throws Exception {
        if ( this.tester == null ) {
            this.tester = newDatabaseTester();
        }
        return this.tester;
    }

    protected IDatabaseTester newDatabaseTester() throws SQLException {
        return new DefaultDatabaseTester(new DatabaseDataSourceConnection(getDataSource()));
    }

    protected DataSource getDataSource() {
        return session.getConfiguration().getEnvironment().getDataSource();
    }

    protected DatabaseOperation getSetUpOperation() throws Exception {
        return CLEAN_INSERT_NO_CHECKS;
    }

    protected DatabaseOperation getTearDownOperation() throws Exception {
        return DELETE_ALL_NO_CHECKS;
    }

    protected IOperationListener getOperationListener()
    {
        if(this.operationListener==null){
            this.operationListener = new DefaultOperationListener(){
                public void connectionRetrieved(IDatabaseConnection connection) {
                    super.connectionRetrieved(connection);
                    // When a new connection has been created then invoke the setUp method
                    // so that user defined DatabaseConfig parameters can be set.
                    setUpDatabaseConfig(connection.getConfig());
                }
            };
        }
        return this.operationListener;
    }

    protected IDatabaseConnection getTestConnection() throws DatabaseUnitException {
        IDatabaseConnection connection = new DatabaseConnection(session.getConnection());
        setUpDatabaseConfig(connection.getConfig());
        return connection;
    }

    public String getDSStringValue(ITable table, String columnName, int position) throws DataSetException {
        Object value = table.getValue(position, columnName);
        if(value != null && !value.equals(ITable.NO_VALUE)) return (String) value;
        return null;
    }

    public Integer getDSIntegerValue(ITable table, String columnName, int position) throws DataSetException {
        String value = getDSStringValue(table, columnName, position);
        if(value != null && !value.equals(ITable.NO_VALUE)) return Integer.valueOf(value);
        return null;
    }

    public DateTime getDSDateTimeValue(ITable table, String columnName, int position) throws DataSetException {
        String value = getDSStringValue(table, columnName, position);
        if (value != null && !value.equals(ITable.NO_VALUE))
            return DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").parseDateTime(value);
        return null;
    }

    public Double getDSDoubleValue(ITable table, String columnName, int position) throws DataSetException {
        String value = getDSStringValue(table, columnName, position);
        if(value != null && !value.equals(ITable.NO_VALUE)) return Double.valueOf(value);
        return null;
    }

    public Long getDSLongValue(ITable table, String columnName, int position) throws DataSetException {
        String value = getDSStringValue(table, columnName, position);
        if(value != null && !value.equals(ITable.NO_VALUE)) return Long.valueOf(value);
        return null;
    }

    public Boolean getDSBooleanValue(ITable table, String columnName, int position) throws DataSetException {
        String value = getDSStringValue(table, columnName, position);
        if(value != null && !value.equals(ITable.NO_VALUE)) {
            //TINIINT detection
            if(value.length() == 1 && Character.isDigit(value.charAt(0))) return Integer.valueOf(value) == 1;
            return Boolean.valueOf(value);
        }
        return null;
    }
}
