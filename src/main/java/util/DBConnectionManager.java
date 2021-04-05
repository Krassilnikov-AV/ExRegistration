/*
 * Copyright (c) 2021 Tander, All Rights Reserved.
 */

package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	private Connection connection;

	public DBConnectionManager(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");
		this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/userdb"
			, "postgres"
			, "alex159");
	}

	public Connection getConnection(){
		return this.connection;
	}
}