/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
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
 * Trieda spravuje všetky činnosti týkajúce sa pohovorov v rámci GUI
 * @author Jaroslav
 */
public class EvaluationGUI {
    EmployeeEvaluator employeeEvaluator;
    EvaluationGUI(EmployeeEvaluator employeeEvaluator){
        this.employeeEvaluator= employeeEvaluator;
    }
     
    /**
     * Vracia obrazovku s detailmi o pohovore
     * @param evaluation pohovor
     * @param evaluationTable tabuľka pohovorov
     * @param employee zamestnanec, ktorého sa pohovor týka
     * @return 
     */
    public Pane evaluationDetail(Evaluation evaluation,TableView<Evaluation> evaluationTable, Employee employee){
        VBox detailScreen = new VBox();
            HBox detailScreenButtons = new HBox();
                Button editEvaluationBtn = new Button();
                editEvaluationBtn.setText("Upraviť pohovor");
                editEvaluationBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("upraviťpohovor!");
                    
                    employeeEvaluator.getRoot().setCenter(editEvaluation(evaluation,employee));
                });
                editEvaluationBtn.setPrefWidth(200);

                Button stornoEvaluationBtn = new Button();
                stornoEvaluationBtn.setText("Stornovať pohovor");
                stornoEvaluationBtn.setOnAction((ActionEvent event) -> {
                    try{
                        System.out.println("vymazaťpohovor");
                        
                        
                        employeeEvaluator.getEmployeeGUI().refreshEmployeeTable();
                        employeeEvaluator.getRoot().setCenter(stornoEvaluation(evaluation,employee));
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
                        employeeEvaluator.getEr().rollBack();
                    }
                });
                stornoEvaluationBtn.setPrefWidth(200);  
                
                Button deleteEvaluationBtn = new Button();
                deleteEvaluationBtn.setText("Vymazať pohovor");
                deleteEvaluationBtn.setOnAction((ActionEvent event) -> {
                    try{
                        System.out.println("vymazaťpoh");
                        employeeEvaluator.getEvalr().delete(evaluation.getIdEvaluation());
                        employee.getEvaluations().remove(evaluation);
                        refreshEvaluationTable(employee);
                        employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEmployeeGUI().employeeDetail(employee));
                        
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
                        employeeEvaluator.getEvalr().rollBack();
                    }
                });
                deleteEvaluationBtn.setPrefWidth(200);  
                
                Button editItemsBtn = new Button();
                editItemsBtn.setText("Upraviť položky");
                editItemsBtn.setOnAction((ActionEvent event) -> {
                    System.out.println("Upraviť položky");

                    employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEvaluationItemGUI().editEvaluationItems(evaluation));
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
            

            
            Pane evaluationItemTable = employeeEvaluator.getEvaluationItemGUI().evaluationItemTable(evaluation);
            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        detailScreen.getChildren().add(evaluationItemTable);
        return detailScreen;
    }
    
    
    
    
    
    
        
    
    /**
     * Vytvára obrazovku na založenie nového pohovoru
     * @param employee zamestnanec, ktorého sa nový pohovor týka
     * @return obrazovka na vytvorenie pohovoru
     */
    public Pane newEvaluation(Employee employee){
        GridPane newEvaluationScreen = new GridPane();
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
                    final Evaluation newEvaluation = new Evaluation(employeeEvaluator.getEvalr().freeId(),employee,date);
                    employeeEvaluator.getEvalr().create(newEvaluation); 
                    employee.getEvaluations().add(newEvaluation);//questionable
                    refreshEvaluationTable(employee);
                    employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEmployeeGUI().employeeDetail(employee));
                });
        newEvaluationScreen.add(newEvaluationBtn,1,3);
        
        return newEvaluationScreen;
    }
    
    /**
     * Vracia obrazovku na editovanie údajov o pohovore
     * @param evaluation editovaný pohovor
     * @param employeezamestnanec, ktorého sa pohovor týka
     * @return obrazovka an editovanie pohovoru
     */
    public Pane editEvaluation(Evaluation evaluation, Employee employee){
        
        GridPane editEvaluationScreen = new GridPane();
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
                    employeeEvaluator.getEvalr().edit(newEvaluation); 
                    employee.getEvaluations().remove(evaluation);//questionable
                    employee.getEvaluations().add(newEvaluation);//questionable
                    refreshEvaluationTable(employee);
                    employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEmployeeGUI().employeeDetail(employee));
                });
        editEvaluationScreen.add(newEvaluationBtn,1,4);
        
        return editEvaluationScreen;        
       
    }
    
    /**
     * Vracia obrazovku na zadanie detailov o stornovanom pohovore
     * @param evaluation stornovaný pohovor
     * @param employee zamestnanec, ktorého sa pohovor týka
     * @return 
     */
    public Pane stornoEvaluation(Evaluation evaluation, Employee employee){
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
                    employeeEvaluator.getEvalr().edit(newEvaluation); 
                    employee.getEvaluations().remove(evaluation);//questionable
                    employee.getEvaluations().add(newEvaluation);//questionable
                    refreshEvaluationTable(employee);
                    employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEmployeeGUI().employeeDetail(employee));
                });
        stornoEvaluation.add(newEvaluationBtn,1,3);
        
        return stornoEvaluation; 
    }
    
    /**
     * Vracia aktuálnu tabuľku pohovorov pre daného zamestnanca
     * @param employee zamestnanec, ktorého sa pohovory týkajú
     * @return tabuľka pohovorov zamestnanca
     */
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
                    employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEvaluationGUI().evaluationDetail(rowData,evaluationTable,employee));
                }
            });
            return row ;
            });   
        evaluationTable.getColumns().setAll(evaluationDateCol, plannedDateCol,stornoReasonCol);
        return evaluationTable;
    }
    
}
