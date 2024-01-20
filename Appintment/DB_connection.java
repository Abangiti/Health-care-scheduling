
package health;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;

public class DB_connection {
       Connection con=null;
     
    public static Connection ConnectionDB(){
       try{
             Class.forName("org.sqlite.JDBC");
             Connection con = DriverManager.getConnection("jdbc:sqlite:Java_p.db");
            System.out.println("connection sucess");
            return con;}
         
     catch(Exception e){
         System.out.println("connection failed" + e);
     return null;
     }
        
    }
    
}
