import java.sql.*;
import java.util.Scanner;

public class CRUDOperationsUsingPreparedStatement {
    private static final String url = "jdbc:postgresql://localhost:5432/demo";
    private static final String uName = "postgres";
    private static final String pass = "Raheel@786";

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Connection con = null;
        PreparedStatement pStCreate = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, uName, pass);
            String query = "INSERT INTO students(id, name, marks) VALUES (?, ?, ?)";
            pStCreate = con.prepareStatement(query);

            //Inserting Data into students table which is already created in our ConnectingToDataBaseAndCreateTable.java

            while (true) {
                System.out.println("Enter the id : ");
                int id = s.nextInt();
                System.out.println("Enter the name : ");
                String name = s.next();
                System.out.println("Enter the marks : ");
                int marks = s.nextInt();
                pStCreate.setInt(1,id);
                pStCreate.setString(2,name);
                pStCreate.setInt(3,marks);
                int rows = pStCreate.executeUpdate();
                if (rows > 0) {
                    System.out.println("Data inserted succesfully");
                } else {
                    System.out.println("Insertion failed");
                }
                System.out.println("Enter the choice (Y|N)? : ");
                String choice = s.next();
                 char ch = choice.charAt(0);
                if (ch!='Y'&& ch!='y') {
                    break;
                }
            }
            pStCreate.close();

            // Updating data in the students table

            String udpateQuery = "UPDATE students SET marks=? where name =?";
            PreparedStatement pStUpdate = con.prepareStatement(udpateQuery);
            System.out.println("Enter the Student name whose marks need to be changed : ");
            String sname = s.next();
            System.out.println("Enter updated marks : ");
            int updatedMarks = s.nextInt();
            pStUpdate.setInt(1,updatedMarks);
            pStUpdate.setString(2,sname);
            int updated = pStUpdate.executeUpdate();
            if (updated > 0) {
                System.out.println("Data updated succesfully");
            } else {
                System.out.println("Updation failed");
            }
            pStUpdate.close();

            // Deleting data from the students table

            String deleteQuery = "DELETE from students where id = ?";
            PreparedStatement pStDelete = con.prepareStatement(deleteQuery);
            System.out.println("Enter the Student id to delete that student record : ");
            int deleteId = s.nextInt();
            pStDelete.setInt(1,deleteId);
            int deleted = pStDelete.executeUpdate();

            if (deleted > 0) {
                System.out.println("Data deleted succesfully");
            } else {
                System.out.println("Deletion failed");
            }
            pStDelete.close();

            // Displaying All records from students table

            String allRecords = "SELECT * FROM students";
            PreparedStatement pStDisplay = con.prepareStatement(allRecords);
            ResultSet rs = pStDisplay.executeQuery();
            while(rs.next()){
                System.out.println(rs.getInt("id")+
                        " "+rs.getString("name")+
                        " "+ rs.getInt("marks"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (pStCreate != null) pStCreate.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error occurred while closing resources: " + e.getMessage());
            }
        }
    }

}
