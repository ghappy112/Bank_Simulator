package myBank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOUtilities {

	private static final String CONNECTION_USERNAME = "postgres";
	private static final String CONNECTION_PASSWORD = "gregsql";
	//private static final String URL = "C:\\Program Files\\PostgreSQL\\12\\pgAdmin 4\\bin\\pgAdmin4.exe";
	//private static final String URL = "jdbc:postgresql://localhost/Bank";
	private static final String URL = "jdbc:postgresql://localhost:5432/Bank";
	//private static final String URL = "jdbc:postgresql://localhost:5432/Bank";
	//"C:\Program Files\PostgreSQL\12\pgAdmin 4\bin\pgAdmin4.exe"
	private static Connection connection;
	
	public static synchronized Connection getConnection() throws SQLException {
		if (connection == null) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException e) {
				System.out.println("Could not register driver!");
				e.printStackTrace();
			}
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		
		//If connection was closed then retrieve a new connection
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}

	
	public static void main(String[] args) throws SQLException {
		//connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		getConnection();
	}
}
