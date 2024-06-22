package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class Connect {
	public static Connection con;
	static Statement st;
	private static String driver;
	private static String url;
	private static String username;
	private static String password;

	public Connect() {
		connect();
	}

	public static void connect() {
		try {
			getProperties();
			Class.forName(driver);
			System.out.println("Driver Loaded Successfully");
			// need to make some way to use properties file to load password
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Successful Connection");
			st = con.createStatement();
			System.out.println("Statement created Successfully");
			System.out.println("Now, You can access to database");
		} catch (ClassNotFoundException cnfe) {
			System.err.println(cnfe);
		} catch (SQLException sqle) {
			System.err.println(sqle);
		}
	}

	private static void getProperties() {
		Properties props = new Properties();
		try {
			InputStream in = Connect.class.getResourceAsStream("/db.properties");
			props.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		driver = props.getProperty("db.driver");
		url = props.getProperty("db.url");
		username = props.getProperty("db.username");
		password = props.getProperty("db.password");
	}
}
