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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Category;
import model.Employee;
import model.Evaluation;
import repository.EmployeeRepository;

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
    BorderPane root;
    
    
    
    @Override
    public void start(Stage primaryStage) {
        // --------------------- TESTY --------------------------------------------------------
//        er = new EmployeeRepository();
//
//        Employee employee = new Employee();
//        
//        employee.setFirstName("Ja%");
//        
//        Employee emp2 = new Employee();
//        emp2.setIdEmployee(1);
//        
//        er.getEmployeesByParameters(employee).stream().forEach((a) -> {
//            System.out.println("like " +a.getFirstName() + " " + a.getLastName());
//        });
//        
//        er.getEmployeesByParameters(emp2).stream().forEach((a) -> {
//            System.out.println("param " + a.getFirstName() + " " + a.getLastName());
//        });
//        
//        
//        er.getAllEmployees().stream().forEach((a) -> {
//            System.out.println("all " + a.getFirstName() + " " + a.getLastName());
//        });
//        
//        Employee emp3 = new Employee();
//        
//        emp3.setRole("Tester");
//        emp3.setLastName("Burtik%");
//        
//        er.getEmployeesByParameters(emp3).stream().forEach((a) -> {
//            System.out.println("tester " + a.getFirstName() + " " + a.getLastName());
//        });
//        
//        System.out.println(er.freeId());
//        
//        System.out.println("delete: " + er.delete(5));
//        
//        Employee empToAdd = new Employee();
//        
//        empToAdd.setBirthNumber("1234");
//        empToAdd.setFirstName("bubak");
//        empToAdd.setLastName("bubinovity");
//        empToAdd.setRole("konik");
//        empToAdd.setIdEmployee(er.freeId());
//        
//        System.out.println("adding emp -> " + empToAdd.getIdEmployee() + ": " + er.create(empToAdd));
//        
//        //empToAdd.setIdEmployee(30);
//        empToAdd.setBirthNumber("999123456");
//        System.out.println("edit emp: " + er.edit(empToAdd));
        
        
        // ------------------- GUI --------------------------------------------------
        root = new BorderPane();
        
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
            root.setCenter(newEmployee());
        });
        newEmployeeBtn.setPrefWidth(200);
        

        
        
        


        
        
        VBox menu = new VBox();
        menu.getChildren().add(allEmployeesBtn);
        menu.getChildren().add(newEmployeeBtn);
        //menu.getChildren().add(editEmployeeBtn);
        //menu.getChildren().add(deleteEmployeeBtn);
        
        
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
        
        
        
        
        
        refreshEmployeeTable();
        


        
        
        allEmployeesScreen.setRowFactory( tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    //zmeniť screen na detail zamestnanca
                    Employee rowData = row.getItem();
                    System.out.println("zoberazujem detail zamestnanca"+rowData);
                    root.setCenter(employeeDetail(rowData));
                }
            });
        return row ;
        });
  
        
        
        
        
        
        deleteEmployeeScreen = new GridPane();
        deleteEmployeeScreen.setAlignment(Pos.CENTER);
        deleteEmployeeScreen.setHgap(10);
        deleteEmployeeScreen.setVgap(10);
        deleteEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle3 = new Text("delete Employee Screen");
        scenetitle3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        deleteEmployeeScreen.add(scenetitle3, 0, 0, 2, 1);
        
        
        
        
        
    }
    
    private Pane employeeDetail(Employee employee){
        VBox detailScreen = new VBox();
            HBox detailScreenButtons = new HBox();
                Button editEmployeeBtn = new Button();
                editEmployeeBtn.setText("Upraviť zamestnanca");
                editEmployeeBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("upraviťzamestnanca!");
                    
                    root.setCenter(editEmployee(employee));
                });
                editEmployeeBtn.setPrefWidth(200);

                Button deleteEmployeeBtn = new Button();
                deleteEmployeeBtn.setText("Vymazať zamestnanca");
                deleteEmployeeBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("vymazaťzame");
                    er.delete(employee.getIdEmployee());
                    refreshEmployeeTable();
                    root.setCenter(allEmployeesScreen);
                });
                deleteEmployeeBtn.setPrefWidth(200);  
            detailScreenButtons.getChildren().add(editEmployeeBtn);
            detailScreenButtons.getChildren().add(deleteEmployeeBtn);
        
            VBox detailScreenData = new VBox();
            detailScreenData.getChildren().add(new Text("Meno: "+employee.getFirstName()));
            detailScreenData.getChildren().add(new Text("Priezvisko: "+employee.getLastName()));
            detailScreenData.getChildren().add(new Text("Rodné číslo: "+employee.getBirthNumber()));
            detailScreenData.getChildren().add(new Text("Rola: "+employee.getRole()));
            

            
            TableView<Evaluation> evaluationTable = new TableView<Evaluation>();
            evaluationTable.setItems(FXCollections.observableArrayList(employee.getEvaluations()));
            TableColumn<Evaluation,String> evaluationDateCol = new TableColumn<Evaluation,String>("Evaluation date");
            evaluationDateCol.setCellValueFactory(new PropertyValueFactory("evaluationDate"));
            TableColumn<Employee,String> plannedDateCol = new TableColumn<Employee,String>("Planned Date");
            plannedDateCol.setCellValueFactory(new PropertyValueFactory("plannedDate"));
            TableColumn<Employee,String> stornoReasonCol = new TableColumn<Employee,String>("Storno Reason");
            stornoReasonCol.setCellValueFactory(new PropertyValueFactory("stornoReason"));
            evaluationTable.setRowFactory( tv -> {
            TableRow<Evaluation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    //zmeniť screen na detail zamestnanca
                    Evaluation rowData = row.getItem();
                    System.out.println("zoberazujem detail zamestnanca"+rowData);
                    root.setCenter(evaluationDetail(rowData));
                }
            });
            return row ;
            });
            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        detailScreen.getChildren().add(evaluationTable);
        return detailScreen;
    }
    
    private Pane evaluationDetail(Evaluation evaluation){
        VBox detailScreen = new VBox();
            HBox detailScreenButtons = new HBox();
                Button editEvaluationBtn = new Button();
                editEvaluationBtn.setText("Upraviť pohovor");
                editEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("upraviťpohovor!");
                    
                    root.setCenter(editEvaluationScreen);
                });
                editEvaluationBtn.setPrefWidth(200);

                Button deleteEvaluationBtn = new Button();
                deleteEvaluationBtn.setText("Vymazať pohovor");
                deleteEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("vymazaťpohovor");
                    er.delete(evaluation.getIdEvaluation());
                    refreshEmployeeTable();
                    root.setCenter(allEmployeesScreen);
                });
                deleteEvaluationBtn.setPrefWidth(200);  
            detailScreenButtons.getChildren().add(editEvaluationBtn);
            detailScreenButtons.getChildren().add(deleteEvaluationBtn);
        
            VBox detailScreenData = new VBox();
            detailScreenData.getChildren().add(new Text("Evaluation date: "+evaluation.getEvaluationDate()));
            detailScreenData.getChildren().add(new Text("Planned Date: "+evaluation.getPlannedDate()));
            detailScreenData.getChildren().add(new Text("Storno reason: "+evaluation.getStornoReason()));
            

            
            TableView<Category> categoryTable = new TableView<Category>();
