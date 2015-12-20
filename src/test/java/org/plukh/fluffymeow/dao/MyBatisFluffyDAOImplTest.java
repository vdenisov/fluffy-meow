package org.plukh.fluffymeow.dao;

import org.dbunit.dataset.ReplacementTable;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.guice.transactional.Transactional;
import org.plukh.fluffymeow.dao.entities.User;
import org.plukh.fluffymeow.dao.exceptions.UserExistsException;
import org.plukh.test.dbunit.DataSets;
import org.plukh.test.dbunit.DbUnitTest;

import javax.inject.Inject;

import static org.dbunit.Assertion.assertEqualsByQuery;
import static org.junit.Assert.assertTrue;
import static org.plukh.test.TestUtils.parseDateTime;

@DataSets("users.xml")
public class MyBatisFluffyDAOImplTest extends DbUnitTest {
    private static final String USERS_TABLE = "USERS";

    private static final String[] NO_COLUMNS = new String[0];

    @Inject
    protected FluffyDAO dao;

    protected User existingUser;
    protected User newUser;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        existingUser = new User()
                .withId(1)
                .withEmail("test@test.com")
                .withPasswordHash("AAAA")
                .withPasswordSalt("BBBB")
                .withRegisterDate(parseDateTime("2015-01-01 00:00:00"))
                .withLastLoginDate(parseDateTime("2015-01-02 00:00:00"));
        newUser = new User()
                .withEmail("test2@test.com")
                .withPasswordHash("CCCC")
                .withPasswordSalt("DDDD")
                .withRegisterDate(parseDateTime("2015-01-02 00:00:00"))
                .withLastLoginDate(parseDateTime("2015-01-03 00:00:00"));
    }

    @Test
    @Transactional
    public void testCreateUserNewUser() throws Exception {
        dao.createUser(newUser);

        assertTrue("User Id not updated after create", newUser.getId() > 0);

        ReplacementTable expected = new ReplacementTable(getDataSet("expected/expected_create_user.xml").getTable(USERS_TABLE));
        expected.addReplacementSubstring("[USR_ID]", String.valueOf(newUser.getId()));
        expected.setStrictReplacement(true);

        assertEqualsByQuery(expected, getTestConnection(), USERS_TABLE, "select * from USERS order by USR_ID", NO_COLUMNS);
    }

    @Test(expected = UserExistsException.class)
    @Transactional
    public void testCreateUserShouldThrowExceptionOnDuplicateUser() throws Exception {
        newUser.setEmail(existingUser.getEmail());
        dao.createUser(newUser);
    }
}