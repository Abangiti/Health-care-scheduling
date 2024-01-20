package TestPackage;
 
import javaproject.HealthCareProviders;
 
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Optional;
 

 public class MainFX extends Application {
//one stage
            Stage stage;
// three scenes
//scene one
    private Scene scene1;
    private TextField txtLoginUsername;
    private PasswordField loginPassword;
    private Button btnRegister ;
    private Button btnLogin;
//scene two
    private Scene scene2;
    private TextField txtFirstName;
    private TextField txtLastName;
    private TextField txtProfession;
    private TextField txtPhone;
    private TextField txtRegUsername;
    private PasswordField regPassword;
    private String gender;
    private TextField txtYearsOfExperience;
    private Button btnSubmit ; 
       // Create radio buttons for different gender options
        RadioButton btnRadioMale ;
        RadioButton btnRadioFemale ;
        RadioButton btnOther ;
//scene three
     private Scene scene3;
     boolean isLogin;
     private Scene scene3PersonalInfo;  
    private TextField editFirstName;
    private TextField editLastName;
    private TextField editProfession;
    private TextField editPhone;
    private TextField editPassword;
    private TextField editYr;
    // common variables for all scenes
      Button btnBack;
      Alert alertI = new Alert(Alert.AlertType.INFORMATION);
     Alert alertE = new Alert(Alert.AlertType.ERROR);
     Alert alertc = new Alert(Alert.AlertType.CONFIRMATION);
     HealthCareProviders newUser = new HealthCareProviders();
     //main method        
    public static void main(String[] args) {
        launch(args);
              }
    
    // start method to show the stage
    @Override
    public void start(Stage primaryStage) {
        stage=primaryStage;
        stage.setTitle("Korea Hospital");
          
        scene1=showLoginForm();
        scene2=showRegistrationForm();
      
        stage.setScene(scene1);
        stage.show();
    }
    
    
       //SCENE ONE
      //HealthCare Provider Login    
    public Scene showLoginForm(){
        FlowPane fpane=new FlowPane();
        GridPane grid = new GridPane();
      
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 25, 25, 30));
        
         // set the declared variables to accept user login inputs
        txtLoginUsername = new TextField();
        loginPassword = new PasswordField();
        btnLogin = new Button("Login");
        btnRegister = new Button("Sign Up");
       
        fpane.setAlignment(Pos.CENTER);
        fpane.getChildren().addAll(new Label("HealthCare Provider Login"));
        fpane.setStyle("-fx-font-weight: bold; -fx-font-size:16px;");
        fpane.setPadding(new Insets(20, 0, 0, 0));
     
        grid.add(new Label("Username:"), 0, 3);
        grid.add(txtLoginUsername, 1, 3);
        grid.add(new Label("Password:"), 0, 4);
        grid.add(loginPassword, 1, 4);
        VBox vbox= new VBox();
        vbox.getChildren().addAll(fpane,grid,getHBox1());
     
   
     // Set action for the login and Register button
         btnLogin.setOnAction(btnHandleLogin);
         btnRegister.setOnAction(e -> switchScenes(scene2));
         
        scene1 = new Scene(vbox, 400, 300);
        
        return scene1;  
    }
    
    //SCENE TWO
   //HealthCare Provider Registration
    private Scene showRegistrationForm() {
        //panes used in this scene two
        FlowPane fPane = new FlowPane();
        GridPane grid = new GridPane();
        VBox vbox = new VBox(15); 
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(5, 5, 10, 5));
        HBox hbox = new HBox(15);
        
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(15, 25, 25, 25));

          //instantiate variables to accept user registration inputs
        txtFirstName  = new TextField();
        txtLastName  = new TextField();
        txtProfession  = new TextField();
        txtPhone  = new TextField();
        txtRegUsername  = new TextField();
        regPassword  = new PasswordField();
        
          // Create radio buttons for different gender options
         btnRadioMale = new RadioButton("Male");
         btnRadioFemale = new RadioButton("Female");
          btnOther  = new RadioButton("Other");
      
        txtYearsOfExperience= new TextField();
        
        // Add form fields for additional details to display
        fPane.getChildren().add(new Label("HealthCare Providers Registration"));
        fPane.setStyle("-fx-font-weight:bold; -fx-font-size:10px;");
        fPane.setPadding(new Insets(20, 0, 0, 0));
     
        fPane.setAlignment(Pos.CENTER);
        fPane.setStyle("fx-font-weight: bold;");
        grid.add(new Label("First Name:"), 0, 1);
        grid.add(txtFirstName , 1, 1);
        grid.add(new Label("Last Name:"), 0, 2);
        grid.add(txtLastName, 1, 2);
        grid.add(new Label("Profession:"), 0, 3);
        grid.add(txtProfession, 1, 3);
        grid.add(new Label("Phone:"), 0, 4);
        grid.add(txtPhone, 1, 4);
        grid.add(new Label("Username:"), 0, 5);
        grid.add(txtRegUsername, 1, 5);
        grid.add(new Label("Password:"), 0, 6);
        grid.add(regPassword , 1, 6);
        grid.add(new Label("Gender:"), 0, 7);
        grid.add(btnRadioMale, 1, 7);
        grid.add(btnRadioFemale, 1, 8);
        grid.add(btnOther, 1, 9);
        grid.add(new Label("Years of Experience:"),0,10);
        grid.add(txtYearsOfExperience, 1, 10); 
        
        btnSubmit = new Button("Register");
        btnBack  = new Button("Back");
        btnSubmit.setOnAction(e -> handleRegistration());
        btnBack.setOnAction(e -> switchScenes(scene1));
        
    
       hbox.setPadding(new Insets(0, 0, 15, 150));
       hbox.getChildren().addAll( btnSubmit, btnBack);
         
        vbox.getChildren().addAll(fPane,grid,hbox);

        //control the size of pop up page
        scene2 = new Scene(vbox, 400, 520);
       
        return scene2;
    }

