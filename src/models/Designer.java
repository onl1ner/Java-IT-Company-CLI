package models;

public class Designer extends Employee {
    
    @Override
    public String getPosition() {
        return "Designer";
    }

    public Designer(String name) {
        super(name, 8);
    }

    public Designer(int workerID, String name, int hourlySalary) {
        super(workerID, name, hourlySalary);
    }
}
