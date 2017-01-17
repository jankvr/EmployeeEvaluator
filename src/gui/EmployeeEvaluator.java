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
 * Hlavná trieda GUI.
 * @author Jaroslav Fedorčák, Jan Kovář
 */
public class EmployeeEvaluator extends Application {
    
    //TableView<Employee> allEmployeesScreen;
    GridPane newEmployeeScreen;
    GridPane editEmployeeScreen;
    GridPane deleteEmployeeScreen;
    GridPane newEvaluationScreen;
    GridPane editEvaluationScreen;
    GridPane deleteEvaluationScreen;
    //TableView<Category> allCategoriesScreen;
    GridPane newCategoryScreen;
    GridPane editCategoryScreen;
    GridPane deleteCategoryScreen;
    GridPane editEvaluationItemsScreen;
    EmployeeRepository er;
    CategoryRepository cr;
    EvaluationItemRepository eir;
    EvaluationRepository evalr;
    BorderPane root;
    EmployeeGUI employeeGUI;
    CategoryGUI categoryGUI;
    EvaluationGUI evaluationGUI;
    EvaluationItemGUI evaluationItemGUI;
    
    
    
    @Override
    public void start(Stage primaryStage) {
                
        // ------------------- GUI --------------------------------------------------
        
        /*EmployeeRepository*/ er = new EmployeeRepository();
        cr=new CategoryRepository();
        eir=new EvaluationItemRepository();
        evalr=new EvaluationRepository();
        root = new BorderPane();
        
        
        employeeGUI = new EmployeeGUI(this);
        categoryGUI = new CategoryGUI(this);
        evaluationGUI = new EvaluationGUI(this);
        evaluationItemGUI = new EvaluationItemGUI(this);
        
        Button allEmployeesBtn = new Button();
        allEmployeesBtn.setText("Všetci zamestnanci");
        allEmployeesBtn.setOnAction((ActionEvent event) -> {
            System.out.println("všetcizamestnanci!");
            
            root.setCenter(employeeGUI.getAllEmployeesScreen());
        });
        allEmployeesBtn.setPrefWidth(200);
        
        Button newEmployeeBtn = new Button();
        newEmployeeBtn.setText("Nový zamestnanec");
        newEmployeeBtn.setOnAction((ActionEvent event) -> {
            System.out.println("novýzamestnanec!");
            root.setCenter(employeeGUI.newEmployee());
        });
        newEmployeeBtn.setPrefWidth(200);
        
        Button allCategoriesBtn = new Button();
        allCategoriesBtn.setText("Všetky otázky");
        allCategoriesBtn.setOnAction((ActionEvent event) -> {
            System.out.println("všetciotázky!");
            categoryGUI.refreshCategoryTable();
            root.setCenter(categoryGUI.getAllCategoriesScreen());
        });
        allCategoriesBtn.setPrefWidth(200);
        
        Button newCategoryBtn = new Button();
        newCategoryBtn.setText("Nová otázka");
        newCategoryBtn.setOnAction((ActionEvent event) -> {
            System.out.println("nováotázka!");
            root.setCenter(categoryGUI.newCategory());
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
        
        
//        setScreens();
        
        
        
        root.setCenter(employeeGUI.getAllEmployeesScreen());
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("The greatest EmployeeEvaluator in the whole 4IT353!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });   
        
        
    }

    
//    private void setScreens(){
//        
//        //základná obrazovka
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        
//        deleteEmployeeScreen = new GridPane();
//        deleteEmployeeScreen.setAlignment(Pos.CENTER);
//        deleteEmployeeScreen.setHgap(10);
//        deleteEmployeeScreen.setVgap(10);
//        deleteEmployeeScreen.setPadding(new Insets(25, 25, 25, 25));
//
//        Text scenetitle3 = new Text("Vymazanie zamestnanca");
//        scenetitle3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        deleteEmployeeScreen.add(scenetitle3, 0, 0, 2, 1);
//        
//        
//        
//        
//        
//    }

    public EmployeeGUI getEmployeeGUI() {
        return employeeGUI;
    }

    public CategoryGUI getCategoryGUI() {
        return categoryGUI;
    }

    public EvaluationGUI getEvaluationGUI() {
        return evaluationGUI;
    }

    public EvaluationItemGUI getEvaluationItemGUI() {
        return evaluationItemGUI;
    }

    public EmployeeRepository getEr() {
        return er;
    }

    public CategoryRepository getCr() {
        return cr;
    }

    public EvaluationItemRepository getEir() {
        return eir;
    }

    public EvaluationRepository getEvalr() {
        return evalr;
    }

    public BorderPane getRoot() {
        return root;
    }
    
    
   
    
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