//Includes SCENE THREE  
//method to handle login button click
         EventHandler<ActionEvent> btnHandleLogin = new EventHandler<ActionEvent>() {
               
            @Override
            public void handle(ActionEvent event){
                    alertE.setTitle(" Error Dialog");
                
          if(txtLoginUsername.getText().equals("")){
               alertE.setHeaderText(null);
              alertE.setContentText("Please fill out username!");
            alertE.showAndWait(); 
            
          }else if(loginPassword.getText().equals("")){
               alertE.setHeaderText(null);
              alertE.setContentText("Please fill out password!");
            alertE.showAndWait();   
          }else     
        isLogin= newUser.handleLogin(txtLoginUsername.getText(), loginPassword.getText());
       
          if(isLogin){
        //  SCENE THREE
           VBox vbox= new VBox(15);   
          vbox.setAlignment(Pos.TOP_CENTER);
          vbox.setPadding(new Insets(15, 5, 10, 5));
          
        Button personalInfo = new Button("Personal Information");
        Button schedule = new Button("Schedule");
        vbox.getChildren().addAll(new Label("Hello  "+ txtLoginUsername.getText()),personalInfo,schedule);
       // Label.setStyle("-fx-font-weight: bold;");
        //onclick personal information
           personalInfo.setOnAction(e -> handlePersonalInformation());
          
        scene3 = new Scene(vbox, 300, 200);
        switchScenes(scene3);
            }
             
    }
         };  
    
    
    
     //METHODS
    //method for aligning login and registration button
      public HBox getHBox1(){ 
       HBox hbox= new HBox(20);
       hbox.setAlignment(Pos.TOP_RIGHT);
       hbox.setPadding(new Insets(15, 180, 15, 0));
       hbox.getChildren().add(btnLogin);
       hbox.getChildren().add(btnRegister);
        
        return hbox;
} 
      
    //method to switch between scene
    public void switchScenes(Scene scene){
        stage.setScene(scene);
    }
 
    //mehtod to handle the register button
    private void handleRegistration() { 
        //gives alert if one of the registration field is empty
            if( txtFirstName .getText().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out firstname!");
            alertE.showAndWait();
           }
            else if( txtLastName .getText().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out lastname!");
            alertE.showAndWait();
            
           }else if(txtProfession .getText().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out profession!");
            alertE.showAndWait();
            
           }else if( txtPhone .getText().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out phone!");
            alertE.showAndWait();
            
           }else if(txtRegUsername .getText().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out username!");
            alertE.showAndWait();
            
           }else if(newUser.isUsernameFound( txtRegUsername .getText())==true){
                alertE.setTitle("ERROR");
                 alertE.setHeaderText(null);
                 alertE.setContentText("Username Already Exist! Please Enter another username!");
                 alertE.showAndWait();
                 
                } else if( regPassword .getText().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out password!");
            alertE.showAndWait();
            
           }else if( testGender().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out gender!");
            alertE.showAndWait();
            
           }else if( txtYearsOfExperience.getText().equals("")){
                alertE.setHeaderText(null);
              alertE.setContentText("Please fill out Years of experience!");
            alertE.showAndWait();
            
           }
        
           else{   
        //using setter methodes from the healthcareproviders class
        
         newUser.setFname( txtFirstName .getText());
         newUser.setLname( txtLastName .getText());
         newUser.setProfession(txtProfession .getText());
         newUser.setphone(txtPhone .getText()); 
         newUser.setpassword(regPassword .getText());
         newUser.setgender(testGender());
         newUser.setyearsOfExperience(Integer.parseInt(txtYearsOfExperience.getText()));
    
         newUser.insertData();
           }
      }
 

    // Handle personal information retrieval and editing
    private void handlePersonalInformation() {
        GridPane grid= new GridPane(); 
        grid.setVgap(5);
       newUser.setusername(txtLoginUsername.getText());
        
     // Retrieve personal information
        String personalInfo = newUser.getPersonalInformation();

        // Parse the retrieved information (assuming a specific format)
        String[] infoArray = personalInfo.split("\n");

        // Extract details from the parsed information
        String firstName = infoArray[0].substring(infoArray[0].indexOf(":") + 1);
        String lastName = infoArray[1].substring(infoArray[1].indexOf(":") + 1);
        String profession = infoArray[2].substring(infoArray[2].indexOf(":") + 1);
        String phone = infoArray[3].substring(infoArray[3].indexOf(":") + 1);
        String passwordg = infoArray[4].substring(infoArray[4].indexOf(":") + 1);
        //extract substring from int value
        String info = infoArray[5];
        int index = info.indexOf(":");
        int yr = Integer.parseInt(info.substring(index + 1));

   
        // Create editable text fields for each piece of information
        
        editFirstName = new TextField(firstName);
        editLastName = new TextField(lastName);
        editProfession = new TextField(profession);
        editPhone = new TextField(phone);
     
        editPassword = new TextField(passwordg);
        //change value of yr to string to display on the text field
        editYr = new TextField(String.valueOf(yr));
        
       grid.add(new Label("Personal Information:"),0,0);
       grid.add(new Label("F_name:"), 0, 1);
       grid.add(editFirstName,  1, 1);
       grid.add(new Label("L_name:"), 0, 2);
       grid.add(editLastName,  1, 2);
       grid.add(new Label("profession:"), 0, 3);
       grid.add( editProfession,  1, 3);
       grid.add(new Label("phone:"), 0, 4);
       grid.add(  editPhone,  1, 4);
       grid.add(new Label("password:"), 0, 5);
       grid.add(  editPassword,  1, 5);
       grid.add(new Label("year_of_experience:"), 0, 6);
       grid.add(  editYr,  1, 6);
       
        // Create an Edit button
        Button btnEdit = new Button("Edit");
        btnEdit.setOnAction(e -> handleEditButtonClick());

        //create delete button
        Button btnDelete= new Button("Delete");
         btnDelete.setOnAction(e ->handleDelete());
        
        // Create a button to go back to the main menu
        btnBack = new Button("Back");
        btnBack.setOnAction(e -> switchScenes(scene3));

        // Create a VBox to hold the elements
        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(5, 5, 10, 5));
        HBox btnHbox= new HBox(15);
        btnHbox.getChildren().addAll(btnEdit , btnDelete, btnBack); 
        btnHbox.setPadding(new Insets(15, 0, 15, 120));
        //combine all elemnts in one vbox 
        vbox.getChildren().addAll(grid, btnHbox);

        // Create the scene for personal information
        scene3PersonalInfo = new Scene(vbox, 400, 400);
        switchScenes(scene3PersonalInfo);
    }

    // Handle the Edit button click
   private void handleEditButtonClick() {
    // Create a dialog to confirm edits
    alertc.setTitle("Edit Confirmation");
    alertc.setHeaderText(null);
    alertc.setContentText("Do you want to save the changes?");
    
    // Show the dialog and wait for the user's response
    Optional<ButtonType> result = alertc.showAndWait();

    // Check if the user clicked OK
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // call the method on this file Update the database with the edited information
        updatePersonalInformation(txtLoginUsername.getText());
         // Retrieve the updated personal information
        handlePersonalInformation();
    }
}
 

