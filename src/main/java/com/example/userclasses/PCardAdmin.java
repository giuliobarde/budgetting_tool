package com.example.userclasses;

public class PCardAdmin extends User{

    public PCardAdmin(String fName, String lName, String XNumber, int departmentId, String departmentName){
        super(fName, lName, XNumber, departmentId, departmentName, "PCardAdmin");
    }

    public String toString() {
        return this.getfName() + " " + this.getlName();
    }
}
