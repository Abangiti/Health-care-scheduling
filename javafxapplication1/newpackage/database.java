
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
public class database {


    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myshop", "root", "sorry4998$");
            System.out.println("Connection success");
            return con;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Connection failed: " + e);
            return null;
        }
    }
}
    








   
        
    