// Update the personal information in the database after EDIT is confirmed
    private void updatePersonalInformation(String username) {
             int yr=Integer.parseInt(editYr.getText()); 
        
            newUser.setusername(username);
            newUser.setFname(editFirstName.getText()); 
            newUser.setLname(editLastName.getText());
            newUser.setProfession(editProfession.getText()); 
            newUser.setphone(editPhone.getText());  
            newUser.setpassword(editPassword.getText()); 
            newUser.setyearsOfExperience(yr);  
            newUser.updatePersonalInformation();
    } 
    
    
    // handle delete button
    private void handleDelete() {
         alertc.setTitle("Confirmation");
            alertc.setHeaderText(null);
            alertc.setContentText("Are you sure? This will delete all your personal information.");
            
            // Show the dialog and wait for the user's response
    Optional<ButtonType> result = alertc.showAndWait();

    // Check if the user clicked OK
    if (result.isPresent() && result.get() == ButtonType.OK) {
        //call method to delete info
        newUser.setusername(txtLoginUsername.getText());
       newUser.delete();
       
            alertI.setTitle("Information");
            alertI.setHeaderText(null);
            alertI.setContentText("You have deleted your personal information!");
            
    }  
    }

  
 // method to return gender
     public String testGender( ){              
if ( btnRadioMale.isSelected()) {
    gender="male";
} else if (btnRadioFemale.isSelected()) {
   gender="female";
} else if (btnOther.isSelected()){
    gender="other";
}       
   return gender; 

 
    }
   
 

 }

