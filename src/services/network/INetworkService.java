package services.network;

import java.util.ArrayList;
import java.sql.SQLException;

import models.*;

public interface INetworkService {
    /**
     * Function to create a new entry of 
     * employee in SQL database.
     * 
     * @param emp – instance of an employee class.
     * @throws SQLException if something went wrong.
     */
    public void create(Employee emp) throws SQLException;

    /**
     * Function to create a new entry of
     * project in SQL database.
     * 
     * @param proj – instance of a project class.
     * @throws SQLException if something went wrong.
     */
    public void create(Project proj) throws SQLException;

    /**
     * Function gives the list of all
     * existing employees in SQL database.
     * 
     * @return List with employees.
     * @throws SQLException if something went wrong.
     */
    ArrayList<Employee> readEmployees() throws SQLException;

    /**
     * Function gives the list of
     * all projects that exist in SQL
     * database
     * 
     * @return List with projects.
     * @throws SQLException if something went wrong.
     */
    ArrayList<Project> readProject() throws SQLException;
}
