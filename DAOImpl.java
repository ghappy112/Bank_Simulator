package myBank;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class DAOImpl implements DAO {

	Connection connection = null;
	PreparedStatement stmt = null;
	
	@Override
	public boolean register(String username, String pass_word, String firstName, String lastName, String email) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO Users VALUES (?, ?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql);
			int PK = generateNewPrimaryKey();
			stmt.setInt(1, PK);
			stmt.setString(2, username);
			stmt.setString(3, pass_word);
			stmt.setString(4, firstName);
			stmt.setString(5, email);
			stmt.setString(6, "Customer");
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean apply(int userID) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO AccountStatus VALUES (?, ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userID);
			stmt.setString(2, "Pending");
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean withdraw(int userID, double amount) {
		float amt = (float) amount;
		float balance = getBalance(userID);
		float newBalance = balance - amt;
		if (balance < 0) {
			return false;
		}
		return updateBalance(userID, newBalance);
	}

	@Override
	public boolean deposit(int userID, double amount) {
		float amt = (float) amount;
		float balance = getBalance(userID);
		float newBalance = balance + amt;
		return updateBalance(userID, newBalance);
	}

	@Override
	public List <Customer> ViewCustomers() {
		List <Customer> Customers = new ArrayList<>();
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT accounts.userid, balance, status, username, firstname, lastname, email FROM accounts INNER JOIN users on accounts.userid = users.userid;";
			stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Customer Customer = new Customer();
				Customer.setUserID(rs.getInt("userID"));
				Customer.setBalance(rs.getFloat("balance"));
				Customer.setStatus(rs.getString("status"));
				Customer.setUsername(rs.getString("username"));
				Customer.setFirstName(rs.getString("firstName"));
				Customer.setLastName(rs.getString("lastName"));
				Customer.setEmail(rs.getString("email"));
				Customers.add(Customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return Customers;
	}
	
	@Override
	public boolean approve(int userID, String account_type) {
		boolean updatedStatus = updateStatus(userID, "Open");
		if (!updatedStatus) {
			return false;
		}
		float balance = 0;
		try {
			connection = DAOUtilities.getConnection();
			String sql = "INSERT INTO Accounts VALUES (?, ?, ?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userID);
			stmt.setFloat(2, balance);
			stmt.setString(3, "Open");
			stmt.setString(4, account_type);
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean deny(int userID) {
		return updateStatus(userID, "Denied");
	}

	@Override
	public boolean cancel(int userID) {
		boolean updatedStatus = updateStatus(userID, "Closed");
		if (!updatedStatus) {
			return false;
		}
		try {
			connection = DAOUtilities.getConnection();
			String sql = "DELETE FROM accounts WHERE userID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userID);
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

	@Override
	public int generateNewPrimaryKey() {
		int PK;
		int userKey = getGreatestPrimaryKey("users");
		int acctStatusKey = getGreatestPrimaryKey("users");
		if (userKey>acctStatusKey) {
			PK = userKey;
		}
		else {
			PK = acctStatusKey;
		}
		PK++;
		return PK;
	}

	@Override
	public int getGreatestPrimaryKey(String table_name) {
		List<Integer> PrimaryKeys = new ArrayList<>();
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT userID FROM ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, table_name);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				PrimaryKeys.add(rs.getInt("userID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return Collections.max(PrimaryKeys);
	}

	@Override
	public float getBalance(int userID) {
		float Balance = 0;
		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT balance FROM Accounts WHERE (userID = ?)";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, userID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Balance = (rs.getFloat("balance"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		return Balance;
	}

	@Override
	public boolean updateBalance(int userID, float newBalance) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE Accounts SET balance = ? WHERE userID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setFloat(1, newBalance);
			stmt.setInt(2, userID);
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}

	@Override
	public boolean updateStatus(int userID, String newStatus) {
		try {
			connection = DAOUtilities.getConnection();
			String sql = "UPDATE AccountStatus SET status = ? WHERE userID = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, newStatus);
			stmt.setInt(2, userID);
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			closeResources();
		}
	}
}
