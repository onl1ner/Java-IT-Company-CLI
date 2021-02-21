package services.network;

import java.sql.*;
import java.util.ArrayList;

import models.*;

/**
 * A service that provides a basic actions
 * to store the data about in SQL database.
 */
public class NetworkService implements INetworkService {

    public static final INetworkService shared = new NetworkService();
    
    private Connection conn;
    private Statement statement;

    public void create(Employee emp) throws SQLException {
        // As worker_id field is set to be of type SERIAL
        // we do not have to initialize the value of it.
        final String query = "INSERT INTO employee (worker_id, name, hourly_salary, position) VALUES (DEFAULT, ?, ?, ?) RETURNING worker_id";
        final PreparedStatement preparedStatement = this.conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1, emp.getName());
        preparedStatement.setInt(2, emp.getHourlySalary());
        preparedStatement.setString(3, emp.getPosition());

        preparedStatement.executeUpdate();

        // Because query is returning the generated 
        // worker identificator we have to get it and
        // set it to given employee.
        ResultSet rs = preparedStatement.getGeneratedKeys();

        while (rs.next()) {
            emp.setWorkerID(rs.getInt(1));
        }
    }

    public void create(Project proj) throws SQLException {
        // As id field is set to be of type SERIAL
        // we do not have to initialize the value of it.
        final String queryForProj = "INSERT INTO project (id, name, total_hour, total_cost) VALUES (DEFAULT, ?, ?, ?) RETURNING id";
        final String queryForBridge = "INSERT INTO emp_to_proj (worker_id, project_id) VALUES (?, ?)";

        final PreparedStatement preparedStatementProj = this.conn.prepareStatement(queryForProj, Statement.RETURN_GENERATED_KEYS);
        final PreparedStatement preparedStatementBridge = this.conn.prepareStatement(queryForBridge);

        preparedStatementProj.setString(1, proj.getName());
        preparedStatementProj.setInt(2, proj.getTotalHour());
        preparedStatementProj.setInt(3, proj.getTotalCost());

        preparedStatementProj.executeUpdate();

        // Because query is returning the generated 
        // worker identificator we have to get it and
        // set it to given project.
        ResultSet projRS = preparedStatementProj.getGeneratedKeys();

        while (projRS.next()) {
            proj.setID(projRS.getInt(1));
        }

        // Getting each employee that involved in
        // given project and adding them to 
        // bridge table emp_to_proj.
        for (Employee emp : proj.getEmployees()) {
            preparedStatementBridge.setInt(1, emp.getWorkerID());
            preparedStatementBridge.setInt(2, proj.getID());
            preparedStatementBridge.executeUpdate();
        }
    }

    public ArrayList<Employee> readEmployees() throws SQLException {
        final String query = "SELECT * FROM employee";
        final ResultSet rs = this.statement.executeQuery(query);

        ArrayList<Employee> employees = new ArrayList<Employee>();

        while (rs.next()) {
            final int workerID = rs.getInt("worker_id");
            final String name = rs.getString("name");
            final int hourlySalary = rs.getInt("hourly_salary");
            final String position = rs.getString("position");

            // As Employee class is abstract we cannot
            // initialize it. That is why we have to
            // determine on which position the employee
            // and initialize it with proper class.
            if (position.equals("Developer")) {
                Developer dev = new Developer(workerID, name, hourlySalary);
                employees.add(dev);
            } else if (position.equals("Designer")) {
                Designer des = new Designer(workerID, name, hourlySalary);
                employees.add(des);
            }
        }
        
        return employees;
    }
    
    public ArrayList<Project> readProject() throws SQLException {
        final String query = "SELECT * FROM project";
        final ResultSet rs = this.statement.executeQuery(query);

        ArrayList<Project> projects = new ArrayList<Project>();

        while(rs.next()) {
            final int id = rs.getInt("id");
            final String name = rs.getString("name");
            final int totalHour = rs.getInt("total_hour");
            final int totalCost = rs.getInt("total_cost");

            Project proj = new Project(id, name, totalHour, totalCost);
            projects.add(proj);
        }

        return projects;
    }

    private NetworkService() {
        try {
            Class.forName("org.postgresql.Driver");

            // Instead of DB name, user and password write
            // credentials of an existing database with the
            // tables: employee, project, emp_to_proj.
            this.conn = DriverManager.getConnection("CHANGE_ME", "CHANGE_ME", "CHANGE_ME");
            this.statement = this.conn.createStatement();
        } catch (Exception error) {
            System.out.println(error);
        }
    }
}