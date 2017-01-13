/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Employee;
import repositories.EmployeeRepository;

/**
 *
 * @author User
 */
public class EmployeeEvaluator extends Application {
    
    TableView<Employee> allEmployeesScreen;
    GridPane newEmployeeScreen;
    GridPane editEmployeeScreen;
    GridPane deleteEmployeeScreen;
    GridPane newEvaluationScreen;
    GridPane editEvaluationScreen;
    GridPane deleteEvaluationScreen;
    GridPane newCategoryScreen;
    GridPane editCategoryScreen;
    GridPane deleteCategoryScreen;
    EmployeeRepository er;
    
    
    
    @Override
    public void start(Stage primaryStage) {
//        // --------------------- TESTY --------------------------------------------------------
        /*EmployeeRepository*/ er = new EmployeeRepository();

        Employee employee = new Employee();
        
        employee.setFirstName("Ja%");
        
        Employee emp2 = new Employee();
        emp2.setIdEmployee(1);
        
        er.getEmployeesByParameters(employee).stream().forEach((a) -> {
            System.out.println("like " +a.getFirstName() + " " + a.getLastName());
        });
        
        er.getEmployeesByParameters(emp2).stream().forEach((a) -> {
            System.out.println("param " + a.getFirstName() + " " + a.getLastName());
        });
        
        
        er.getAllEmployees().stream().forEach((a) -> {
            System.out.println("all " + a.getFirstName() + " " + a.getLastName());
        });
        
        Employee emp3 = new Employee();
        
        emp3.setRole("Tester");
        emp3.setLastName("Burtik%");
        
        er.getEmployeesByParameters(emp3).stream().forEach((a) -> {
            System.out.println("tester " + a.getFirstName() + " " + a.getLastName());
        });
        
        System.out.println(er.freeId());
        
        System.out.println("delete: " + er.delete(5));
        
        Employee empToAdd = new Employee();
        
        empToAdd.setBirthNumber("1234");
        empToAdd.setFirstName("bubak");
        empToAdd.setLastName("bubinovity");
        empToAdd.setRole("konik");
        empToAdd.setIdEmployee(er.freeId());
        
        System.out.println("adding emp -> " + empToAdd.getIdEmployee() + ": " + er.create(empToAdd));
        
        //empToAdd.setIdEmployee(30);
        empToAdd.setBirthNumber("999123456");
        System.out.println("edit emp: " + er.edit(empToAdd));
        
        
        // ------------------- GUI --------------------------------------------------
        BorderPane root = new BorderPane();
        
        Button allEmployeesBtn = new Button();
        allEmployeesBtn.setText("Všetci zamestnanci");
        allEmployeesBtn.setOnAction((ActionEvent event) -> {
            System.out.println("všetcizamestnanci!");
            
            root.setCenter(allEmployeesScreen);
        });
        allEmployeesBtn.setPrefWidth(200);
        
        Button newEmployeeBtn = new Button();
        newEmployeeBtn.setText("Nový zamestnanec");
        newEmployeeBtn.setOnAction((ActionEvent event) -> {
            System.out.println("novýzamestnanec!");
            root.setCenter(newEmployeeScreen);
        });
        newEmployeeBtn.setPrefWidth(200);
        
        Button editEmployeeBtn = new Button();
        editEmployeeBtn.setText("Upraviť zamestnanca");
        editEmployeeBtn.setOnAction((ActionEvent event) -> {
            System.out.println("upraviťzamestnanca!");
            root.setCenter(editEmployeeScreen);
        });
        editEmployeeBtn.setPrefWidth(200);
        
        Button deleteEmployeeBtn = new Button();
        deleteEmployeeBtn.setText("Vymazať zamestnanca");
        deleteEmployeeBtn.setOnAction((ActionEvent event) -> {
            System.out.println("vymazaťzame");
            root.setCenter(deleteEmployeeScreen);
        });
        deleteEmployeeBtn.setPrefWidth(200);
        
        
        


        
        
        VBox menu = new VBox();
        menu.getChildren().add(allEmployeesBtn);
        menu.getChildren().add(newEmployeeBtn);
        menu.getChildren().add(editEmployeeBtn);
        menu.getChildren().add(deleteEmployeeBtn);
        
        
        root.setLeft(menu);
        
        
//        VBox displayer = new VBox();
//        displayer.getChildren().add(new Button());
        
        
        setScreens();
        
        
        
        root.setCenter(allEmployeesScreen);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("The greatest EmployeeEvaluator in the whole 4IT353!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });   
    }

    
    private void setScreens(){
        
        //základná obrazovka
        allEmployeesScreen = new TableView<Employee>();
        //keď budem mať prístup k DB 
//        allEmployeesScreen.setItems((ObservableList)er.getAllEmployees());
//        TableColumn<Employee,String> firstNameCol = new TableColumn<Employee,String>("First Name");
//        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
//        TableColumn<Employee,String> lastNameCol = new TableColumn<Employee,String>("Last Name");
//        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
//        TableColumn<Employee,String> birthNumberCol = new TableColumn<Employee,String>("Birth Number");
//        birthNumberCol.setCellValueFactory(new PropertyValueFactory("birthNumber"));
//        TableColumn<Employee,String> roleCol = new TableColumn<Employee,String>("Role");
//        roleCol.setCellValueFactory(new PropertyValueFactory("role"));
//        

        ObservableList<Employee> ol = FXCollections.observableArrayList();
        ol.add(new Employee(0, "Andrej", "Hopko", "1234567890", "úrad práce"));
        ol.add(new Employee(1, "Anton", "Buday", "940912/1234", "všeznalec"));
        allEmployeesScreen.setItems(ol);
        TableColumn<Employee,String> firstNameCol = new TableColumn<Employee,String>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Employee,String> lastNameCol = new TableColumn<Employee,String>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Employee,String> birthNumberCol = new TableColumn<Employee,String>("Birth Number");
        birthNumberCol.setCellValueFactory(new PropertyValueFactory("birthNumber"));
        TableColumn<Employee,String> roleCol = new TableColumn<Employee,String>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory("role"));

        allEmployeesScreen.getColumns().setAll(firstNameCol, lastNameCol, birthNumberCol, roleCol);
        
        allEmployeesScreen.setRowFactory( tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    //zmeniť screen na detail zamestnanca
                    Employee rowData = row.getItem();
                    System.out.println(rowData);
                }
            });
        return row ;
});
  
        newEmployeeScreen = new GridPane();
        newEmployeeScreen.setAlignment(Pos.CENTER);
        newEmployeeScreen.setHgap(10);
        newEmployeeScreen.setVgap(10);
        newEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle1 = new Text("New Employee Screen");
        scenetitle1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        newEmployeeScreen.add(scenetitle1, 0, 0, 2, 1);
        
        
        
        editEmployeeScreen = new GridPane();
        editEmployeeScreen.setAlignment(Pos.CENTER);
        editEmployeeScreen.setHgap(10);
        editEmployeeScreen.setVgap(10);
        editEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Edit Employee Screen");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editEmployeeScreen.add(scenetitle2, 0, 0, 2, 1);
        
        
        deleteEmployeeScreen = new GridPane();
        deleteEmployeeScreen.setAlignment(Pos.CENTER);
        deleteEmployeeScreen.setHgap(10);
        deleteEmployeeScreen.setVgap(10);
        deleteEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle3 = new Text("delete Employee Screen");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        deleteEmployeeScreen.add(scenetitle3, 0, 0, 2, 1);
        
        
        
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
