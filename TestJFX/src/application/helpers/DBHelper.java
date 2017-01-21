package application.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	
	private static Connection conn;
	
	public static void DBinit() {
		
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sql = "CREATE TABLE IF NOT EXISTS tasks (\n"
                + "	id INTEGER PRIMARY KEY UNIQUE,\n"
                + "	summary TEXT NOT NULL,\n"
                + "	description TEXT NOT NULL,\n"
                + " due_date TEXT NOT NULL"
                + ");";
        
        try {
        	conn = DriverManager.getConnection("jdbc:sqlite:MySQLiteDB.db");
        	Statement stmt = conn.createStatement(); 
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
        	try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}

}
