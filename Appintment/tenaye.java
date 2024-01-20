import javafx.scene.Scene;
import javafx.scene.control.*;import javafx.application.Application;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import health.Appatient;

import java.sql.ResultSet;

public class tenaye extends Application {
Appatient sc=new Appatient();
Appatient s=new Appatient();
Appatient ss=new Appatient();
    private TextField txtpatientId = new TextField();
    private TextField txtdoc_username = new TextField();
    private TextField txtset_day = new TextField();
    private TextField txtset_time = new TextField();
    private Button btnadd = new Button("Add");
    private Button btnupdate = new Button("Update");
    private Button btndelete = new Button("Delete");
    private Button btnSee = new Button("next");
    private Button seeButton = new Button("disp");
    private Button viewButton = new Button("view");
     private Button backButton = new Button("back");
    private TextField usernameTextField = new TextField();
    private TableView<TableRowData> tableView = createTableView();
        private TableView<TableRowData2> tableView2 = createTableView2();

    private Stage stage;
    private BorderPane root;
    private ResultSet resultSet;

    @Override
    public void start(Stage primaryStage) {
        root = new BorderPane();
        stage = primaryStage;

        changeScene(createSceneTwo());
        displayDoc();

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setTitle("Appointment");
        stage.setScene(scene);
        stage.show();
    }

    private Pane createSceneOne() {
        VBox v = new VBox();

        HBox pidHbox = new HBox();
        HBox u_nameHbox = new HBox();
        HBox set_dayHbox = new HBox();
        HBox set_timeHbox = new HBox();
 backButton.setOnAction(event -> changeScene(createSceneTwo()));
        pidHbox.getChildren().addAll(new Label("Patient Id: "), txtpatientId);
        u_nameHbox.getChildren().addAll(new Label("DOC_username: "), txtdoc_username);
        set_dayHbox.getChildren().addAll(new Label("Set Day: "), txtset_day);
        set_timeHbox.getChildren().addAll(new Label("Set Time: "), txtset_time);

        btnadd.setText("Add");
        btnupdate.setText("Update");
        btndelete.setText("Delete");

        HBox btnHbox = new HBox();
        btnHbox.getChildren().addAll(btnadd, btnupdate, btndelete,backButton);

        v.getChildren().addAll(pidHbox, u_nameHbox, set_dayHbox, set_timeHbox, btnHbox);

        return v;
    }

    private Pane createSceneTwo() {
        btnSee.setOnAction(event -> changeScene(createSceneOne()));

        VBox vBox = new VBox();
        vBox.getChildren().addAll( createTablePane(),btnSee);
        
        return vBox;
    }

