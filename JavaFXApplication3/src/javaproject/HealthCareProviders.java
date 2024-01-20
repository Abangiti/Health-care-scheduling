package javaproject;
 
 
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.sql.ResultSet;


public class HealthCareProviders {
      Connection connection=DbConnection.getConnection(); 
      ResultSet result;
      PreparedStatement ps ; 
      
      Alert alertI = new Alert(AlertType.INFORMATION);
      Alert alertE = new Alert(AlertType.ERROR);
    private String Fname;
    private String Lname;
    private String profession;
    private String phone;
    private String username;
    private String password;
    private String gender;
    private int yearsOfExperience;
        //default constructor
            public HealthCareProviders(){}
        //parametrized constructor
             public HealthCareProviders(String Fname,String Lname,String profession,String phone,String username,String password) {
        this.Fname = Fname;
        this.Lname = Lname;
        this.profession = profession;
        this.phone = phone;
        this.username = username;
        this.password = password;
             } 
            //getter methods
            public String getFname(){
                return Fname;
            }
             public String getLname(){
                return Lname;
            }
             public String getProfession(){
                return profession;
            }
              public String getphone(){
                return phone;
            }
                public String getusername(){
                return username;
            }
                public String getpassword(){
                return password;
            }
                  public String getgender(){
                return gender;
            }
                   public int getyearsOfExperience(){
                return yearsOfExperience;
            }
                
             //setter
               public void setFname( String Fname){
               this.Fname=Fname;
            }
                public void setLname(String Lname){
                this.Lname=Lname;
            }
               public void setProfession(String profession){
                this.profession=profession;
            }
               public void setphone(String phone){
               this.phone=phone;
            }
                 public void setusername(String username){
               this.username = username;
            }
                    public void setpassword(String password){
               this.password = password;
            }
                    public void setgender(String gender){
               this.gender = gender;
            }
                    public void setyearsOfExperience(int yearsOfExperience){
               this.yearsOfExperience = yearsOfExperience;
            }
              
           //methode to insert data into database table 
    public void insertData(){
    
        try{
            String sql="INSERT INTO  hopehealthcare (F_name, L_name, profession, phone, username , password , gender ,year_of_experience ) VALUES(?,?,?,?,?,?,?,?)";
               if (connection != null) {
            ps=connection.prepareStatement(sql);
            
            ps.setString(1, Fname);
            ps.setString(2, Lname);
            ps.setString(3, profession);
            ps.setString(4, phone);
            ps.setString(5, username);
            ps.setString(6, password);
            ps.setString(7, gender);
            ps.setInt(8, yearsOfExperience);
            ps.execute();
            System.out.println("Data has been inserted!");
             }else {
    System.out.println("Connection is null. Unable to prepare statement.");
}
            
        }catch(SQLException e){
            System.out.print(e.toString());
        }  
        finally{
   closeResources();
     }
        // an alert to be shown for the user 
        alertI.setTitle("Information Dialog");
        alertI.setHeaderText(null);
        alertI.setContentText("Your Registration is Sccessfully Done!");
         alertI.showAndWait();
        //creating table for the registered care provider
          createTable(username);
          
    }
    
      //method to check if the user name is already found
     public boolean isUsernameFound(String un){
           boolean isFound = false;
           try{
            String sql3 = "SELECT * FROM HopeHealthCare WHERE username= ? ";
            
            ps= connection.prepareStatement(sql3);
            
            ps.setString(1,un);
      
            result = ps.executeQuery();
           
            if(result.next()){
           isFound= true;
                
            }else{
                  isFound = false;
            }
            
        }catch (SQLException e){
            System.out.println("Error: Connection Failed!");
        }
         finally{
   closeResources();
     }
       return isFound;
     }
    
    
    
