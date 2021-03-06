/**
 * 
 */
package org.codehaus.cargo.sample.testdata.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class DerbyDao extends JdbcDaoSupport
{
    public void dropTable()
    {
        this.getJdbcTemplate().update("DROP TABLE Person" + "");
    }

    public void createTable()
    {
        this.getJdbcTemplate().update(
            "CREATE TABLE Person("
                + "ID int generated by default as identity (start with 1) not null,"
                + "FIRSTNAME VARCHAR(20) NOT NULL," + "LASTNAME VARCHAR(20) NOT NULL,"
                + "PRIMARY KEY (ID)" + ")");
    }

    public void create(String firstName, String lastName)
    {
        this.getJdbcTemplate().update("INSERT INTO PERSON (FIRSTNAME, LASTNAME) VALUES(?,?)",
            new Object[] {firstName, lastName});
    }

    public List selectAll()
    {
        return this.getJdbcTemplate().query("select FIRSTNAME, LASTNAME from PERSON",
            new PersonRowMapper());
    }

    class PersonRowMapper implements RowMapper
    {

        public Object mapRow(ResultSet rs, int line) throws SQLException
        {
            PersonResultSetExtractor extractor = new PersonResultSetExtractor();
            return extractor.extractData(rs);
        }

        class PersonResultSetExtractor implements ResultSetExtractor
        {

            public Object extractData(ResultSet rs) throws SQLException
            {
                Person person = new Person();
                person.setFirstName(rs.getString(1));
                person.setLastName(rs.getString(2));
                return person;
            }

        }
    }
}
