/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.ArrayList;
import java.util.List;
import models.Evaluationitem;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 * Trida fungujici jako repozitar pro otazky v pohovoru. Prikazy jsou formovany ve stylu "QBE".
 * @author Jan Kovar
 */
public class EvaluationItemRepository {
    private final Session session;
    
    /**
     * Konstruktor, ktery vytvari session a uklada si ji do atributu.
     */
    public EvaluationItemRepository() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        
        this.session = sessionFactory.openSession();
    }
    
    
    /**
     * Vraci vsechny otazky v pohovoru z databaze serazenych podle id.
     * @return seznam vsech otazek v pohovoru
     */
    public List<Evaluationitem> getAllEvaluationItems() {
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Evaluationitem.class).addOrder(Order.asc("idEvaluationItem"));
        List<Evaluationitem> list = criteria.list();
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Vyhledavani otazek v pohovoru na zaklade danych parametru.
     * Logika je nasledujici: prozkouma se otazka v pohovoru predana v parametru a vyhleda se v databazi pomoci poli jinych, nez je primarni klic.
     * Pokud je zadan i primarni klic, vyhledava se primarne podle nej.
     * 
     * @param evaluationItem
     * @return seznam relevantnich otazek v pohovoru
     */
    public List<Evaluationitem> getEvaluationByParameter(Evaluationitem evaluationItem) {
        session.beginTransaction();

        Example example;
        List<Evaluationitem> list = new ArrayList<>();
        
        //vyhledavani podle parametru
        if (evaluationItem.getIdEvaluationItem()<= 0) {
            example = Example.create(evaluationItem).enableLike();
            Criteria criteria = session.createCriteria(Evaluationitem.class).add(example);
            list = criteria.list();
        }
        else { //vyhledavani podle ids
            Evaluationitem eval = (Evaluationitem) session.get(Evaluationitem.class, evaluationItem.getIdEvaluationItem());
            if (eval != null) {
                list.add(eval);
            }
        }
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Upravuje otazku v pohovoru.
     * 
     * @param evaluationItem upravovana otazka v pohovoru
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean edit(Evaluationitem evaluationItem) {
        boolean value = false;
        
        if (evaluationItem != null) {
            // kontrola, jestli je v db
            Evaluationitem evalItem = (Evaluationitem) session.get(Evaluationitem.class, evaluationItem.getIdEvaluationItem());
            
            if (validateEvaluationItem(evaluationItem) && evalItem != null) {
                session.beginTransaction();
                session.update(evaluationItem);
                value = true;
                session.getTransaction().commit();
            } 
        }
        
        return value;
    }

    /**
     * Pridava otazku v pohovoru.
     * 
     * @param evaluationItem pridavana otazka v pohovoru
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean create(Evaluationitem evaluationItem) {
        boolean value = false;
        if (evaluationItem != null) {
            
            // kontrola, jestli neni v db
            Evaluationitem evalItem = (Evaluationitem) session.get(Evaluationitem.class, evaluationItem.getIdEvaluationItem());
            
            if (validateEvaluationItem(evaluationItem) && evalItem == null) {
                session.beginTransaction();
                session.save(evaluationItem);
                session.getTransaction().commit();
                value = true;
            }
        }
        
        return value;
    }

    /**
     * Maze otazku v pohovoru.
     * 
     * @param id id mazane otazky v pohovoru
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean delete(int id) {
        boolean value = false;
        
        if (id > 0) {
            // vyhleda zamestnance
            Evaluationitem evaluationItem = (Evaluationitem) session.get(Evaluationitem.class, id);
            
            // pokud neni null, smaze se
            if (evaluationItem != null && validateEvaluationItem(evaluationItem)) {
                session.beginTransaction();
                session.delete(evaluationItem);
                session.getTransaction().commit();
                value = true;
            }
        }
        
        return value;
    }
    
    /**
     * Metoda nachazi volne id pro Evaluationitem.
     * 
     * @return volne id
     */
    public int freeId() {
        session.beginTransaction();

        // setridene id do listu
        Criteria criteria = session.createCriteria(Evaluationitem.class).setProjection(Projections.property("idEvaluationItem")).addOrder(Order.desc("idEvaluationItem"));
        
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
     * Pomocna metoda, ktera validuje Evaluationitem. 
     * Kontroluje neprazdnost udaju Evaluationitem, pokud je otazka prazdna, vrati se false.
     * 
     * @param evaluationItem otazka
     * @return true, pokud je validni; jinak false
     */
    private boolean validateEvaluationItem(Evaluationitem evaluationItem) {
        return (evaluationItem.getCategory() != null);
    }
}
