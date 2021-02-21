package models;

import java.sql.SQLException;
import services.network.*;

/**
 * Base class for an employee of some IT-company. 
 */
public abstract class Employee {

    // Unique identificator of worker, 
    // acts as primary key in DB. 
    // - Important: 
    // Must be defined as a SERIAL type 
    // in the table declaration.
    private int workerID;

    // Name of the Employee.
    private String name;

    // Amount of money that employee gains in an hour.
    private int hourlySalary;

    public int getWorkerID() {
        return this.workerID;
    }

    /**
     * Function is used to set worker id 
     * after the new record was inserted 
     * in the table.
     * 
     * @param workerID – new, auto-generated 
     * identificator of the employee.
     */
    public void setWorkerID(int workerID) {
        this.workerID = workerID;
    }

    public String getName() {
        return this.name;
    }

    public int getHourlySalary() {
        return this.hourlySalary;
    }

    public abstract String getPosition();
    
    @Override
    public boolean equals(Object object) {
        // We have to override equals(Object) function
        // to test for the equality of instances
        // of the Employee class.
        if (object instanceof Employee) {
            return this.workerID == ((Employee) object).workerID;
        }

        return false;
    }

    /**
     * Constructor to construct new entry 
     * of an employee.
     * 
     * @param name – name of the new employee.
     * @param hourlySalary – hourly salary of the new employee.
     */
    public Employee(String name, int hourlySalary) {
        this.name = name;
        this.hourlySalary = hourlySalary;

        try {
            NetworkService.shared.create(this);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * Constructor to serialize existing 
     * record of an employee in the database.
     * 
     * @param workerID – worker identificator of an employee.
     * @param name – name of an employee.
     * @param hourlySalary – hourly salary of an employee.
     */
    public Employee(int workerID, String name, int hourlySalary) {
        this.workerID = workerID;
        this.name = name;
        this.hourlySalary = hourlySalary;
    }
}
