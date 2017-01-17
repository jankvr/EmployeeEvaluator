/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import model.Category;
import org.hibernate.exception.ConstraintViolationException;

/**
 * Trieda CategoryGUI spravuje všetky činnosti v rámci obrazoviek spracujúcich
 * otázky.
 * @author Jaroslav
 */
public class CategoryGUI {
    EmployeeEvaluator employeeEvaluator;
    TableView<Category> allCategoriesScreen;
    CategoryGUI(EmployeeEvaluator employeeEvaluator){
        this.employeeEvaluator = employeeEvaluator;
        
        allCategoriesScreen = new TableView<Category>();
        refreshCategoryTable();
  
        allCategoriesScreen.setRowFactory( tv -> {
            TableRow<Category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && (! row.isEmpty()) ) {
                    //zmeniť screen na detail zamestnanca
                    Category rowData = row.getItem();
                    System.out.println("zoberazujem detail zamestnanca"+rowData);
                    employeeEvaluator.getRoot().setCenter(categoryDetail(rowData));
                }
            });
        return row ;
        });
    }
    
    /**
     * Zobrazí detaily o otázke.
     * @param category zobrazovaná otázka
     * @return obrazovka s detailmi.
     */
    public Pane categoryDetail(Category category){
        VBox detailScreen = new VBox();
            HBox detailScreenButtons = new HBox();

                Button deleteCategoryBtn = new Button();
                deleteCategoryBtn.setText("Vymazať otázku");
                deleteCategoryBtn.setOnAction((ActionEvent event) -> {
                    try{
                        System.out.println("vymazaťCategory");
                        employeeEvaluator.getCr().delete(category.getIdCategory());
                        refreshCategoryTable();
                        employeeEvaluator.getRoot().setCenter(allCategoriesScreen);
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
                        employeeEvaluator.getCr().rollBack();
                    }
                });
                deleteCategoryBtn.setPrefWidth(200);  
            detailScreenButtons.getChildren().add(deleteCategoryBtn);
        
            VBox detailScreenData = new VBox();
            detailScreenData.getChildren().add(new Text("Description: "+category.getDescription()));
            detailScreenData.getChildren().add(new Text("Coefficient: "+category.getCoefficient()));
            

            
        detailScreen.getChildren().add(detailScreenButtons);
        detailScreen.getChildren().add(detailScreenData);
        return detailScreen;
    }
        
    /**
     * Vytvára obrazovku umožňujúcu založiť novú otázku.
     * @return obrazovka zakladajúca novú otázku.
     */
    public Pane newCategory(){
        GridPane newCategoryScreen = new GridPane();
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
                    
                    final Category newCategory = new Category(employeeEvaluator.getCr().freeId(),descriptionField.getText(),
                            (int)coefficientField.getValue());
                    employeeEvaluator.getCr().create(newCategory); 
                    refreshCategoryTable();
                    employeeEvaluator.getRoot().setCenter(allCategoriesScreen);
                });
        newCategoryScreen.add(editCategoryBtn,1,3);
        
        return newCategoryScreen;
    }
    
    /**
     * Skontrolovanie otázky v zozname otázok, porovnávanie cez popis a koeficient
     * @param answeredCategories množina zodpvoedaných otázok
     * @param category porovnávaná otázka
     * @return true, pokiaľ sa otázka v zozname nachádza, inak false
     */
    public boolean checkCategoryInList(Set<Category> answeredCategories, Category category){
            for(Category ac:answeredCategories){
                if(ac.getDescription().equals(category.getDescription())&&ac.getCoefficient()==(category.getCoefficient())){
                    return true;
                }
            }
        return false;
    }
    
    /**
     * Nanovo generuje tabuľku s otázkami
     */
    public void refreshCategoryTable(){
        allCategoriesScreen.setItems(FXCollections.observableArrayList(employeeEvaluator.getCr().getAllCategories()));
        TableColumn<Category,String> descriptionCol = new TableColumn<Category,String>("Popis");
        descriptionCol.setCellValueFactory(new PropertyValueFactory("description"));
        TableColumn<Category,String> coefficientCol = new TableColumn<Category,String>("Koeficient");
        coefficientCol.setCellValueFactory(new PropertyValueFactory("coefficient"));        
        allCategoriesScreen.getColumns().setAll(descriptionCol, coefficientCol);
    }

    /**
     * Vracia obrazovku so všetkými aktuálnymi otázkami.
     * @return 
     */
    public TableView<Category> getAllCategoriesScreen() {
        return allCategoriesScreen;
    }
    
    
}
