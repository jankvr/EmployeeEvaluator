/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeeevaluator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    
    GridPane defaultScreen;
    GridPane newEmployeeScreen;
    GridPane editEmployeeScreen;
    GridPane deleteEmployeeScreen;
    GridPane newEvaluationScreen;
    GridPane editEvaluationScreen;
    GridPane deleteEvaluationScreen;
    GridPane newCategoryScreen;
    GridPane editCategoryScreen;
    GridPane deleteCategoryScreen;
    
    
    
    @Override
    public void start(Stage primaryStage) {
//        // --------------------- TESTY --------------------------------------------------------
        EmployeeRepository er = new EmployeeRepository();

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
        
        
        Button newEvaluationBtn = new Button();
        newEvaluationBtn.setText("Nové hodnotenie");
        newEvaluationBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Nové hodnotenie!");
            root.setCenter(newEvaluationScreen);
        });
        newEvaluationBtn.setPrefWidth(200);
        
        Button editEvaluationBtn = new Button();
        editEvaluationBtn.setText("Upraviť hodnotenie");
        editEvaluationBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Upraviť hodnotenie!");
            root.setCenter(editEvaluationScreen);
        });
        editEvaluationBtn.setPrefWidth(200);
        
        Button deleteEvaluationBtn = new Button();
        deleteEvaluationBtn.setText("Vymazať hodnotenie");
        deleteEvaluationBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Vymazať hodnotenie");
            root.setCenter(deleteEvaluationScreen);
        });
        deleteEvaluationBtn.setPrefWidth(200);
        
        
        
        
        Button newCategoryBtn = new Button();
        newCategoryBtn.setText("Nová otázka");
        newCategoryBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Nová otázka!");
            root.setCenter(newCategoryScreen);
        });
        newCategoryBtn.setPrefWidth(200);
        
        Button editCategoryBtn = new Button();
        editCategoryBtn.setText("Upraviť otázku");
        editCategoryBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Upraviť otázku!");
            root.setCenter(editCategoryScreen);
        });
        editCategoryBtn.setPrefWidth(200);
        
        Button deleteCategoryBtn = new Button();
        deleteCategoryBtn.setText("Vymazať otázku");
        deleteCategoryBtn.setOnAction((ActionEvent event) -> {
            System.out.println("Vymazať otázku");
            root.setCenter(deleteCategoryScreen);
        });
        deleteCategoryBtn.setPrefWidth(200);


        
        
        VBox menu = new VBox();
        menu.getChildren().add(newEmployeeBtn);
        menu.getChildren().add(editEmployeeBtn);
        menu.getChildren().add(deleteEmployeeBtn);
        menu.getChildren().add(newEvaluationBtn);
        menu.getChildren().add(editEvaluationBtn);
        menu.getChildren().add(deleteEvaluationBtn);
        menu.getChildren().add(newCategoryBtn);
        menu.getChildren().add(editCategoryBtn);
        menu.getChildren().add(deleteCategoryBtn);
        
        root.setLeft(menu);
        
        
//        VBox displayer = new VBox();
//        displayer.getChildren().add(new Button());
        
        
        setScreens();
        
        
        
        root.setCenter(defaultScreen);
        
        Scene scene = new Scene(root, 800, 600);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });   
    }

    
    private void setScreens(){
        
        //základná obrazovka
        defaultScreen = new GridPane();
        defaultScreen.setAlignment(Pos.CENTER);
        defaultScreen.setHgap(10);
        defaultScreen.setVgap(10);
        defaultScreen.setPadding(new Insets(25, 25, 25, 25));

        Text dsscenetitle = new Text("Default Screen");
        dsscenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        defaultScreen.add(dsscenetitle, 0, 0, 2, 1);

//        Label userName = new Label("User Name:");
//        defaultScreen.add(userName, 0, 1);
        
  
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
        
        
        newEvaluationScreen = new GridPane();
        newEvaluationScreen.setAlignment(Pos.CENTER);
        newEvaluationScreen.setHgap(10);
        newEvaluationScreen.setVgap(10);
        newEvaluationScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle4 = new Text("new Eval Screen");
        scenetitle4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        newEvaluationScreen.add(scenetitle4, 0, 0, 2, 1);
        
        
        
        editEvaluationScreen= new GridPane();
        editEvaluationScreen.setAlignment(Pos.CENTER);
        editEvaluationScreen.setHgap(10);
        editEvaluationScreen.setVgap(10);
        editEvaluationScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle5 = new Text("editw Eval Screen");
        scenetitle5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editEvaluationScreen.add(scenetitle5, 0, 0, 2, 1);
        
        
        deleteEvaluationScreen = new GridPane();
        deleteEvaluationScreen.setAlignment(Pos.CENTER);
        deleteEvaluationScreen.setHgap(10);
        deleteEvaluationScreen.setVgap(10);
        deleteEvaluationScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle6 = new Text("delete Eval Screen");
        scenetitle6.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        deleteEvaluationScreen.add(scenetitle6, 0, 0, 2, 1);
        
        
        
        
        newCategoryScreen= new GridPane();
        newCategoryScreen.setAlignment(Pos.CENTER);
        newCategoryScreen.setHgap(10);
        newCategoryScreen.setVgap(10);
        newCategoryScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle7 = new Text("new catl Screen");
        scenetitle7.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        newCategoryScreen.add(scenetitle7, 0, 0, 2, 1);
        
        
        
        editCategoryScreen= new GridPane();
        editCategoryScreen.setAlignment(Pos.CENTER);
        editCategoryScreen.setHgap(10);
        editCategoryScreen.setVgap(10);
        editCategoryScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle8 = new Text("edit categ Screen");
        scenetitle8.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editCategoryScreen.add(scenetitle8, 0, 0, 2, 1);
        
        
        deleteCategoryScreen = new GridPane();
        deleteCategoryScreen.setAlignment(Pos.CENTER);
        deleteCategoryScreen.setHgap(10);
        deleteCategoryScreen.setVgap(10);
        deleteCategoryScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle9 = new Text("delete categ Screen");
        scenetitle9.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        deleteCategoryScreen.add(scenetitle9, 0, 0, 2, 1);
        
        
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
