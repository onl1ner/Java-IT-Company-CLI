package models;

public class Developer extends Employee {
    
    @Override
    public String getPosition() {
        return "Developer";
    }

    public Developer(String name) {
        super(name, 10);
    }

    public Developer(int workerID, String name, int hourlySalary) {
        super(workerID, name, hourlySalary);
    }
}
