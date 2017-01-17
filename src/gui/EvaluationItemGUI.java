/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Category;
import model.Evaluation;
import model.EvaluationItem;

/**
 * Trieda EvaluationItemGUI spravuje všetky činnosti v rámci obrazoviek spracujúcich
 * položky pohovorov.
 * @author Jaroslav
 */
public class EvaluationItemGUI {
    EmployeeEvaluator employeeEvaluator;
    EvaluationItemGUI(EmployeeEvaluator employeeEvaluator){
        this.employeeEvaluator= employeeEvaluator;
    }
        
            
    /**
     * Vytvára obrazovku umožňujúcu editovať prvky pohovoru.
     * @param evaluation editované pohovor
     * @return obrazovka na editovanie.
     */
    public Pane editEvaluationItems(Evaluation evaluation){
        GridPane editEvaluationItemsScreen = new GridPane();
        editEvaluationItemsScreen.setAlignment(Pos.CENTER);
        editEvaluationItemsScreen.setHgap(10);
        editEvaluationItemsScreen.setVgap(10);
        editEvaluationItemsScreen.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Zodpovedané otázky:");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        editEvaluationItemsScreen.add(scenetitle2, 0, 0, 2, 1);
        
        List<Category> categoryList = employeeEvaluator.getCr().getAllCategories();
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
            if(/*!answeredCategories.contains(c)*/!employeeEvaluator.getCategoryGUI().checkCategoryInList(answeredCategories, c)){    
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
                        employeeEvaluator.getEir().edit(newEvaluationItem); 
                        evaluation.getEvaluationItems().remove(formerEI);
                        evaluation.getEvaluationItems().add(newEvaluationItem);
                    }
                    
                    //ukladanie novopridaných otázok
                    for (int k=evaluationItemList.size()/*+1*/; k<previouslyUnansweredCategoriesList.size()/*+1*/;k++){
                        if(editedComboBoxesList.get(k).getValue()!=null){
                            final EvaluationItem newEvaluationItem = new EvaluationItem(employeeEvaluator.getEir().freeId(),previouslyUnansweredCategoriesList.get(k),evaluation,(int)editedComboBoxesList.get(k).getValue());
                            if(!editedTextFieldsList.get(k).getText().isEmpty()){newEvaluationItem.setCommentary(editedTextFieldsList.get(k).getText());}
                            employeeEvaluator.getEir().create(newEvaluationItem);                             
                            evaluation.getEvaluationItems().add(newEvaluationItem);
                        }
                    }
                    employeeEvaluator.getRoot().setCenter(employeeEvaluator.getEmployeeGUI().employeeDetail(evaluation.getEmployee()));
//                    
                });
        editEvaluationItemsScreen.add(saveEvaluationItemsBtn,1,i);
        return editEvaluationItemsScreen;
    }
    
    /**
     * Vracia tabuľku s položkami pohovoru jednotlivých položiek.
     * @param evaluation pohovor, ktorého sa položky týkajú
     * @return tabuľka s položkami
     */
    public Pane evaluationItemTable(Evaluation evaluation){
        GridPane evaluationItemTable = new GridPane();
        evaluationItemTable.setAlignment(Pos.CENTER);
        evaluationItemTable.setHgap(10);
        evaluationItemTable.setVgap(10);
        evaluationItemTable.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle2 = new Text("Zodpovedané otázky:");
        scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        evaluationItemTable.add(scenetitle2, 0, 0, 2, 1);
        
//        List<Category> categoryList = employeeEvaluator.getCr().getAllCategories();
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
                employeeEvaluator.getEir().delete(ei.getIdEvaluationItem());
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
    
    
    
}
