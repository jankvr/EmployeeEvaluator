/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeeevaluator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Employee;
import repositories.EmployeeRepository;

/**
 *
 * @author User
 */
public class EmployeeEvaluator extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // --------------------- TESTY --------------------------------------------------------
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
        
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction((ActionEvent event) -> {
            System.out.println("Hello World!");
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        primaryStage.setOnCloseRequest((WindowEvent we) -> {
            System.exit(0);
        });   
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
