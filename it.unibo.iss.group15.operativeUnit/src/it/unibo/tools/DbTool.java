package it.unibo.tools;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import model.implementations.parameters.Parameters;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DbTool {

	private Connection conn;
	
	public void setConnection(String dbUrl, Properties properties) throws SQLException{
		this.conn = (Connection) DriverManager.getConnection(dbUrl, properties);
	}
	
	public void CreateDBIfNotExists(String dbName) throws SQLException{
		Statement stmt = (Statement) conn.createStatement();
		String query = "CREATE DATABASE IF NOT EXISTS " + dbName + ";";
		stmt.executeUpdate(query);
	}
	
	public boolean CheckIfTableExists(String tableName) throws SQLException{
		Statement stmt = (Statement) conn.createStatement();
		String query = "SHOW TABLES LIKE '" + tableName + "';";
		ResultSet result = stmt.executeQuery(query);
		
		return result.next();
	}
	
	public void Execute(String query) throws SQLException{
		Statement stmt = (Statement) conn.createStatement();
		stmt.execute(query);
	}
	
	public ResultSet ExecuteQuery(String query) throws SQLException {
		Statement stmt = (Statement) conn.createStatement();
		return stmt.executeQuery(query);
	}
	
	public void InsertInto(String tableName, String columnNames, String values) throws SQLException{
		String query = "INSERT INTO " + tableName + " (" + columnNames + ") " +
					   " VALUES( " + values + ");";
		this.Execute(query);
	}
}