    // method to handle login after login button clicked with the database
    public boolean handleLogin( String checkun, String checkpass){
        boolean isLogin = false;
    
        try{
            String sql2 = "SELECT * FROM HopeHealthCare WHERE username= ? AND password= ?";
            
            ps= connection.prepareStatement(sql2);
            
            ps.setString(1, checkun);
            ps.setString(2, checkpass);
            
            result = ps.executeQuery();
            
            //set alert to inform the user if login is failed
        
        
            if(result.next()){
           isLogin= true;

            }else{
                 alertE.setTitle("ERROR");
                 alertE.setHeaderText(null);
                 alertE.setContentText("Login Failed! Incorrect username or password!");
                 alertE.showAndWait();
                 
                 isLogin = false;
            }
            
        }catch (SQLException e){
            System.out.println("Error: Connection Failed!");
        }
         finally{
   closeResources();
     }
       return isLogin;  
    }
    
    
    // A method to create a table for the registered health care providers
       private void createTable(String tableName) {
             Statement statement;
           try{  
               // SQL query to create the table
              String createTableQuery = "CREATE TABLE " + tableName + " ("
                      + "id INT PRIMARY KEY AUTO_INCREMENT,"
                      + "timeInterval VARCHAR(20),"
                      + "monday VARCHAR(20),"
                      + "tuesday VARCHAR(20),"
                      + "wednesday VARCHAR(20),"
                      + "thursday VARCHAR(20)," 
                      + "friday VARCHAR(20),"
                      + "saturday VARCHAR(20),"
                      + "sunday VARCHAR(20)"
                      + ")"; 
               if (connection != null) {
              statement = connection.createStatement(); 
              // Execute the query
              statement.executeUpdate(createTableQuery);
              // Close the Statement
                statement.close();
          }else
                   System.out.println("Unable tomprepare statement!");
               
           }catch (SQLException e){
               // e.printStackTrace();
                System.out.println("Error: Connection Failed!"); 
          }
            finally{
   closeResources();
     }
      
      }
   
       
       //method to handle delete button with the databese
   public void delete(){
        
       try{
           String sql="DELETE FROM HopeHealthCare WHERE username=? ;";
        ps= connection.prepareStatement(sql);
         ps.setString(1,getusername());
         ps.execute();
        System.out.print("sucessfully deleted!");
       }
       catch(SQLException e){
    System.out.print("failed delete" + e);
    
}
 finally{
   closeResources();
     }     
   }
  
   
 
   //method to handle getPersonalInformation with database
    public String getPersonalInformation() {
              
        String personalInfo = "";

        String query = "SELECT  F_name, L_name, profession, phone ,password ,year_of_experience FROM HopeHealthCare WHERE username = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, getusername());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                  
                    String firstName = resultSet.getString("F_name");
                    String lastName = resultSet.getString("L_name");
                    String professiong = resultSet.getString("profession");
                    String phoneg = resultSet.getString("phone");
                    String passwordg = resultSet.getString("password");
                    int yearofexperienceg = resultSet.getInt("year_of_experience");

                   
                    personalInfo =  firstName + "\n"+ lastName +"\n"+  professiong +"\n"+  phoneg +   "\n" + passwordg +  "\n"+ yearofexperienceg ;
                            
                } 
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
 finally{
   closeResources();
     }
        return personalInfo;
    }

    
    //Method to handle EDIT button with the database
    public void updatePersonalInformation() {
           
           try   {
    String query = "UPDATE HopeHealthCare SET "
            + "F_name = ?, "
            + "L_name = ?, "
            + "profession = ?, "
            + "phone = ?,"
            + "password=? ,"
            + "year_of_experience=? "
            + "WHERE username LIKE ?";
         ps = connection.prepareStatement(query);
    
        ps.setString(1, Fname);
        ps.setString(2, Lname);
        ps.setString(3, profession);
        ps.setString(4, phone);
        ps.setString(5, password);
        ps.setInt(6, yearsOfExperience);
        ps.setString(7, username);
        
        ps.executeUpdate();
         System.out.print("sucessful update!");

       
    } catch (SQLException e) {
          System.out.print("Update Failed!");
        e.printStackTrace(); // Handle the exception appropriately
    }
     
     finally{
   closeResources();
     }
}
    
    //method to close database 
   private void closeResources(){
            try{
                if(result!=null){
                    result.close();                  
                }
                if(ps!=null){
                    ps.close();
                }                
            }catch(Exception e){
                System.out.print("error closing"+e.getMessage());
            }
        }
 }
       



