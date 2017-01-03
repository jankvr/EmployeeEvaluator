package models;
// Generated 3.1.2017 19:50:21 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Category generated by hbm2java
 */
public class Category  implements java.io.Serializable {


     private int idCategory;
     private String description;
     private int coefficient;
     private Set evaluationitems = new HashSet(0);

    public Category() {
    }

	
    public Category(int idCategory, String description, int coefficient) {
        this.idCategory = idCategory;
        this.description = description;
        this.coefficient = coefficient;
    }
    public Category(int idCategory, String description, int coefficient, Set evaluationitems) {
       this.idCategory = idCategory;
       this.description = description;
       this.coefficient = coefficient;
       this.evaluationitems = evaluationitems;
    }
   
    public int getIdCategory() {
        return this.idCategory;
    }
    
    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public int getCoefficient() {
        return this.coefficient;
    }
    
    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }
    public Set getEvaluationitems() {
        return this.evaluationitems;
    }
    
    public void setEvaluationitems(Set evaluationitems) {
        this.evaluationitems = evaluationitems;
    }




}

