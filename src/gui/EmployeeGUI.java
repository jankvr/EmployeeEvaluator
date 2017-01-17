/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Employee;
import model.Evaluation;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Trieda spravuje všetky činnosti týkajúce sa zamestnanca v rámci GUI
 * @author Jaroslav
 */
public class EmployeeGUI {
    EmployeeEvaluator employeeEvaluator;
    TableView<Employee> allEmployeesScreen;
    
    EmployeeGUI(EmployeeEvaluator employeeEvaluator){
        this.employeeEvaluator= employeeEvaluator;
        
        allEmployeesScreen = new TableView<Employee>();   
        refreshEmployeeTable();
        
        allEmployeesScreen.setRowFactory( tv -> {
            TableRow<Employee> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    //zmeniť screen na detail zamestnanca
                    Employee rowData = row.getItem();
                    System.out.println("zoberazujem detail zamestnanca"+rowData);
                    this.employeeEvaluator.getRoot().setCenter(employeeDetail(rowData));
                }
            });
        return row ;
        });
        
    }
    
    /**
     * Vracia obrazovku umožňujúcu založiť nového zamestnanca
     * @return obrazovka na založenie zamestnanca
     */
    public Pane newEmployee(){
        GridPane newEmployeeScreen = new GridPane();
        newEmployeeScreen.setAlignment(Pos.CENTER);
        newEmployeeScreen.setHgap(10);
        newEmployeeScreen.setVgap(10);
        newEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        
        Text scenetitle2 = new Text("Pridávanie zamestnanca");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        newEmployeeScreen.add(scenetitle2, 0, 0, 2, 1);
        Label firstName = new Label("Krstné meno:");
        newEmployeeScreen.add(firstName, 0, 1);

        TextField firstNameField = new TextField();
        newEmployeeScreen.add(firstNameField, 1, 1);

        Label lastName = new Label("Priezvisko:");
        newEmployeeScreen.add(lastName, 0, 2);

        TextField lastNameField = new TextField();
        newEmployeeScreen.add(lastNameField, 1, 2);
        
        Label birthNumber = new Label("Rodné číslo:");
        newEmployeeScreen.add(birthNumber, 0, 3);

        TextField birthNumberField = new TextField();
        newEmployeeScreen.add(birthNumberField, 1, 3);

        Label role = new Label("Úloha:");
        newEmployeeScreen.add(role, 0, 4);

        TextField roleField = new TextField();
        newEmployeeScreen.add(roleField, 1, 4);        
        
        //int formerId = employee.getIdEmployee();
        
        Button editEmployeeBtn = new Button("Uložiť zzamestnanca");
        editEmployeeBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("uložiťzame");
                    
                    final Employee newEmployee = new Employee(employeeEvaluator.getEr().freeId(),firstNameField.getText(),
                            lastNameField.getText(),birthNumberField.getText(),
                            roleField.getText());
                    employeeEvaluator.getEr().create(newEmployee); 
                    refreshEmployeeTable();
                    employeeEvaluator.getRoot().setCenter(allEmployeesScreen);
                });
        newEmployeeScreen.add(editEmployeeBtn,1,5);
        
        return newEmployeeScreen;
    }
    
    /**
     * Aktualizuje tabuľku zamestnancov.
     */
    public void refreshEmployeeTable(){
        allEmployeesScreen.setItems(FXCollections.observableArrayList(employeeEvaluator.getEr().getAllEmployees()));
        TableColumn<Employee,String> firstNameCol = new TableColumn<Employee,String>("Krstné meno");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("firstName"));
        TableColumn<Employee,String> lastNameCol = new TableColumn<Employee,String>("Priezvisko");
        lastNameCol.setCellValueFactory(new PropertyValueFactory("lastName"));
        TableColumn<Employee,String> birthNumberCol = new TableColumn<Employee,String>("Rodné číslo");
        birthNumberCol.setCellValueFactory(new PropertyValueFactory("birthNumber"));
        TableColumn<Employee,String> roleCol = new TableColumn<Employee,String>("Úloha");
        roleCol.setCellValueFactory(new PropertyValueFactory("role"));
        allEmployeesScreen.getColumns().setAll(firstNameCol, lastNameCol, birthNumberCol, roleCol);
    }
    
    /**
     * Vracia obrazovku umožňujúcu editovať zamestnanca.
     * @param employee editovaný zamestnanec
     * @return obrazovka na editovanie
     */
    public Pane editEmployee(Employee employee){
        GridPane editEmployeeScreen = new GridPane();
        editEmployeeScreen.setAlignment(Pos.CENTER);
        editEmployeeScreen.setHgap(10);
        editEmployeeScreen.setVgap(10);
        editEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Upravovanie zamestnanca");
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
                    newEmployee.setEvaluations(employee.getEvaluations());
                    employeeEvaluator.getEr().edit(newEmployee); 
                    refreshEmployeeTable();
                    employeeEvaluator.getEvaluationGUI().refreshEvaluationTable(newEmployee);
                    employeeEvaluator.getRoot().setCenter(allEmployeesScreen);
                });
        editEmployeeScreen.add(editEmployeeBtn,1,5);
        return editEmployeeScreen;
    }   
    
    /**
     * Vracia obrazovku s detailnými údajmi o zamestnancovi.
     * @param employee zamestnanec, ktorého sa detaily týkajú
     * @return obrazovka s detailnými údajmi
     */
    public Pane employeeDetail(Employee employee){
        
        VBox detailScreen = new VBox();
            HBox detailScreenButtons = new HBox();
                Button editEmployeeBtn = new Button();
                editEmployeeBtn.setText("Upraviť zamestnanca");
                editEmployeeBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("upraviťzamestnanca!");
                    
                    employeeEvaluator.getRoot().setCenter(editEmployee(employee));
                });
                editEmployeeBtn.setPrefWidth(200);

                Button deleteEmployeeBtn = new Button();
                deleteEmployeeBtn.setText("Vymazať zamestnanca");
                deleteEmployeeBtn.setOnAction((ActionEvent event) -> {
                    try{
                        System.out.println("vymazaťzame");
                        employeeEvaluator.getEr().delete(employee.getIdEmployee());
                        refreshEmployeeTable();
                        employeeEvaluator.getRoot().setCenter(allEmployeesScreen);
                    } catch(ConstraintViolationException e){
                        Stage stage = new Stage();
                        GridPane grid = new GridPane();
                        grid.setAlignment(Pos.CENTER);
                        grid.setHgap(10);
                        grid.setVgap(10);
                        grid.setPadding(new Insets(25, 25, 25, 25));
                        grid.add(new Text("Zamestnanec má naplánované pohovory."),0,1);
                        grid.add(new Text("Ak ho chcete zmazať, musíte zmazať jeho pohovory."),0,2);
                         
                        Scene gridScene = new Scene(grid, 300, 275);
                        stage.setScene(gridScene);
                        stage.show();
                        employeeEvaluator.getEr().rollBack();
                    }
                });
                deleteEmployeeBtn.setPrefWidth(200);  
                
                Button newEvaluationBtn = new Button();
                newEvaluationBtn.setText("Nový pohovor ");
                newEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("novypohovor");
                    
                    employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEvaluationGUI().newEvaluation(employee));
                });
                newEvaluationBtn.setPrefWidth(200);
            detailScreenButtons.getChildren().add(editEmployeeBtn);
            detailScreenButtons.getChildren().add(deleteEmployeeBtn);
            detailScreenButtons.getChildren().add(newEvaluationBtn);
        
            VBox detailScreenData = new VBox();
            detailScreenData.getChildren().add(new Text("Meno: "+employee.getFirstName()));
            detailScreenData.getChildren().add(new Text("Priezvisko: "+employee.getLastName()));
            detailScreenData.getChildren().add(new Text("Rodné číslo: "+employee.getBirthNumber()));
            detailScreenData.getChildren().add(new Text("Rola: "+employee.getRole()));
            

            
            TableView<Evaluation> evaluationTable = employeeEvaluator.getEvaluationGUI().refreshEvaluationTable(employee);
            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        detailScreen.getChildren().add(evaluationTable);
        return detailScreen;
    }

    /**
     * Vracia obrazovku so všetkými zamestnancami.
     * @return obrazovka so všetkými zamestnancami.
     */
    public TableView<Employee> getAllEmployeesScreen() {
        return allEmployeesScreen;
    }
    
    
}
