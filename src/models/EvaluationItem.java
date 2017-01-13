package models;
// Generated 13.1.2017 16:38:33 by Hibernate Tools 4.3.1



/**
 * EvaluationItem generated by hbm2java
 */
public class EvaluationItem  implements java.io.Serializable {


     private int idEvaluationItem;
     private Category category;
     private Evaluation evaluation;
     private int score;
     private String commentary;

    public EvaluationItem() {
    }

	
    public EvaluationItem(int idEvaluationItem, Category category, Evaluation evaluation, int score) {
        this.idEvaluationItem = idEvaluationItem;
        this.category = category;
        this.evaluation = evaluation;
        this.score = score;
    }
    public EvaluationItem(int idEvaluationItem, Category category, Evaluation evaluation, int score, String commentary) {
       this.idEvaluationItem = idEvaluationItem;
       this.category = category;
       this.evaluation = evaluation;
       this.score = score;
       this.commentary = commentary;
    }
   
    public int getIdEvaluationItem() {
        return this.idEvaluationItem;
    }
    
    public void setIdEvaluationItem(int idEvaluationItem) {
        this.idEvaluationItem = idEvaluationItem;
    }
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    public Evaluation getEvaluation() {
        return this.evaluation;
    }
    
    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
    public int getScore() {
        return this.score;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    public String getCommentary() {
        return this.commentary;
    }
    
    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }




}


