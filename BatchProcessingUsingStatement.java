import java.sql.*;
import java.util.Scanner;

public class BatchProcessingUsingStatement {
    private static final String url = "jdbc:postgresql://localhost:5432/demo";
    private static final String uName = "postgres";
    private static final String pass = "Raheel@786";

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        Connection con = null;
        Statement st = null;
        try {
            Class.forName("org.postgresql.Driver");
            con = DriverManager.getConnection(url, uName, pass);
            st = con.createStatement();

            //Inserting Data into students table which is already created in our ConnectingToDataBaseAndCreateTable.java

            while (true) {
                System.out.println("Enter the id : ");
                int id = s.nextInt();
                System.out.println("Enter the name : ");
                String name = s.next();
                System.out.println("Enter the marks : ");
                int marks = s.nextInt();
                String query = String.format("INSERT INTO students(id, name, marks) VALUES (%d, '%s', %d)", id, name, marks);
                st.addBatch(query);
                System.out.println("Enter the choice (Y|N)? : ");
                String choice = s.next();
                char ch = choice.charAt(0);
                if (ch!='Y'&& ch!='y') {
                    break;
                }
            }
            //Executing the entire batch at a time and execute batch returns array with index values
            //1 or O 1 means query executed successfully 0 means query failed to execute.
            int[] rows = st.executeBatch();
            for(int i=0;i<rows.length;i++){
                if(rows[i]==1){
                    System.out.println("query " + (i+1) + " executed successfully.");
                }else{
                    System.out.println("query " + (i+1) + " failed to execute.");
                }
            }

            // Updating data in the students table

            System.out.println("Enter the Student name whose marks need to be changed : ");
            String sname = s.next();
            System.out.println("Enter updated marks : ");
            int updatedMarks = s.nextInt();
            String udpateQuery = String.format("UPDATE students SET marks=%d where name ='%s' ", updatedMarks, sname);
            int updated = st.executeUpdate(udpateQuery);
            if (updated > 0) {
                System.out.println("Data updated succesfully");
            } else {
                System.out.println("Updation failed");
            }

            // Deleting data from the students table

            System.out.println("Enter the Student id to delete that student record : ");
            int deleteId = s.nextInt();
            String deleteQuery = String.format("DELETE from students where id = %d", deleteId);
            int deleted = st.executeUpdate(deleteQuery);
            if (deleted > 0) {
                System.out.println("Data deleted succesfully");
            } else {
                System.out.println("Deletion failed");
            }

            String allRecords = "SELECT * FROM students";
            ResultSet rs = st.executeQuery(allRecords);
            while (rs.next()) {
                System.out.println(rs.getInt("id") +
                        " " + rs.getString("name") +
                        " " + rs.getInt("marks"));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver not found.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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