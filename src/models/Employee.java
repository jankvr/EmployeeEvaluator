package models;
// Generated 13.1.2017 16:33:39 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Employee generated by hbm2java
 */
public class Employee  implements java.io.Serializable {


     private int idEmployee;
     private String firstName;
     private String lastName;
     private String birthNumber;
     private String role;
     private Set evaluations = new HashSet(0);

    public Employee() {
    }

	
    public Employee(int idEmployee, String firstName, String lastName, String birthNumber, String role) {
        this.idEmployee = idEmployee;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthNumber = birthNumber;
        this.role = role;
    }
    public Employee(int idEmployee, String firstName, String lastName, String birthNumber, String role, Set evaluations) {
       this.idEmployee = idEmployee;
       this.firstName = firstName;
       this.lastName = lastName;
       this.birthNumber = birthNumber;
       this.role = role;
       this.evaluations = evaluations;
    }
   
    public int getIdEmployee() {
        return this.idEmployee;
    }
    
    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }
    public String getFirstName() {
        return this.firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getBirthNumber() {
        return this.birthNumber;
    }
    
    public void setBirthNumber(String birthNumber) {
        this.birthNumber = birthNumber;
    }
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    public Set getEvaluations() {
        return this.evaluations;
    }
    
    public void setEvaluations(Set evaluations) {
        this.evaluations = evaluations;
    }




}


