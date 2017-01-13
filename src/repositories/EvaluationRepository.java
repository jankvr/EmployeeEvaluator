/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.ArrayList;
import java.util.List;
import models.Evaluation;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 * Trida fungujici jako repozitar pro pohovory. Prikazy jsou formovany ve stylu "QBE".
 * @author Jan Kovar
 */
public class EvaluationRepository {
    private final Session session;
    
    /**
     * Konstruktor, ktery vytvari session a uklada si ji do atributu.
     */
    public EvaluationRepository() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        
        this.session = sessionFactory.openSession();
    }
    
    
    /**
     * Vraci vsechny pohovory z databaze serazenych podle id.
     * @return seznam vsech pohovoru
     */
    public List<Evaluation> getAllEvaluations() {
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Evaluation.class).addOrder(Order.asc("idEvaluation"));
        List<Evaluation> list = criteria.list();
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Vyhledavani pohovoru na zaklade danych parametru.
     * Logika je nasledujici: prozkouma se pohovor predany v parametru a vyhleda se v databazi pomoci poli jinych, nez je primarni klic.
     * Pokud je zadan i primarni klic, vyhledava se primarne podle nej.
     * 
     * @param evaluation pohovor
     * @return seznam relevantnich pohovoru
     */
    public List<Evaluation> getEvaluationsByParameter(Evaluation evaluation) {
        session.beginTransaction();

        Example example;
        List<Evaluation> list = new ArrayList<>();
        
        //vyhledavani podle parametru
        if (evaluation.getIdEvaluation()<= 0) {
            example = Example.create(evaluation).enableLike();
            Criteria criteria = session.createCriteria(Evaluation.class).add(example);
            list = criteria.list();
        }
        else { //vyhledavani podle ids
            Evaluation eval = (Evaluation) session.get(Evaluation.class, evaluation.getIdEvaluation());
            if (eval != null) {
                list.add(eval);
            }
        }
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Upravuje pohovor.
     * 
     * @param evaluation upravovany pohovor
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean edit(Evaluation evaluation) {
        boolean value = false;
        
        if (evaluation != null) {
            // kontrola, jestli je v db
            Evaluation eval = (Evaluation) session.get(Evaluation.class, evaluation.getIdEvaluation());
            
            if (validateEvaluation(evaluation) && eval != null) {
                session.beginTransaction();
                session.update(evaluation);
                value = true;
                session.getTransaction().commit();
            } 
        }
        
        return value;
    }

    /**
     * Vytvari otazku.
     * 
     * @param evaluation pridavany pohovor
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean create(Evaluation evaluation) {
        boolean value = false;
        if (evaluation != null) {
            
            // kontrola, jestli neni v db
            Evaluation eval = (Evaluation) session.get(Evaluation.class, evaluation.getIdEvaluation());
            
            if (validateEvaluation(evaluation) && eval == null) {
                session.beginTransaction();
                session.save(evaluation);
                session.getTransaction().commit();
                value = true;
            }
        }
        
        return value;
    }

    /**
     * Maze otazku.
     * 
     * @param id id mazaneho pohovoru
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean delete(int id) {
        boolean value = false;
        
        if (id > 0) {
            // vyhleda zamestnance
            Evaluation evaluation = (Evaluation) session.get(Evaluation.class, id);
            
            // pokud neni null, smaze se
            if (evaluation != null && validateEvaluation(evaluation)) {
                session.beginTransaction();
                session.delete(evaluation);
                session.getTransaction().commit();
                value = true;
            }
        }
        
        return value;
    }
    
    /**
     * Metoda nachazi volne id pro Evaluation.
     * 
     * @return volne id
     */
    public int freeId() {
        session.beginTransaction();

        // setridene id do listu
        Criteria criteria = session.createCriteria(Evaluation.class).setProjection(Projections.property("idEvaluation")).addOrder(Order.desc("idEvaluation"));
        
        List<Integer> list = criteria.list();
        
        // pokud seznam neni prazdny, hledej postupne prvni nejmensi volne id (recyklace id)
        if (!list.isEmpty()) {
            boolean isFound = false;
            int foundIndex = -1;
            
            // dvojity for cyklus
            for (int index = 1; index <= list.get(0); index ++) {
                isFound = false;
                for (Integer i : list) {
                    if (i == index) {
                        isFound = true;
                    }
                }
                if (!isFound) {
                    foundIndex = index;
                    break;
                }
            }
            
            session.getTransaction().commit();
            // pokud neni nalezeno vhodne cislo, vrati max+1, jinak vraci vhodne cislo
            return (isFound) ? list.get(0) + 1 : foundIndex;
        }
        
        session.getTransaction().commit();
        return 1;
    }
    
    /**
     * Pomocna metoda, ktera validuje pohovor. 
     * Kontroluje neprazdnost udaju pohovoru, pokud je planovane datum prazdne, vrati se false.
     * 
     * @param evaluation otazka
     * @return true, pokud je validni; jinak false
     */
    private boolean validateEvaluation(Evaluation evaluation) {
        return (evaluation.getPlannedDate() != null);
    }
}