    private Pane createTablePane() {
        VBox tablePane = new VBox();

        HBox filterBox = new HBox();
        filterBox.getChildren().addAll(new Label("Username: "), usernameTextField, seeButton);

        seeButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            if (!username.isEmpty()) {
                fetchAndDisplayData(username,tableView);
            } else {
                showAlert("Username cannot be empty.");
            }
        });
         viewButton.setOnAction(event -> {
            
            
                fetchAndDisplayData2(tableView2);
           
         });

        tablePane.getChildren().addAll( tableView2,viewButton,filterBox,tableView);
        return tablePane;
    }

    private void fetchAndDisplayData(String username,TableView<TableRowData>tableView) {
        tableView.getItems().clear();
        try {
          ResultSet  resultSet = s.displayTable(username);
            if (resultSet != null ) {
                while(resultSet.next()) {
                    String timeInterval = resultSet.getString("time_interval");
                    String monday = resultSet.getString("monday");
                    String tuesday = resultSet.getString("tuesday");
                    String wednesday = resultSet.getString("wednesday");
                    String thursday = resultSet.getString("thursday");
                    String friday = resultSet.getString("friday");
                    String saturday = resultSet.getString("saturday");
                    String sunday = resultSet.getString("sunday");
                    TableRowData rowData = new TableRowData(timeInterval, monday, tuesday, wednesday, thursday, friday, saturday, sunday);
                    tableView.getItems().add(rowData);
                } 
            } else {
                showAlert("No data found for the specified username.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private void fetchAndDisplayData2(TableView<TableRowData2>tableView2) {
        tableView2.getItems().clear();
        try {
          ResultSet  resultSet = ss.displayTable2();
            if (resultSet != null ) {
                while(resultSet.next()) {
                    String timeInterval = resultSet.getString("time_interval");
                    String monday = resultSet.getString("monday");
                    String tuesday = resultSet.getString("tuesday");
                  
                    TableRowData2 rowData2 = new TableRowData2(timeInterval, monday, tuesday);
                    tableView2.getItems().add(rowData2);
                } 
            } else {
                showAlert("No data found for the specified username.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TableView<TableRowData> createTableView() {
        TableView<TableRowData> tableView = new TableView<>();
        TableColumn<TableRowData, String> timeIntervalCol = new TableColumn<>("Time Interval");
        timeIntervalCol.setCellValueFactory(new PropertyValueFactory<>("timeInterval"));

        TableColumn<TableRowData, String> mondayCol = new TableColumn<>("Monday");
        mondayCol.setCellValueFactory(new PropertyValueFactory<>("monday"));

        TableColumn<TableRowData, String> tuesdayCol = new TableColumn<>("Tuesday");
        tuesdayCol.setCellValueFactory(new PropertyValueFactory<>("tuesday"));

        TableColumn<TableRowData, String> wednesdayCol = new TableColumn<>("Wednesday");
        wednesdayCol.setCellValueFactory(new PropertyValueFactory<>("wednesday"));

        TableColumn<TableRowData, String> thursdayCol = new TableColumn<>("Thursday");
        thursdayCol.setCellValueFactory(new PropertyValueFactory<>("thursday"));

        TableColumn<TableRowData, String> fridayCol = new TableColumn<>("Friday");
        fridayCol.setCellValueFactory(new PropertyValueFactory<>("friday"));

        TableColumn<TableRowData, String> saturdayCol = new TableColumn<>("Saturday");
        saturdayCol.setCellValueFactory(new PropertyValueFactory<>("saturday"));

        TableColumn<TableRowData, String> sundayCol = new TableColumn<>("Sunday");
        sundayCol.setCellValueFactory(new PropertyValueFactory<>("sunday"));

        tableView.getColumns().addAll(timeIntervalCol, mondayCol, tuesdayCol, wednesdayCol, thursdayCol, fridayCol, saturdayCol, sundayCol);
        return tableView;
    }
 private TableView<TableRowData2> createTableView2() {
        TableView<TableRowData2> tableView2 = new TableView<>();
        TableColumn<TableRowData2, String> timeIntervalCol = new TableColumn<>("Time Interval");
        timeIntervalCol.setCellValueFactory(new PropertyValueFactory<>("timeInterval"));

        TableColumn<TableRowData2, String> mondayCol = new TableColumn<>("Monday");
        mondayCol.setCellValueFactory(new PropertyValueFactory<>("monday"));

        TableColumn<TableRowData2, String> tuesdayCol = new TableColumn<>("Tuesday");
        tuesdayCol.setCellValueFactory(new PropertyValueFactory<>("tuesday"));

   
        tableView2.getColumns().addAll(timeIntervalCol, mondayCol, tuesdayCol);
        return tableView2;
    }

    private void displayDoc() {
        btnadd.setOnAction(event -> {
            Appatient app = new Appatient();
            app.setusername(txtdoc_username.getText());
            app.setdateapp(txtset_day.getText());
            app.settimeapp(Integer.parseInt(txtset_time.getText()));
            app.setpID(Integer.parseInt(txtpatientId.getText()));
            app.Add();
        });

        btndelete.setOnAction(event -> {
            Appatient app = new Appatient();
            app.setusername(txtdoc_username.getText());
            app.setdateapp(txtset_day.getText());
            app.settimeapp(Integer.parseInt(txtset_time.getText()));
            app.setpID(Integer.parseInt(txtpatientId.getText()));
            app.cancel();
        });

        btnupdate.setOnAction(event -> {
            Appatient app = new Appatient();
            app.setusername(txtdoc_username.getText());
            app.setdateapp(txtset_day.getText());
            app.settimeapp(Integer.parseInt(txtset_time.getText()));
            app.setpID(Integer.parseInt(txtpatientId.getText()));
            app.update();
        });
    }

    private void changeScene(Pane scene) {
        root.setCenter(scene);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static class TableRowData {
        private final String timeInterval;
        private final String monday;
        private final String tuesday;
        private final String wednesday;
        private final String thursday;
        private final String friday;
        private final String saturday;
        private final String sunday;
        public TableRowData(String timeInterval, String monday, String tuesday, String wednesday,
                            String thursday, String friday, String saturday, String sunday) {
            this.timeInterval = timeInterval;
            this.monday = monday;
            this.tuesday = tuesday;
            this.wednesday = wednesday;
            this.thursday = thursday;
            this.friday = friday;
            this.saturday = saturday;
            this.sunday = sunday;
        }

        public String getTimeInterval() {
            return timeInterval;
        }

        public String getMonday() {
            return monday;
        }

        public String getTuesday() {
            return tuesday;
        }

        public String getWednesday() {
            return wednesday;
        }

        public String getThursday() {
            return thursday;
        }

        public String getFriday() {
            return friday;
        }

        public String getSaturday() {
            return saturday;
        }

        public String getSunday() {
            return sunday;
        }
    }
 public static class TableRowData2 {
        private final String timeInterval;
        private final String monday;
        private final String tuesday;
        
        public TableRowData2(String timeInterval, String monday, String tuesday) {
            this.timeInterval = timeInterval;
            this.monday = monday;
            this.tuesday = tuesday;
            
        }

        public String getTimeInterval() {
            return timeInterval;
        }

        public String getMonday() {
            return monday;
        }

        public String getTuesday() {
            return tuesday;
        }

 }
    

    public static void main(String[] args) {
        launch(args);
    }
}


























