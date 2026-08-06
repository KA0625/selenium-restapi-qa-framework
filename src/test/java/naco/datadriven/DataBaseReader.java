package naco.datadriven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class DataBaseReader {
	
	public static List<String> getDatafromDB(String query) throws SQLException {

		/*String host = "localhost";
		String port = "3306";
		String databaseName = "naco";
		String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;*/
		//host.docker.internal

		//String url = "jdbc:mysql://localhost:3306/naco";
		String url = "jdbc:mysql://host.docker.internal:3306/naco";
		Connection con = DriverManager.getConnection(url, "root", "Selenium2025");
		Statement s = con.createStatement();
		
		ResultSet rs = s.executeQuery(query);
		
		List<String> states = new ArrayList<>();
	    while (rs.next()) {
	        states.add(rs.getString("StateCode"));
	    }
	    con.close();
	    return states;
	}
	


}
