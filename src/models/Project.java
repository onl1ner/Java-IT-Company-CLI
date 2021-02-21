package models;

import java.sql.SQLException;
import java.util.ArrayList;

import services.network.*;

/**
 * Class for the projects of some IT-company.
 */
public class Project {

    // Unique identificator of the project.
    private int id;

    // Name of the project.
    private String name;

    // Hours that needed to finish the project.
    private int totalHour;

    // Total cost of the project. 
    private int totalCost;

    // Employees that involved in this project.
    private ArrayList<Employee> employees;

    /**
     * Function calculates the total
     * cost of the project according
     * to total hours that were provided
     * in the constructor. 
     * 
     * @return The multiplication of 
     * total hours to sum of all
     * involved employee's hourly salaries.
     */
    private int calculateTotalCost() {
        int totalHourlySalary = 0;

        for(Employee emp : this.employees) {
            totalHourlySalary += emp.getHourlySalary();
        }

        return totalHourlySalary * this.totalHour;
    }

    /**
     * Function is used to set project id 
     * after the new record was inserted 
     * in the table.
     * 
     * @param id – new, auto-generated 
     * identificator of the project.
     */
    public void setID(int id) {
        this.id = id;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getTotalHour() {
        return this.totalHour;
    }

    public int getTotalCost() {
        return this.totalCost;
    }

    public ArrayList<Employee> getEmployees() {
        return this.employees;
    }

    /**
     * Constructor for creating a new project.
     * 
     * @param name – name of the new project.
     * @param totalHour – hours that needed to finish the project.
     * @param employees – employees that involved in the project.
     */
    public Project(String name, int totalHour, ArrayList<Employee> employees) {
        this.name = name;
        this.totalHour = totalHour;
        this.employees = employees;

        this.totalCost = this.calculateTotalCost();

        try {
            NetworkService.shared.create(this);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Constructor for serializing an existing record
     * for the project in database.
     * 
     * @param id – unique identificator of the project.
     * @param name – name of the project.
     * @param totalHour – hours that needed to finish the project.
     * @param totalCost – total cost of the project.
     */
    public Project(int id, String name, int totalHour, int totalCost) {
        this.id = id;
        this.name = name;
        this.totalHour = totalHour;
        this.totalCost = totalCost;
    }
}
