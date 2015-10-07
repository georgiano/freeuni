package ge.edu.freeuni.rdbcourse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCExample {

	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	private static final String JDBC_SERVER = "localhost:3306"; 
	private static final String JDBC_SCHEMA = "test"; 
	private static final String JDBC_USERNAME = "root"; 
	private static final String JDBC_PASSWORD = "root"; 

	private static String connectionString = "jdbc:mysql://" + JDBC_SERVER + "/" + JDBC_SCHEMA;

	private static void runExample() throws Exception {
	
		System.out.println("loading JDBC driver");
		Class.forName(JDBC_DRIVER).newInstance();
        
		System.out.println("connecting to database with connection string \"" + connectionString + "\"");
		Connection con = DriverManager.getConnection(
				connectionString, 
				JDBC_USERNAME, 
				JDBC_PASSWORD);
		
		System.out.println("preparing manufacturers query");
		Statement statManu = con.createStatement();
		
		System.out.println("executing manufacturers query");
		ResultSet rsManu = statManu.executeQuery("select name from Manufacturers"); 

		System.out.println("\nfetching data");
		System.out.println("-------------------------------------------------------------");
		while (rsManu.next()) {
			String name = rsManu.getString("name");
			System.out.println(name);
		}
		System.out.println("-------------------------------------------------------------");

		
		System.out.println("preparing products query");
		PreparedStatement statProducts = con.prepareStatement("select * from Products where price < ?");
		statProducts.setInt(1, 200);
		
		System.out.println("executing products query");
		ResultSet rsProducts = statProducts.executeQuery();

		System.out.println("\nfetching data");
		System.out.println("-------------------------------------------------------------");
		while (rsProducts.next()) {
			int code = rsProducts.getInt("code");
			String name = rsProducts.getString("name");
			double price = rsProducts.getDouble("price");
			
			System.out.println(code + "; " + name + "; "+ price);
		}
		System.out.println("-------------------------------------------------------------");

		System.out.println("closing objects");
		rsManu.close();
		rsProducts.close();
		statProducts.close();
		con.close();
	}
	
	public static void main(String[] args) throws Exception {
		runExample();
	}

}
