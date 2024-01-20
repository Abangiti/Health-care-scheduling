 package javaproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

 
public class DbConnection {
   
    //static method for connection
       public static Connection getConnection(){
         Connection con;
         try{
             Class.forName("com.mysql.cj.jdbc.Driver");
             con=  DriverManager.getConnection("jdbc:mysql://localhost:3306/ hopeappointmet", "root", "11am22bm");
              System.out.println("Connected");
              
              return con;
         } catch(ClassNotFoundException | SQLException e){
           
             System.out.println("connection failed"+e);
        return null;
         } 
       }
       
       
}

 