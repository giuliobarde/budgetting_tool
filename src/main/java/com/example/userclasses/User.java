package com.example.userclasses;

public class User implements SessionUser{
    private String fName;
    private String lName;
    private String XNumber;
    private int departmentId;
    private String departmentName;
    private String role;


    public User(String fName, String lName, String XNumber, int departmentId,String departmentName, String role){
        this.fName = fName;
        this.lName = lName;
        this.XNumber = XNumber;
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.role = role;
    }

    public User() {
		this.fName = null;
		this.lName = null;
		this.XNumber = null;
		this.departmentId = 0;
		this.role = null;
	}

	public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getXNumber() {
        return XNumber;
    }

    public void setXNumber(String XNumber) {
        this.XNumber = XNumber;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}




}
