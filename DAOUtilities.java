package myBank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOUtilities {

	private static final String CONNECTION_USERNAME = "YOUR_USERNAME";
	private static final String CONNECTION_PASSWORD = "YOUR_PASSWORD";
	private static final String URL = "jdbc:postgresql://localhost:0000/Bank";
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
		
		if (connection.isClosed()){
			System.out.println("Opening new connection...");
			connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		}
		return connection;
	}

	//un-comment the below code to test if u have succesfully connected to the database!!!!!!
	//public static void main(String[] args) throws SQLException {
		//connection = DriverManager.getConnection(URL, CONNECTION_USERNAME, CONNECTION_PASSWORD);
		//getConnection();
	//}
}
