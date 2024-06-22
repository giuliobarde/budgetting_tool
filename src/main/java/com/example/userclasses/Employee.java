package com.example.userclasses;

public class Employee extends User{

    public Employee(String fName, String lName, String XNumber, int departmentId, String departmentName){
        super(fName, lName, XNumber, departmentId, departmentName, "Employee");
    }
}
