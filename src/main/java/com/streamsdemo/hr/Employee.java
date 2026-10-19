package com.streamsdemo.hr;

/** An employee record for HR analytics. */
public class Employee {
    private final String name;
    private final String dept;
    private final double salary;

    public Employee(String name, String dept, double salary) {
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }

    public String getName() { return name; }
    public String getDept() { return dept; }
    public double getSalary() { return salary; }

    @Override
    public String toString() {
        return name + " (" + dept + ") $" + String.format("%,.0f", salary);
    }
}