//            categoryTable.setItems(FXCollections.observableArrayList(evaluation.getEvaluationItems()));
//            TableColumn<Evaluation,String> evaluationDateCol = new TableColumn<Evaluation,String>("Evaluation date");
//            evaluationDateCol.setCellValueFactory(new PropertyValueFactory("evaluationDate"));
//            TableColumn<Employee,String> plannedDateCol = new TableColumn<Employee,String>("Planned Date");
//            plannedDateCol.setCellValueFactory(new PropertyValueFactory("plannedDate"));
//            TableColumn<Employee,String> stornoReasonCol = new TableColumn<Employee,String>("Storno Reason");
//            stornoReasonCol.setCellValueFactory(new PropertyValueFactory("stornoReason"));
//            evaluationTable.setRowFactory( tv -> {
//            TableRow<Evaluation> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
//                    //zmeniť screen na detail zamestnanca
//                    Evaluation rowData = row.getItem();
//                    System.out.println("zoberazujem detail zamestnanca"+rowData);
//                    root.setCenter(evalautionDetail(rowData));
//                }
//            });
//            return row ;
//            });
            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        detailScreen.getChildren().add(categoryTable);
        return detailScreen;
    }
    
    private Pane editEmployee(Employee employee){
        editEmployeeScreen = new GridPane();
        editEmployeeScreen.setAlignment(Pos.CENTER);
        editEmployeeScreen.setHgap(10);
        editEmployeeScreen.setVgap(10);
        editEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Edit Employee Screen");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editEmployeeScreen.add(scenetitle2, 0, 0, 2, 1);
        Label firstName = new Label("First Name:");
        editEmployeeScreen.add(firstName, 0, 1);

        TextField firstNameField = new TextField();
        firstNameField.setText(employee.getFirstName());
        editEmployeeScreen.add(firstNameField, 1, 1);

        Label lastName = new Label("Last Name:");
        editEmployeeScreen.add(lastName, 0, 2);

        TextField lastNameField = new TextField();
        lastNameField.setText(employee.getLastName());
        editEmployeeScreen.add(lastNameField, 1, 2);
        
        Label birthNumber = new Label("Birth Number:");
        editEmployeeScreen.add(birthNumber, 0, 3);

        TextField birthNumberField = new TextField();
        birthNumberField.setText(employee.getBirthNumber());
        editEmployeeScreen.add(birthNumberField, 1, 3);

        Label role = new Label("Role:");
        editEmployeeScreen.add(role, 0, 4);

        TextField roleField = new TextField();
        roleField.setText(employee.getRole());
        editEmployeeScreen.add(roleField, 1, 4);        
        
        //int formerId = employee.getIdEmployee();
        
        Button editEmployeeBtn = new Button("Uložiť zmeny");
        editEmployeeBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("vymazaťzame");
                    
                    final Employee newEmployee = new Employee(employee.getIdEmployee(),firstNameField.getText(),
                            lastNameField.getText(),birthNumberField.getText(),
                            roleField.getText());
                    er.edit(newEmployee); //hádže NonUniqueObjectException
                    refreshEmployeeTable();
                    root.setCenter(allEmployeesScreen);
                });
        editEmployeeScreen.add(editEmployeeBtn,1,5);
        return editEmployeeScreen;
    }   
    
    private Pane newEmployee(){
        newEmployeeScreen = new GridPane();
        newEmployeeScreen.setAlignment(Pos.CENTER);
        newEmployeeScreen.setHgap(10);
        newEmployeeScreen.setVgap(10);
        newEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        
        Text scenetitle2 = new Text("New Employee Screen");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        newEmployeeScreen.add(scenetitle2, 0, 0, 2, 1);
        Label firstName = new Label("First Name:");
        newEmployeeScreen.add(firstName, 0, 1);

        TextField firstNameField = new TextField();
        newEmployeeScreen.add(firstNameField, 1, 1);

        Label lastName = new Label("Last Name:");
        newEmployeeScreen.add(lastName, 0, 2);

        TextField lastNameField = new TextField();
        newEmployeeScreen.add(lastNameField, 1, 2);
        
        Label birthNumber = new Label("Birth Number:");
        newEmployeeScreen.add(birthNumber, 0, 3);

        TextField birthNumberField = new TextField();
        newEmployeeScreen.add(birthNumberField, 1, 3);

        Label role = new Label("Role:");
        newEmployeeScreen.add(role, 0, 4);

        TextField roleField = new TextField();
        newEmployeeScreen.add(roleField, 1, 4);        
        
        //int formerId = employee.getIdEmployee();
        
        Button editEmployeeBtn = new Button("Uložiť zzamestnanca");
        editEmployeeBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("uložiťzame");
                    
                    final Employee newEmployee = new Employee(er.freeId(),firstNameField.getText(),
                            lastNameField.getText(),birthNumberField.getText(),
                            roleField.getText());
                    er.create(newEmployee); 
                    refreshEmployeeTable();
                    root.setCenter(allEmployeesScreen);
                });
        newEmployeeScreen.add(editEmployeeBtn,1,5);
        
        return newEmployeeScreen;
    }
    public void refreshEmployeeTable(){
        allEmployeesScreen.setItems(FXCollections.observableArrayList(er.getAllEmployees()));
        TableColumn<Employee,String> firstNameCol = new TableColumn<Employee,String>("First Name");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Employee,String> lastNameCol = new TableColumn<Employee,String>("Last Name");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Employee,String> birthNumberCol = new TableColumn<Employee,String>("Birth Number");
        birthNumberCol.setCellValueFactory(new PropertyValueFactory("birthNumber"));
        TableColumn<Employee,String> roleCol = new TableColumn<Employee,String>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory("role"));
        allEmployeesScreen.getColumns().setAll(firstNameCol, lastNameCol, birthNumberCol, roleCol);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
