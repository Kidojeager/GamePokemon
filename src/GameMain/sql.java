package GameMain;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

	public class sql {

		static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost/sql1";
		static final String USER = "root";
		static final String PASS = "";
		private static Statement stmt;

		public static void main(String[] args) throws SQLException, ClassNotFoundException {
			Connection conn = null;
			Statement stmt = null;
			try {
				System.out.println("STEP 1: Register JDBC driver");
				Class.forName(DRIVER_CLASS);

				System.out.println("STEP 2: Open a connection");
				conn = DriverManager.getConnection(DB_URL,USER,PASS);

				System.out.println("STEP 3: Execute a query");
				String query = "Select * FROM leaderboard";
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);

				System.out.println("STEP 4: Extract data from result set");
				while (rs.next()) {
					int score = rs.getInt("score");
					String name = rs.getString("name");

					System.out.println("name: " + name);
					System.out.println("Score: " + score);
					

				}
				rs.close();
			} finally {
				System.out.println("STEP 5: Close connection");
				if (stmt != null)
				stmt.close();
				if (conn != null)
				conn.close();
				} 
				System.out.println("Done!");
				}
		//-----------------------------------------------
		public static void addToDatabase(Connection conn, String name, int score) throws SQLException {
		    PreparedStatement ps = null;
		    try {
		        String query = "INSERT INTO leaderboard (name, score) VALUES (?, ?)";
		        ps = conn.prepareStatement(query);
		        ps.setString(1, name);
		        ps.setInt(2, score);
		        ps.executeUpdate();
		        System.out.println("Record inserted successfully");
		    } finally {
		        if (ps != null)
		            ps.close();
		    }
		}
		
}
