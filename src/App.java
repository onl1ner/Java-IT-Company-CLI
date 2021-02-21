import java.util.*;

import models.*;
import services.network.*;

public class App {

    // Upper bound string for the CLI.
    private static final String upperBound = "–––––––––––––––––––––––";

    // Lower bound string for the CLI.
    private static final String lowerBound = "–––––––––––––––––––––––";

    private static ArrayList<Employee> employees = new ArrayList<Employee>();
    private static ArrayList<Project> projects = new ArrayList<Project>();

    /**
     * Function find an employee by
     * his worker identificator.
     * 
     * @param workerID – unique worker identificator.
     * @return Employee with given worker identificator. 
     * If there is no employee with such id function
     * returns null.
     */
    private static Employee getEmployee(int workerID) {
        for (Employee emp : employees) {
            if (emp.getWorkerID() == workerID) {
                return emp;
            }
        }

        return null;
    }

    /**
     * Function prints all employees with
     * format: {worker_id}. {name}: {position}
     */
    private static void printEmployees() {
        System.out.println(upperBound);
        
        for (Employee emp : employees) {
            System.out.println(emp.getWorkerID() + ". " + emp.getName() + ": " + emp.getPosition());
        }

        System.out.println(lowerBound);
    }

    /**
     * Function prints all projects with
     * format: {id}. {name}: ${total_cost}
     */
    private static void printProjects() {
        System.out.println(upperBound);
        
        for (Project proj : projects) {
            System.out.println(proj.getID() + ". " + proj.getName() + ": $" + proj.getTotalCost());
        }

        System.out.println(lowerBound);
    }

    /**
     * Function creates flow with suggestion to go to main menu.
     */
    private static void goToMainMenu(Scanner sc) {
        String choice = "";

        while (!choice.equals("Y") && !choice.equals("N")) {
            System.out.println("Go to main menu? (Y/N)");
            choice = sc.next();
        }

        if (choice.equals("Y")) showMainMenu(sc);
    }

    /**
     * Function creates flow to add a new employee.
     */
    private static void addEmployee(Scanner sc) {
        System.out.println(upperBound);

        System.out.println("New employee's name: ");
        String name = sc.next();

        int choice = -1;

        // Loop untill the right choice were made.
        while (choice != 1 && choice != 2) {
            System.out.println("New employee's position: ");
            System.out.println("1. Developer");
            System.out.println("2. Designer");

            choice = sc.nextInt();
        }

        System.out.println(lowerBound);
        
        switch (choice) {
            case 1: 
                Developer newDeveloper = new Developer(name);
                employees.add(newDeveloper);
                System.out.println("Successfully added new developer: " + newDeveloper.getName());
                break;
            case 2: 
                Designer newDesigner = new Designer(name);
                employees.add(newDesigner);
                System.out.println("Successfully added new designer: " + newDesigner.getName());
                break;
            default: break;
        }

        showMainMenu(sc);
    }

    private static void showEmployees(Scanner sc) {
        // If employees are empty then show
        // suggestion to add a new employee.
        if (employees.isEmpty()) {
            String choice = "";
            
            while (!choice.equals("Y") && !choice.equals("N")) {
                System.out.println("No employees. Would you like to add a new one? (Y/N)");
                choice = sc.next();
            }

            if (choice.equals("Y")) {
                addEmployee(sc);
            } else {
                showMainMenu(sc);
            }

            return;
        }

        printEmployees();
        goToMainMenu(sc);
    }

    private static void addProject(Scanner sc) {
        // If there is no employees we cannot
        // add a new project, that is why we
        // are showing the message and returning
        // to the main menu.
        if (employees.isEmpty()) {
            System.out.println("Add employees first");
            showMainMenu(sc);
            return;
        }

        System.out.println(upperBound);

        System.out.println("New project's name: ");
        String name = sc.next();

        System.out.println("New project's needed hours: ");
        int totalHour = sc.nextInt();

        System.out.println(lowerBound);

        System.out.println("Pick the employees for project:");

        // Employees are not empty at that
        // moment that is why we can call this
        // function so we will not confuse 
        // the flow.
        printEmployees();

        ArrayList<Employee> empForProj = new ArrayList<Employee>();

        String choice = "";
        
        // Looping untill the user will end
        // adding a new employees for a project.
        while (!choice.equals("N")) {
            int workerID = -1;

            // We have to reset choice value
            // to avoid infinite loop.
            choice = "";

            // Looping untill the user writes
            // identificator of an existing employee.
            while (getEmployee(workerID) == null) {
                System.out.println("Enter worker ID: ");
                workerID = sc.nextInt();
            }

            Employee emp = getEmployee(workerID);
            empForProj.add(emp);
    
            while (!choice.equals("Y") && !choice.equals("N")) {
                System.out.println("Employee added. Continue adding? (Y/N)");
                choice = sc.next();
            }
        }
        
        Project proj = new Project(name, totalHour, employees);
        projects.add(proj);

        System.out.println("Successfuly created project: " + proj.getName());
        
        goToMainMenu(sc);
    }

    private static void showProjects(Scanner sc) {
        // If employees are empty then show
        // suggestion to add a new employee.
        if (projects.isEmpty()) {
            String choice = "";
            
            while (!choice.equals("Y") && !choice.equals("N")) {
                System.out.println("No projects. Would you like to add a new one? (Y/N)");
                choice = sc.next();
            }

            if (choice.equals("Y")) {
                addProject(sc);
            } else {
                showMainMenu(sc);
            }

            return;
        }

        printProjects();
        goToMainMenu(sc);
    }

    private static void showMainMenu(Scanner sc) {
        int choice = -1;

        // Array with the possible actions 
        // that can be done.
        ArrayList<Integer> actions = new ArrayList<Integer>() {{
            add(1);
            add(2);
            add(3);
            add(4);
        }};
        
        // Looping untill user writes existing
        // action in the posibble actions array.
        while (!actions.contains(choice)) {
            System.out.println(upperBound);
            System.out.println("Choose the action:");
            System.out.println("1. View employees");
            System.out.println("2. Add employee");
            System.out.println("3. View projects");
            System.out.println("4. Add project");
            System.out.println(lowerBound);

            choice = sc.nextInt();
        }

        switch (choice) {
            case 1: showEmployees(sc); break;
            case 2: addEmployee(sc); break;
            case 3: showProjects(sc); break;
            case 4: addProject(sc); break;
            default: break;
        }
    }

    public static void main(String[] args) throws Exception {
        // Getting the employees from SQL database.
        employees = NetworkService.shared.readEmployees();

        // Getting the projects from SQL database.
        projects = NetworkService.shared.readProject();
        
        // Initializing a scanner to perform input actions.
        Scanner sc = new Scanner(System.in);
        showMainMenu(sc);
    }
}