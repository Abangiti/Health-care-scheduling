import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafxapplication1.newpackage.schedule;
import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import java.util.Optional;
import javafx.scene.control.Label;
import javafx.scene.text.Font;


import java.sql.ResultSet;

public class JavaFXApplication1 extends Application {

    private final schedule sc = new schedule();//object instantiate

    @Override
    public void start(Stage primaryStage) {
        Label usernamelabel = new Label("username:");
        TextField usernameTextField = new TextField();
        Button seeButton = new Button("See");
        Button updateButton = new Button("Update");
        
        TableView<TableRowData> tableView = createTableView();//to display the rows from the source
        usernamelabel.setFont(new Font(20));
        HBox ppp=new HBox(10);
ppp.getChildren().addAll(usernamelabel,usernameTextField);
HBox p=new HBox(40);
p.getChildren().addAll(seeButton,updateButton);
  p.setAlignment(Pos.CENTER_RIGHT);

        VBox root = new VBox(10);
        root.getChildren().addAll(ppp, tableView,p);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setTitle("Data Viewer");
        primaryStage.setScene(scene);
        primaryStage.show();

        seeButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            if (!username.isEmpty()) {
                fetchAndDisplayData(username, tableView); //to update the content and know what to display from database
            } else {
                showAlert("Username cannot be empty.");
            }
        });
        updateButton.setOnAction(event -> {
    String username = usernameTextField.getText();
    if (!username.isEmpty()) {
        updateData(username, tableView);
    } else {
        showAlert("Username cannot be empty.");
    }
});
    }

 private void fetchAndDisplayData(String username, TableView<TableRowData> tableView) {
    // Clear existing items in the TableView
    tableView.getItems().clear();

    try {
        ResultSet resultSet = sc.createTable(username);
        if (resultSet != null) {
            while (resultSet.next()) {
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
        e.printStackTrace(); // Handle the exception appropriately in your application
    }
}

private void updateData(String username, TableView<TableRowData> tableView) {
    try {
        // Create a custom dialog for gathering information
        Dialog<UpdateDataResult> updateDialog = new Dialog<>();
        updateDialog.setTitle("Update Data");
        updateDialog.setHeaderText(null);

        // Set the button types (OK and Cancel)
        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        updateDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        // Create controls for date, day, new day, and new time
        TextField dateField = new TextField();
        dateField.setPromptText("Enter the time_interval");
        TextField dayField = new TextField();
        dayField.setPromptText("Enter the day");
        TextField newDayField = new TextField();
        newDayField.setPromptText("Enter the new day");
        TextField newTimeField = new TextField();
        newTimeField.setPromptText("Enter the new time_interval");

        // Set the content of the dialog
        GridPane grid = new GridPane();
        grid.add(new Label("Date:"), 0, 0);
        grid.add(dateField, 1, 0);
        grid.add(new Label("Day:"), 0, 1);
        grid.add(dayField, 1, 1);
        grid.add(new Label("New Day:"), 0, 2);
        grid.add(newDayField, 1, 2);
        grid.add(new Label("New Time:"), 0, 3);
        grid.add(newTimeField, 1, 3);

        updateDialog.getDialogPane().setContent(grid);

        // Request focus on the date field by default
        Platform.runLater(dateField::requestFocus);//to focus

        // Convert the result to the UpdateDataResult when the OK button is clicked
        updateDialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new UpdateDataResult(
                        dateField.getText(),
                        dayField.getText(),
                        newDayField.getText(),
                        newTimeField.getText()
                );
            }
            return null;
        });

        // Show the dialog and wait for the user's response
        Optional<UpdateDataResult> result = updateDialog.showAndWait();

        // Process the result if available
        result.ifPresent(updateDataResult -> {
            String timeIntervalToUpdate = updateDataResult.getDateInput();
            String dayToReschedule = updateDataResult.getDayInput();
            String updatedDay = updateDataResult.getNewDay();
            String newTimeInterval = updateDataResult.getNewTime();

            // Perform the update using the existing instance of schedule
            sc.updateTable(username, timeIntervalToUpdate, dayToReschedule, updatedDay, newTimeInterval);

            // Refresh the table view to reflect the changes
            fetchAndDisplayData(username, tableView);
        });
    } catch (Exception e) {
        e.printStackTrace(); // Handle the exception appropriately in your application
    }
}

// Define a class to hold the result of the dialog
private static class UpdateDataResult {
    private final String dateInput;
    private final String dayInput;
    private final String newDay;
    private final String newTime;

    public UpdateDataResult(String dateInput, String dayInput, String newDay, String newTime) {
        this.dateInput = dateInput;
        this.dayInput = dayInput;
        this.newDay = newDay;
        this.newTime = newTime;
    }

    public String getDateInput() {
        return dateInput;
    }

    public String getDayInput() {
        return dayInput;
    }

    public String getNewDay() {
        return newDay;
    }

    public String getNewTime() {
        return newTime;
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

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
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
}
