/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import java.util.ArrayList;
import java.util.List;
import model.Employee;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;

/**
 *
 * Trida fungujici jako repozitar pro zamestnance. Prikazy jsou formovany ve stylu "QBE".
 * 
 * @author Jan Kovar
 */
public class EmployeeRepository {
    
    private final Session session;
    
    /**
     * Konstruktor, ktery vytvari session a uklada si ji do atributu.
     */
    public EmployeeRepository() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        
        this.session = sessionFactory.openSession();
    }
    
    
    /**
     * Vraci vsechny zamestnance z databaze serazenych podle id.
     * @return seznam vsech zamestnancu
     */
    public List<Employee> getAllEmployees() {
        session.beginTransaction();

        Criteria criteria = session.createCriteria(Employee.class).addOrder(Order.asc("idEmployee"));
        List<Employee> list = criteria.list();
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Vyhledavani zamestnancu na zaklade danych parametru.
     * Logika je nasledujici: prozkouma se zamestnanec predany v parametru a vyhleda se v databazi pomoci poli jinych, nez je primarni klic.
     * Pokud je zadan i primarni klic, vyhledava se primarne podle nej.
     * 
     * @param employee
     * @return seznam relevantnich zamestnancu
     */
    public List<Employee> getEmployeesByParameters(Employee employee) {
        session.beginTransaction();

        Example example;
        List<Employee> list = new ArrayList<>();
        
        //vyhledavani podle parametru
        if (employee.getIdEmployee() <= 0) {
            example = Example.create(employee).enableLike();
            Criteria criteria = session.createCriteria(Employee.class).add(example);
            list = criteria.list();
        }
        else { //vyhledavani podle ids
            Employee emp = (Employee) session.get(Employee.class, employee.getIdEmployee());
            if (emp != null) {
                list.add(emp);
            }
        }
        
        session.getTransaction().commit();
        return list;
    }
    
    /**
     * Upravuje zamestnance.
     * 
     * @param employee upravovany zamestnanec
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean edit(Employee employee) {
        boolean value = false;
        
        if (employee != null) {
            // kontrola, jestli je v db
            Employee emp = (Employee) session.get(Employee.class, employee.getIdEmployee());
            
            if (validateEmployee(employee) && emp != null) {
                session.beginTransaction();
                session.merge(employee);
                value = true;
                session.getTransaction().commit();
            } 
        }
        
        return value;
    }

    /**
     * Vytvari zamestnance.
     * 
     * @param employee pridavany zamestnanec
     * @return true, pokud vse skonci v poradku; false v opacnem pripade
     */
    public boolean create(Employee employee) {
        boolean value = false;
        if (employee != null) {
            
            // kontrola, jestli neni v db
            Employee emp = (Employee) session.get(Employee.class, employee.getIdEmployee());
            
            if (validateEmployee(employee) && emp == null) {
                session.beginTransaction();
                session.save(employee);
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
            Employee employee = (Employee) session.get(Employee.class, id);
            
            // pokud neni null, smaze se
            if (employee != null && validateEmployee(employee)) {
                session.beginTransaction();
                session.delete(employee);
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
        Criteria criteria = session.createCriteria(Employee.class).setProjection(Projections.property("idEmployee")).addOrder(Order.desc("idEmployee"));
        
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
     * @param employee zamestnanec
     * @return true, pokud je validni; jinak false
     */
    private boolean validateEmployee(Employee employee) {
        return (employee.getBirthNumber() != null 
                && employee.getFirstName() != null
                && employee.getLastName() != null
                && employee.getIdEmployee() > 0 
                && employee.getRole() != null);
    }
}
