/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxapplication1.newpackage;
import java.sql.*;

/**
 *
 * @author user
 */
public class schedule {
Connection con;
PreparedStatement pst;
    public schedule() {
        con=database.getConnection();
    }

    public ResultSet createTable(String username) {
         ResultSet resultSet = null;
        try {
           String query = "SELECT * FROM " + username;
            pst = con.prepareStatement(query);
             resultSet = pst.executeQuery();
            return resultSet; 

      
        } catch (Exception e) {
            e.printStackTrace(); 
            
// Handle the exception appropriately in your application
        }
        
        return resultSet;
    }

    public boolean checkOccupancy(String username, String timeInterval, String day) {
        try {
            String query = "SELECT " + day + " FROM " + username +
                    " WHERE time_interval = ?";

            pst = con.prepareStatement(query);
            pst.setString(1, timeInterval);

            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(day) == 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateTable(String username, String timeInterval, String dayToReschedule, String newDay, String newTimeInterval) {
        try {
            boolean isOccupied = checkOccupancy(username, timeInterval, dayToReschedule);

            if (isOccupied) {
                String resetQuery = "UPDATE " + username +
                        " SET " + dayToReschedule + " = 0" +
                        " WHERE time_interval = ? AND " + dayToReschedule + " = 1";

                pst = con.prepareStatement(resetQuery);
                pst.setString(1, timeInterval);

                int resetRowsAffected = pst.executeUpdate();

                if (resetRowsAffected > 0) {
                    System.out.println("Reset successful. Rows affected: " + resetRowsAffected);
                } else {
                    System.out.println("No rows were reset. Check if the specified row exists or the time is already free.");
                    return;
                }
            }

            String updateQuery = "UPDATE " + username +
                    " SET " + newDay + " = 1" +
                    " WHERE time_interval = ?";

            pst = con.prepareStatement(updateQuery);
            pst.setString(1, newTimeInterval);

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Update successful. Rows affected: " + rowsAffected);
            } else {
                System.out.println("No rows were updated. Check if the specified row exists or the time is already occupied.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void closeResources() {
        try {
            if (pst != null) {
                pst.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Log or handle the exception appropriately
        }
    }
}
    

    

