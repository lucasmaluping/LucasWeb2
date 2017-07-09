package com.lucas.test;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

import com.lucas.db.JdbcUtills;

public class JdbcUtilsTest {

	@Test
	public void testGetConnection() throws SQLException {
		Connection connection = JdbcUtills.getConnection();
		System.out.println(connection);
	}

}
