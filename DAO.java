package myBank;

import myBank.Customer;
import java.util.List;

public interface DAO {

	//get what the primary key should be of a record you want to create. called once by register()
	public int generateNewPrimaryKey();
	
	//get the highest primary key in a table. this is called twice in generateNewPrimaryKey()
	public int getGreatestPrimaryKey(String table_name);
	
	//get account's current balance. used in withdraw() and deposit() for calculations
	public float getBalance(int userID);
	
	//update account's balance. used in withdraw() and deposit()
	public boolean updateBalance(int userID, float balance);
	
	//register with a username and password
	public boolean register(String username, String pass_word, String firstName, String lastName, String email);
	
	//update account status. used by approve(), deny(), and cancel()
	public boolean updateStatus(int userID, String newStatus);
	
	//apply to open an account
	public boolean apply(int userID);
	
	//withdraw funds
	public boolean withdraw(int userID, double amount);
	
	//deposit funds
	public boolean deposit(int userID, double amount);
	
	//view all of their customers information.
	//This includes, account information, account balances, & personal information
	public List <Customer> ViewCustomers();
	
	//approve applications for accounts
	public boolean approve(int userID, String account_type);
	
	//deny applications for accounts
	public boolean deny(int userID);
	
	//canceling accounts
	public boolean cancel(int userID);
}
