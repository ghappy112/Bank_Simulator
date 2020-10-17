CREATE TABLE Users (
	userID int NOT NULL UNIQUE,
	username varchar(100) NOT NULL UNIQUE,
	pass_word varchar(100) NOT NULL,
	firstName varchar(100) NOT NULL,
	lastName varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	role_type varchar(100) NOT NULL,
	PRIMARY KEY(userID)
)
--
CREATE TABLE Accounts (
	userID int NOT NULL UNIQUE,
	balance float NOT NULL,
	status varchar(100) NOT NULL,
	account_type varchar(100) NOT NULL,
	PRIMARY KEY(userID)
)
--
CREATE TABLE AccountStatus (
	userID int NOT NULL UNIQUE,
	status varchar(100) NOT NULL,
	PRIMARY KEY(userID)
)
--
INSERT INTO users
(userID, username, pass_word, firstName, LastName, email, role_type)
VALUES (1, 'admin', 'admin123', 'Greg', 'Happ', 'greghapp700@gmail.com', 'Admin');
INSERT INTO users
(userID, username, pass_word, firstName, LastName, email, role_type)
VALUES (2, 'first_employee', 'onlyemployee', 'Ben', 'Bernanke', 'BigBen@gmail.com', 'Employee');
INSERT INTO users
(userID, username, pass_word, firstName, LastName, email, role_type)
VALUES (3, 'Doe', 'password', 'John', 'Doe', 'JDoe@gmail.com', 'Customer');
INSERT INTO users
(userID, username, pass_word, firstName, LastName, email, role_type)
VALUES (4, 'TopGun', 'TomCruise', 'Tom', 'Cruise', 'TomCruise@gmail.com', 'Customer');
INSERT INTO accounts
(userID, balance, status, account_type)
VALUES (3, 1000.00, 'Open', 'Savings');
INSERT INTO accounts
(userID, balance, status, account_type)
VALUES (4, 1000000.00, 'Open', 'Checking');
INSERT INTO accountstatus
(userID, status)
VALUES (3, 'Open');
INSERT INTO accountstatus
(userID, status)
VALUES (4, 'Open');
INSERT INTO accountstatus
(userID, status)
VALUES (5, 'Pending');