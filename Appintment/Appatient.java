
package health;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Appatient {
    
	  private int pID;
	  private String dateapp;
	  private int timeapp;
	  private String username;
           Connection con=null;
           PreparedStatement pst=null;
           ResultSet rs=null;
	  //default constructor
	  public Appatient() {
          con=DB_connection.ConnectionDB();
              
          }
        
	   
	  //setter methods
	  public void setpID(int appID) {
		  this.pID=appID;
	  }
	  public void setdateapp(String dateapp) {
		  this.dateapp=dateapp;
	  }
	  public void settimeapp(int timeapp) {
		  this.timeapp=timeapp;
	  }
	  public void setusername(String username) {
		  this.username=username;
	  }
	  
	  //getter methodes
	  public int pID() {
		  return pID;
	  }
	  public String dateapp() {
		  return dateapp;
	  }
	  public int timeapp() {
		  return timeapp;
	  }
	  public String username() {
		  return username;
	  }
  
         public ResultSet displayTable(String username) 
         { ResultSet resultSet = null; 
         try { String query = "SELECT * FROM " + username; 
         pst = con.prepareStatement(query); 
         resultSet = pst.executeQuery(); 
         return resultSet; 
         } catch (Exception e) 
         { e.printStackTrace();  
         } return resultSet; }
         
          public ResultSet displayTable2() 
         { ResultSet resultSet = null; 
         try { String query = "SELECT * FROM " + ""; 
         pst = con.prepareStatement(query); 
         resultSet = pst.executeQuery(); 
         return resultSet; 
         } catch (Exception e) 
         { e.printStackTrace();  
         } return resultSet; }
         
         
public void Add() {
    try {
        // Check the current number of appointments for the patient
        String checkSql = "SELECT COUNT(*) AS appointmentCount FROM patientinfokoriea WHERE username = ?";
        try (PreparedStatement checkPst = con.prepareStatement(checkSql)) {
            checkPst.setString(1, username);
            try (ResultSet checkRs = checkPst.executeQuery()) {
                if (checkRs.next()) {
                    int appointmentCount = checkRs.getInt("appointmentCount");
                    if (appointmentCount >= 2) {
                        System.out.println("Patient already has 2 appointments. Cannot add more.");
                        return;
                    }
                }
            }
        }

        // If the patient has fewer than 2 appointments, proceed with adding the new appointment
        String sql = "UPDATE patientinfokoriea SET username=?, appdate=?, apptime=? WHERE pid LIKE ?; ";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, dateapp);
            pst.setInt(3, timeapp);
            pst.setInt(4, pID);
            pst.execute();
            System.out.println("Appointment added successfully.");
        }
        
        String usernames = username;
        String day = dateapp;
        String sqll = "UPDATE " + usernames + " SET " + day + " = 1 WHERE time LIKE ?;";
        try (PreparedStatement pst = con.prepareStatement(sqll)) {
            pst.setInt(1, timeapp);
            pst.execute();
            System.out.println("Updated table.");
        }
    } catch (Exception f) {
        System.out.println("Failed to add appointment: " + f.getMessage());
    } finally {
        closeResources();
    }
}

   
		   //....
	     
               
	public void update() {
 String usernames="null";
String appDates="null";
String appTimes="null";
    try {
        String sql = "SELECT username, appdate, apptime FROM patientinfokoriea WHERE pid = ?";
        pst = con.prepareStatement(sql);
        pst.setInt(1, pID);
        rs = pst.executeQuery();

        if (rs.next()) {
             usernames = rs.getString("username");
             appDates = rs.getString("appdate");
            appTimes = rs.getString("apptime");
        }

    } catch (Exception e) {
        System.out.println("Faile  " + e.getMessage());
    }
    
    try{ 
        String updateSql = "UPDATE "+ usernames  +" SET "+ appDates + " = 0 where time= "+ appTimes +" ;";
            PreparedStatement updatePst = con.prepareStatement(updateSql);
                    updatePst.execute();
        System.out.println("updated the previous one");
    }catch(Exception e){
    System.out.println("fail pre"+e);
    }
    finally{
        closeResources();
    }
         try{
           String sql="update patientinfokoriea SET username=?,appdate=?,apptime=? where pid like ?; ";
           pst = con.prepareStatement(sql);
           pst.setString(1,username);
            pst.setString(2,dateapp);
            pst.setInt(3,timeapp);
            pst.setInt(4, pID );
          pst.execute();
          System.out.println("update");
   }                                        
 catch(Exception f){
    
     System.out.print("notupdate" + f);
    }finally{
               closeResources();
             
         }
         
    
   
        try{
             String sqll="update " + usernames + " set " + appDates +" = 1 where time like ?;";
           pst = con.prepareStatement(sqll);
           //pst.setString(1,usernames);
            //pst.setString(2,dateapp);
           pst.setInt(1,timeapp);
           // pst.setInt(4, pID );
          pst.execute();
          System.out.println("updatedtable n");
    }                                        
 catch(Exception f){
    
     System.out.print("notupdatetable" + f);
   }
    
    
}
        public void cancel() {
	       String sql="update patientinfokoriea SET username=null,appdate=null,apptime=null where pid like ?; ";

               try{
           pst = con.prepareStatement(sql);
           //pst.setString(1,username);
           // pst.setString(2,dateapp);
           // pst.setInt(3,timeapp);
             pst.setInt(1, pID );
           pst.execute();
          System.out.print("canceled");
    }                                        
 catch(Exception f){
     
      System.out.print("notcancled" + f);
    }
       String usernames=username;
       String day=dateapp;
       String sqll="update " + usernames + " set " + day+" = 0 where time like ?;";
         try{
           pst = con.prepareStatement(sqll);
           //pst.setString(1,usernames);
            //pst.setString(2,dateapp);
            pst.setInt(1,timeapp);
            // pst.setInt(4, pID );
           pst.execute();
          System.out.print("deletetable");
    }                                        
 catch(Exception f){
     
      System.out.print("deletedtable" + f);
    }
}
        public void rescheduleAppointment() {
	       //....
}
	   //....
        private void closeResources(){
            try{
                if(rs!=null){
                    rs.close();
                    
                }
                if(pst!=null){
                    pst.close();
                }
                
            }catch(Exception e){
                System.out.print("error closing"+e.getMessage());
            }
        }
        
	   }
       

    

