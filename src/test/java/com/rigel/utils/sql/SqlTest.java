package com.rigel.utils.sql;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author huming on 2019-06-26.
 */
public class SqlTest {
    @Test
    public void testSqlBasic() {
        String sql = new Sql(){{
            select("*");
            from("table_name");
        }}.toString();
        assertEquals("SELECT * FROM table_name", sql);
    }
}