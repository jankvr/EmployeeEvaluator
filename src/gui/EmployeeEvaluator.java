/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.Category;
import model.Employee;
import model.Evaluation;
import model.EvaluationItem;
import org.hibernate.exception.ConstraintViolationException;
import repository.CategoryRepository;
import repository.EmployeeRepository;
import repository.EvaluationItemRepository;
import repository.EvaluationRepository;

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
    TableView<Category> allCategoriesScreen;
    GridPane newCategoryScreen;
    GridPane editCategoryScreen;
    GridPane deleteCategoryScreen;
    GridPane editEvaluationItemsScreen;
    EmployeeRepository er;
    CategoryRepository cr;
    EvaluationItemRepository eir;
    EvaluationRepository evalr;
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
        
        /*EmployeeRepository*/ er = new EmployeeRepository();
        cr=new CategoryRepository();
        eir=new EvaluationItemRepository();
        evalr=new EvaluationRepository();
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
        
        Button allCategoriesBtn = new Button();
        allCategoriesBtn.setText("Všetky otázky");
        allCategoriesBtn.setOnAction((ActionEvent event) -> {
            System.out.println("všetciotázky!");
            refreshCategoryTable();
            root.setCenter(allCategoriesScreen);
        });
        allCategoriesBtn.setPrefWidth(200);
        
        Button newCategoryBtn = new Button();
        newCategoryBtn.setText("Nová otázka");
        newCategoryBtn.setOnAction((ActionEvent event) -> {
            System.out.println("nováotázka!");
            root.setCenter(newCategory());
        });
        newCategoryBtn.setPrefWidth(200);
        

        
        
        


        
        
        VBox menu = new VBox();
        menu.getChildren().add(allEmployeesBtn);
        menu.getChildren().add(newEmployeeBtn);
        menu.getChildren().add(allCategoriesBtn);
        menu.getChildren().add(newCategoryBtn);
        
        
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
        
        
        
        allCategoriesScreen = new TableView<Category>();
        refreshCategoryTable();
  
        allCategoriesScreen.setRowFactory( tv -> {
            TableRow<Category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    //zmeniť screen na detail zamestnanca
                    Category rowData = row.getItem();
                    System.out.println("zoberazujem detail zamestnanca"+rowData);
                    root.setCenter(categoryDetail(rowData));
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
                    try{
                        System.out.println("vymazaťzame");
                        er.delete(employee.getIdEmployee());
                        refreshEmployeeTable();
                        root.setCenter(allEmployeesScreen);
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
                        er.rollBack();
                    }
                });
                deleteEmployeeBtn.setPrefWidth(200);  
                
                Button newEvaluationBtn = new Button();
                newEvaluationBtn.setText("Nový pohovor ");
                newEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("novypohovor");
                    
                    root.setCenter(newEvaluation(employee));
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
            

            
            TableView<Evaluation> evaluationTable = refreshEvaluationTable(employee);
            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        detailScreen.getChildren().add(evaluationTable);
        return detailScreen;
    }
    
    private Pane evaluationDetail(Evaluation evaluation,TableView<Evaluation> evaluationTable, Employee employee){
        VBox detailScreen = new VBox();
            HBox detailScreenButtons = new HBox();
                Button editEvaluationBtn = new Button();
                editEvaluationBtn.setText("Upraviť pohovor");
                editEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("upraviťpohovor!");
                    
                    root.setCenter(editEvaluation(evaluation,employee));
                });
                editEvaluationBtn.setPrefWidth(200);

                Button stornoEvaluationBtn = new Button();
                stornoEvaluationBtn.setText("Stornovať pohovor");
                stornoEvaluationBtn.setOnAction((ActionEvent event) -> {
                    try{
                        System.out.println("vymazaťpohovor");
                        
                        
                        refreshEmployeeTable();
                        root.setCenter(stornoEvaluation(evaluation,employee));
                    } catch(ConstraintViolationException e){
                        Stage stage = new Stage();
                        GridPane grid = new GridPane();
                        grid.setAlignment(Pos.CENTER);
                        grid.setHgap(10);
                        grid.setVgap(10);
                        grid.setPadding(new Insets(25, 25, 25, 25));
                        grid.add(new Text("K pohovoru sa vzťahujú zodpovedané otázky."),0,1);
                        grid.add(new Text("Ak ho chcete zmazať, musíte zmazať jeho otázky."),0,2);
                         
                        Scene gridScene = new Scene(grid, 300, 275);
                        stage.setScene(gridScene);
                        stage.show();
                        er.rollBack();
                    }
                });
                stornoEvaluationBtn.setPrefWidth(200);  
                
                Button deleteEvaluationBtn = new Button();
                deleteEvaluationBtn.setText("Vymazať pohovor");
                deleteEvaluationBtn.setOnAction((ActionEvent event) -> {
                    try{
                        System.out.println("vymazaťpoh");
                        evalr.delete(evaluation.getIdEvaluation());
                        employee.getEvaluations().remove(evaluation);
                        refreshEvaluationTable(employee);
                        root.setCenter(employeeDetail(employee));
                        
                    } catch(ConstraintViolationException e){
                        Stage stage = new Stage();
                        GridPane grid = new GridPane();
                        grid.setAlignment(Pos.CENTER);
                        grid.setHgap(10);
                        grid.setVgap(10);
                        grid.setPadding(new Insets(25, 25, 25, 25));
                        grid.add(new Text("K pohovoru sa vzťahujú zodpovedané otázky"),0,1);
                        grid.add(new Text("Ak ho chcete zmazať, musíte zmazať jeho otázky."),0,2);
                         
                        Scene gridScene = new Scene(grid, 300, 275);
                        stage.setScene(gridScene);
                        stage.show();
                        evalr.rollBack();
                    }
                });
                deleteEvaluationBtn.setPrefWidth(200);  
                
                Button editItemsBtn = new Button();
                editItemsBtn.setText("Upraviť položky");
                editItemsBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("Upraviť položky");

                    root.setCenter(editEvaluationItems(evaluation));
                });
                editItemsBtn.setPrefWidth(200);  
            detailScreenButtons.getChildren().add(editEvaluationBtn);
            detailScreenButtons.getChildren().add(stornoEvaluationBtn);
            detailScreenButtons.getChildren().add(deleteEvaluationBtn);
            detailScreenButtons.getChildren().add(editItemsBtn);
        
            VBox detailScreenData = new VBox();
            detailScreenData.getChildren().add(new Text("Evaluation date: "+evaluation.getEvaluationDate()));
            detailScreenData.getChildren().add(new Text("Planned Date: "+evaluation.getPlannedDate()));
            detailScreenData.getChildren().add(new Text("Storno reason: "+evaluation.getStornoReason()));
            

            
            Pane evaluationItemTable = evaluationItemTable(evaluation);
            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        detailScreen.getChildren().add(evaluationItemTable);
        return detailScreen;
    }
    
    private Pane editEmployee(Employee employee){
        editEmployeeScreen = new GridPane();
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
                    er.edit(newEmployee); 
                    refreshEmployeeTable();
                    refreshEvaluationTable(newEmployee);
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
    
    
        private Pane categoryDetail(Category category){
        VBox detailScreen = new VBox();
            HBox detailScreenButtons = new HBox();
//                Button editCategoryBtn = new Button();
//                editCategoryBtn.setText("Upraviť otázku");
//                editCategoryBtn.setOnAction((ActionEvent event) -> {
//                    System.out.println("upraviťzCategory!");
//                    
//                    root.setCenter(editCategory(category));
//                });
//                editCategoryBtn.setPrefWidth(200);

                Button deleteCategoryBtn = new Button();
                deleteCategoryBtn.setText("Vymazať otázku");
                deleteCategoryBtn.setOnAction((ActionEvent event) -> {
                    try{
                        System.out.println("vymazaťCategory");
                        cr.delete(category.getIdCategory());
                        refreshCategoryTable();
                        root.setCenter(allCategoriesScreen);
                    } catch(ConstraintViolationException e){
                        Stage stage = new Stage();
                        GridPane grid = new GridPane();
                        grid.setAlignment(Pos.CENTER);
                        grid.setHgap(10);
                        grid.setVgap(10);
                        grid.setPadding(new Insets(25, 25, 25, 25));
                        grid.add(new Text("Otázka sa používa v pohovoroch."),0,1);
                        grid.add(new Text("Ak ju chcete zmazať, musíte zmazať pohovory, \nkde sa vyskytuje."),0,2);
                         
                        Scene gridScene = new Scene(grid, 300, 275);
                        stage.setScene(gridScene);
                        stage.show();
                        cr.rollBack();
                    }
                });
                deleteCategoryBtn.setPrefWidth(200);  
//            detailScreenButtons.getChildren().add(editCategoryBtn);
            detailScreenButtons.getChildren().add(deleteCategoryBtn);
        
            VBox detailScreenData = new VBox();
            detailScreenData.getChildren().add(new Text("Description: "+category.getDescription()));
            detailScreenData.getChildren().add(new Text("Coefficient: "+category.getCoefficient()));
            

            
            
            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        return detailScreen;
    }
        
    private Pane newCategory(){
        newCategoryScreen = new GridPane();
        newCategoryScreen.setAlignment(Pos.CENTER);
        newCategoryScreen.setHgap(10);
        newCategoryScreen.setVgap(10);
        newCategoryScreen.setPadding(new Insets(25, 25, 25, 25));

        
        Text scenetitle2 = new Text("Pridávanie otázky");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        newCategoryScreen.add(scenetitle2, 0, 0, 2, 1);
        Label description = new Label("Popis:");
        newCategoryScreen.add(description, 0, 1);

        TextField descriptionField = new TextField();
        newCategoryScreen.add(descriptionField, 1, 1);

        Label coefficient = new Label("Koeficient:");
        newCategoryScreen.add(coefficient, 0, 2);

        ObservableList<Integer> options = 
        FXCollections.observableArrayList(
            0,1,2,3,4,5,6,7,8,9,10
        );
        ComboBox coefficientField = new ComboBox(options);
        newCategoryScreen.add(coefficientField, 1, 2);
        
               
        
        //int formerId = employee.getIdEmployee();
        
        Button editCategoryBtn = new Button("Uložiť otázku");
        editCategoryBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("uložiťotázkuz");
                    
                    final Category newCategory = new Category(cr.freeId(),descriptionField.getText(),
                            (int)coefficientField.getValue());
                    cr.create(newCategory); 
                    refreshCategoryTable();
                    root.setCenter(allCategoriesScreen);
                });
        newCategoryScreen.add(editCategoryBtn,1,3);
        
        return newCategoryScreen;
    }
    
    
    private Pane newEvaluation(Employee employee){
        newEvaluationScreen = new GridPane();
        newEvaluationScreen.setAlignment(Pos.CENTER);
        newEvaluationScreen.setHgap(10);
        newEvaluationScreen.setVgap(10);
        newEvaluationScreen.setPadding(new Insets(25, 25, 25, 25));

        
        Text scenetitle2 = new Text("Pridávanie pohovoru");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        newEvaluationScreen.add(scenetitle2, 0, 0, 2, 1);
        Label plannedDate = new Label("Plánovaný dátum:");
        newEvaluationScreen.add(plannedDate, 0, 1);

        DatePicker plannedDateField = new DatePicker();
        newEvaluationScreen.add(plannedDateField, 1, 1);

        
        
               
        
        //int formerId = employee.getIdEmployee();
        
        Button newEvaluationBtn = new Button("Uložiť pohovor");
        newEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("uložiťotázkuz");
                    LocalDate localDate = plannedDateField.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);
                    final Evaluation newEvaluation = new Evaluation(evalr.freeId(),employee,date);
                    evalr.create(newEvaluation); 
                    employee.getEvaluations().add(newEvaluation);//questionable
                    refreshEvaluationTable(employee);
                    root.setCenter(employeeDetail(employee));
                });
        newEvaluationScreen.add(newEvaluationBtn,1,3);
        
        return newEvaluationScreen;
    }
    
    private Pane editEvaluation(Evaluation evaluation, Employee employee){
        
        editEvaluationScreen = new GridPane();
        editEvaluationScreen.setAlignment(Pos.CENTER);
        editEvaluationScreen.setHgap(10);
        editEvaluationScreen.setVgap(10);
        editEvaluationScreen.setPadding(new Insets(25, 25, 25, 25));
        
        
        Text scenetitle2 = new Text("Nový pohovor");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editEvaluationScreen.add(scenetitle2, 0, 0, 2, 1);
        Label plannedDate = new Label("Plánovaný dátum:");
        editEvaluationScreen.add(plannedDate, 0, 1);
        
        DatePicker plannedDateField = new DatePicker();
        Date previousDate = evaluation.getPlannedDate();
        LocalDate previousLocalDate = new java.sql.Date(previousDate.getTime()).toLocalDate();
        plannedDateField.setValue(previousLocalDate);
        editEvaluationScreen.add(plannedDateField, 1, 1);
        
        Label evaluationDate = new Label("Dátum pohovoru:");
        editEvaluationScreen.add(evaluationDate, 0, 2);

        DatePicker evaluationDateField = new DatePicker();
        if(evaluation.getEvaluationDate()==null){evaluationDateField.setDisable(true);}
        else{
            Date previousEDate = evaluation.getEvaluationDate();
            LocalDate previousLocalEDate = new java.sql.Date(previousEDate.getTime()).toLocalDate();
            evaluationDateField.setValue(previousLocalEDate);
        }
        editEvaluationScreen.add(evaluationDateField, 1, 2);
        
        
        Label stornoReason = new Label("Dôvod storna:");
        editEvaluationScreen.add(stornoReason, 0, 3);

        TextField stornoReasonField = new TextField();
        if(evaluation.getStornoReason()==null){stornoReasonField.setDisable(true);}
        else{
            stornoReasonField.setText(evaluation.getStornoReason());
        }
        editEvaluationScreen.add(stornoReasonField, 1, 3);
        
        
               
        
        //int formerId = employee.getIdEmployee();
        
        Button newEvaluationBtn = new Button("Uložiť pohovor");
        newEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("uložiťotázkuz");
                    LocalDate localDate = plannedDateField.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);
                    final Evaluation newEvaluation = new Evaluation(evaluation.getIdEvaluation(),employee,date);
                    
                    if(evaluation.getStornoReason()!=null){
                        LocalDate localDate2 = evaluationDateField.getValue();
                        Instant instant2 = Instant.from(localDate2.atStartOfDay(ZoneId.systemDefault()));
                        Date date2 = Date.from(instant2);
                        newEvaluation.setEvaluationDate(date2);
                        newEvaluation.setStornoReason(stornoReasonField.getText());
                        newEvaluation.setEvaluationItems(evaluation.getEvaluationItems());
                    }
                    evalr.edit(newEvaluation); 
                    employee.getEvaluations().remove(evaluation);//questionable
                    employee.getEvaluations().add(newEvaluation);//questionable
                    refreshEvaluationTable(employee);
                    root.setCenter(employeeDetail(employee));
                });
        editEvaluationScreen.add(newEvaluationBtn,1,4);
        
        return editEvaluationScreen;        
       
    }
    
    private Pane stornoEvaluation(Evaluation evaluation, Employee employee){
        GridPane stornoEvaluation = new GridPane();
        stornoEvaluation.setAlignment(Pos.CENTER);
        stornoEvaluation.setHgap(10);
        stornoEvaluation.setVgap(10);
        stornoEvaluation.setPadding(new Insets(25, 25, 25, 25));

        
        Text scenetitle2 = new Text("Stornovanie pohovoru");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        stornoEvaluation.add(scenetitle2, 0, 0, 2, 1);
        Label plannedDate = new Label("Dátum pohovoru:");
        stornoEvaluation.add(plannedDate, 0, 1);

        DatePicker evaluationDateField = new DatePicker();
        stornoEvaluation.add(evaluationDateField, 1, 1);

        
        Label stornoReason = new Label("Dôvod storna:");
        stornoEvaluation.add(stornoReason, 0, 2);

        TextField stornoReasonField = new TextField();
        stornoEvaluation.add(stornoReasonField, 1, 2);
        
               
        
        //int formerId = employee.getIdEmployee();
        
        Button newEvaluationBtn = new Button("Uložiť stornovaný pohovor");
        newEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("uložiťotázkuz");
                    LocalDate localDate = evaluationDateField.getValue();
                    Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                    Date date = Date.from(instant);
                    final Evaluation newEvaluation = new Evaluation(evaluation.getIdEvaluation(),employee,date,evaluation.getPlannedDate(),stornoReasonField.getText(),evaluation.getEvaluationItems());
                    evalr.edit(newEvaluation); 
                    employee.getEvaluations().remove(evaluation);//questionable
                    employee.getEvaluations().add(newEvaluation);//questionable
                    refreshEvaluationTable(employee);
                    root.setCenter(employeeDetail(employee));
                });
        stornoEvaluation.add(newEvaluationBtn,1,3);
        
        return stornoEvaluation; 
    }
    
    
    
    private Pane editEvaluationItems(Evaluation evaluation){
        editEvaluationItemsScreen = new GridPane();
        editEvaluationItemsScreen.setAlignment(Pos.CENTER);
        editEvaluationItemsScreen.setHgap(10);
        editEvaluationItemsScreen.setVgap(10);
        editEvaluationItemsScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Zodpovedané otázky:");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editEvaluationItemsScreen.add(scenetitle2, 0, 0, 2, 1);
        
        List<Category> categoryList = cr.getAllCategories();
        int i=1;


        List<EvaluationItem> editedEvaluationItemList = new ArrayList<EvaluationItem>();
        List<ComboBox> editedComboBoxesList = new ArrayList<ComboBox>();
        List<TextField> editedTextFieldsList = new ArrayList<TextField>();
        List<Category> previouslyUnansweredCategoriesList = new ArrayList<Category>();
        
        
        Set<Category> answeredCategories = new HashSet<Category>(); 
        Set<EvaluationItem> evaluationItemList = evaluation.getEvaluationItems();   
        for(EvaluationItem ei:evaluationItemList){
            editedEvaluationItemList.add(ei);
            answeredCategories.add(ei.getCategory());
             Label categoryDescription = new Label(ei.getCategory().getDescription());
            editEvaluationItemsScreen.add(categoryDescription,0,i);
            Label categoryCoefficient = new Label(Integer.toString(ei.getCategory().getCoefficient()));
            editEvaluationItemsScreen.add(categoryCoefficient,1,i);
            ObservableList<Integer> options = 
                FXCollections.observableArrayList(
                    1,2,3,4,5
                );
            ComboBox scoreBox = new ComboBox(options);
            editedComboBoxesList.add(scoreBox);
            scoreBox.setValue(ei.getScore());
            TextField commentField = new TextField();
            editedTextFieldsList.add(commentField);
            commentField.setText(ei.getCommentary());
            int nullIndex = previouslyUnansweredCategoriesList.size();
            previouslyUnansweredCategoriesList.add(null);
     
            
            editEvaluationItemsScreen.add(scoreBox,2,i);
            editEvaluationItemsScreen.add(commentField,3,i);
            
            i++;
        }
        Text scenetitle3 = new Text("Nezodpovedané otázky:");
        scenetitle3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editEvaluationItemsScreen.add(scenetitle3, 0, i, 2, 1);
        i++;
        for(Category c : categoryList){
            if(/*!answeredCategories.contains(c)*/!checkCategoryInList(answeredCategories, c)){    
                previouslyUnansweredCategoriesList.add(c);
                Label categoryDescription = new Label(c.getDescription());
                editEvaluationItemsScreen.add(categoryDescription,0,i);
                Label categoryCoefficient = new Label(Integer.toString(c.getCoefficient()));
                editEvaluationItemsScreen.add(categoryCoefficient,1,i);
                ObservableList<Integer> options = 
                    FXCollections.observableArrayList(
                        1,2,3,4,5
                    );
                ComboBox scoreBox = new ComboBox(options);
                editedComboBoxesList.add(scoreBox);
                TextField commentField = new TextField();
                editedTextFieldsList.add(commentField);

                editEvaluationItemsScreen.add(scoreBox,2,i);
                editEvaluationItemsScreen.add(commentField,3,i);
                i++;
            }
        }
        
        
        Button saveEvaluationItemsBtn = new Button("Uložiť položky pohovorov");
        saveEvaluationItemsBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("uložiťotázkuz");
                    
                    
                    
                    
                    //editovanie zodpovednaých otázok:
                    for (int j=0; j<evaluationItemList.size();j++){
                        EvaluationItem formerEI = editedEvaluationItemList.get(j);
                        final EvaluationItem newEvaluationItem = new EvaluationItem(formerEI.getIdEvaluationItem(),formerEI.getCategory(),evaluation,(int)editedComboBoxesList.get(j).getValue());
                        if(editedTextFieldsList.get(j).getText()!=null){
                            newEvaluationItem.setCommentary(editedTextFieldsList.get(j).getText());
                        }
                        eir.edit(newEvaluationItem); 
                        evaluation.getEvaluationItems().remove(formerEI);
                        evaluation.getEvaluationItems().add(newEvaluationItem);
                    }
                    
                    //ukladanie novopridaných otázok
                    for (int k=evaluationItemList.size()/*+1*/; k<previouslyUnansweredCategoriesList.size()/*+1*/;k++){
                        if(editedComboBoxesList.get(k).getValue()!=null){
                            final EvaluationItem newEvaluationItem = new EvaluationItem(eir.freeId(),previouslyUnansweredCategoriesList.get(k),evaluation,(int)editedComboBoxesList.get(k).getValue());
                            if(!editedTextFieldsList.get(k).getText().isEmpty()){newEvaluationItem.setCommentary(editedTextFieldsList.get(k).getText());}
                            eir.create(newEvaluationItem);                             
                            evaluation.getEvaluationItems().add(newEvaluationItem);
                        }
                    }
                    root.setCenter(employeeDetail(evaluation.getEmployee()));
//                    
                });
        editEvaluationItemsScreen.add(saveEvaluationItemsBtn,1,i);
        return editEvaluationItemsScreen;
    }
    
    private Pane evaluationItemTable(Evaluation evaluation){
        GridPane evaluationItemTable = new GridPane();
        evaluationItemTable.setAlignment(Pos.CENTER);
        evaluationItemTable.setHgap(10);
        evaluationItemTable.setVgap(10);
        evaluationItemTable.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Zodpovedané otázky:");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        evaluationItemTable.add(scenetitle2, 0, 0, 2, 1);
        
        List<Category> categoryList = cr.getAllCategories();
        int i=1;


        
        
        Set<EvaluationItem> evaluationItemList = evaluation.getEvaluationItems();   
        for(EvaluationItem ei:evaluationItemList){
             Label categoryDescription = new Label(ei.getCategory().getDescription());
            evaluationItemTable.add(categoryDescription,0,i);
            Label categoryCoefficient = new Label(Integer.toString(ei.getCategory().getCoefficient()));
            evaluationItemTable.add(categoryCoefficient,1,i);
            
            Label score = new Label(Integer.toString(ei.getScore()));
            Label comment = new Label(ei.getCommentary());
            Button deleteButton = new Button("Vymazať");
            deleteButton.setOnAction((ActionEvent event) -> {
                System.out.println("vymazať evaluationitem");
                eir.delete(ei.getIdEvaluationItem());
                //vyškrtnúť a refreshnúť
                categoryDescription.setTextFill(Color.RED);
                categoryCoefficient.setTextFill(Color.RED);
                score.setTextFill(Color.RED);
                comment.setTextFill(Color.RED);
                deleteButton.setDisable(true);
                evaluation.getEvaluationItems().remove(ei);
                
            });
            
            
            evaluationItemTable.add(score,2,i);
            evaluationItemTable.add(comment,3,i);
            evaluationItemTable.add(deleteButton,4,i);
            i++;
        }
        
        
        
        return evaluationItemTable;
    }
    
    public void refreshEmployeeTable(){
        allEmployeesScreen.setItems(FXCollections.observableArrayList(er.getAllEmployees()));
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
    
    public void refreshCategoryTable(){
        allCategoriesScreen.setItems(FXCollections.observableArrayList(cr.getAllCategories()));
        TableColumn<Category,String> descriptionCol = new TableColumn<Category,String>("Popis");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        TableColumn<Category,String> coefficientCol = new TableColumn<Category,String>("Koeficient");
        coefficientCol.setCellValueFactory(new PropertyValueFactory("coefficient"));        
        allCategoriesScreen.getColumns().setAll(descriptionCol, coefficientCol);
    }
    
    public TableView<Evaluation> refreshEvaluationTable(Employee employee){
        TableView<Evaluation> evaluationTable = new TableView<Evaluation>();
            evaluationTable.setItems(FXCollections.observableArrayList(employee.getEvaluations()));
            TableColumn<Evaluation,String> evaluationDateCol = new TableColumn<Evaluation,String>("Dátum pohovoru");
            evaluationDateCol.setCellValueFactory(new PropertyValueFactory("evaluationDate"));
            TableColumn<Evaluation,String> plannedDateCol = new TableColumn<Evaluation,String>("Plánovaný pohovor");
            plannedDateCol.setCellValueFactory(new PropertyValueFactory("plannedDate"));
            TableColumn<Evaluation,String> stornoReasonCol = new TableColumn<Evaluation,String>("Dôvod storna");
            stornoReasonCol.setCellValueFactory(new PropertyValueFactory("stornoReason"));
            evaluationTable.setRowFactory( tv -> {
            TableRow<Evaluation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    Evaluation rowData = row.getItem();
                    System.out.println("zoberazujem detail zamestnanca"+rowData);
                    root.setCenter(evaluationDetail(rowData,evaluationTable,employee));
                }
            });
            return row ;
            });   
        evaluationTable.getColumns().setAll(evaluationDateCol, plannedDateCol,stornoReasonCol);
        return evaluationTable;
    }
    
    private boolean checkCategoryInList(Set<Category> answeredCategories, Category category){
            for(Category ac:answeredCategories){
                if(ac.getDescription().equals(category.getDescription())&&ac.getCoefficient()==(category.getCoefficient())){
                    return true;
                }
            }
        return false;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
