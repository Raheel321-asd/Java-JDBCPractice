import java.sql.*;
import java.util.Scanner;

class ConnectingToDataBaseAndCreateTable{
    private static final String url = "jdbc:postgresql://localhost:5432/databasename";
    private static final String uName = "postgres";
    private static final String pass = "Raheel#123";

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Connection con = null;
        Statement st = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, uName, pass);
            st = con.createStatement();
            String sql1 = "CREATE TABLE students(id INTEGER PRIMARY KEY, name TEXT NOT NULL, marks INTEGER NOT NULL)";
            st.executeUpdate(sql1);
            System.out.println("Table 'students' created successfully.");
        } catch (SQLException e) {
            // SQLState is 5 characters code which tells exact reason for the SQL error.
            if (e.getSQLState().equals("42P07")) {
                System.out.println("Table = students already exists in the database.");
            } else {
                System.out.println("SQL Exception: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
        } finally {
            try {
                if (st != null) st.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error occurred while closing resources: " + e.getMessage());
            }
        }
    }
}

