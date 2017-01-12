/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repositories;

import java.util.ArrayList;
import java.util.List;
import models.Category;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 * POTREBA REFAKTOROVAT KOMENTARE, ATD.
 * @author User
 */
public class CategoryRepository {
    private final Session session;
    
    /**
     * Konstruktor, ktery vytvari session a uklada si ji do atributu.
     */
    public CategoryRepository() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        
        this.session = sessionFactory.openSession();
    }
    
    
    /**
     * Vraci vsechny zamestnance z databaze serazenych podle id.
     * @return seznam vsech zamestnancu
     */
    public List<Category> getAllCategories() {
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Category.class).addOrder(Order.asc("idCategory"));
        List<Category> list = criteria.list();
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Vyhledavani zamestnancu na zaklade danych parametru.
     * Logika je nasledujici: prozkouma se zamestnanec predany v parametru a vyhleda se v databazi pomoci poli jinych, nez je primarni klic.
     * Pokud je zadan i primarni klic, vyhledava se primarne podle nej.
     * 
     * @param category
     * @return seznam relevantnich zamestnancu
     */
    public List<Category> getCategoriesByParameters(Category category) {
        session.beginTransaction();

        Example example;
        List<Category> list = new ArrayList<>();
        
        //vyhledavani podle parametru
        if (category.getIdCategory()<= 0) {
            example = Example.create(category).enableLike();
            Criteria criteria = session.createCriteria(Category.class).add(example);
            list = criteria.list();
        }
        else { //vyhledavani podle ids
            Category cat = (Category) session.get(Category.class, category.getIdCategory());
            if (cat != null) {
                list.add(cat);
            }
        }
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Upravuje zamestnance.
     * 
     * @param category upravovany zamestnanec
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean edit(Category category) {
        boolean value = false;
        
        if (category != null) {
            // kontrola, jestli je v db
            Category cat = (Category) session.get(Category.class, category.getIdCategory());
            
            if (validateCategory(category) && cat != null) {
                session.beginTransaction();
                session.update(category);
                value = true;
                session.getTransaction().commit();
            } 
        }
        
        return value;
    }

    /**
     * Vytvari zamestnance.
     * 
     * @param category pridavany zamestnanec
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean create(Category category) {
        boolean value = false;
        if (category != null) {
            
            // kontrola, jestli neni v db
            Category cat = (Category) session.get(Category.class, category.getIdCategory());
            
            if (validateCategory(category) && cat == null) {
                session.beginTransaction();
                session.save(category);
                session.getTransaction().commit();
                value = true;
            }
        }
        
        return value;
    }

    /**
     * Maze zamestnance.
     * 
     * @param id id mazaneho zamestnance
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean delete(int id) {
        boolean value = false;
        
        if (id > 0) {
            // vyhleda zamestnance
            Category category = (Category) session.get(Category.class, id);
            
            // pokud neni null, smaze se
            if (category != null && validateCategory(category)) {
                session.beginTransaction();
                session.delete(category);
                session.getTransaction().commit();
                value = true;
            }
        }
        
        return value;
    }
    
    /**
     * Metoda nachazi volne id pro zamestnance.
     * 
     * @return volne id
     */
    public int freeId() {
        session.beginTransaction();

        // setridene id do listu
        Criteria criteria = session.createCriteria(Category.class).setProjection(Projections.property("idCategory")).addOrder(Order.desc("idCategory"));
        
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
     * Pomocna metoda, ktera validuje zamestnance. 
     * Kontroluje neprazdnost udaju zamestnance, pokud je jeden prvek prazdny, vrati se false.
     * 
     * @param category zamestnanec
     * @return true, pokud je validni; jinak false
     */
    private boolean validateCategory(Category category) {
        return (category.getDescription() != null);
    }
}
