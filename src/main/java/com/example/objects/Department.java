package com.example.objects;

public class Department {
    String name;
    String departmentId;
    String universityId;
    Policy policy;

    public Department(String departmentId, String universityId, String name){
        this.name = name;
        this.departmentId = departmentId;
        this.universityId = universityId;
    }

    public String getName() {
        return name;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public Policy getPolicy() {
        return policy;
    }

    public String getUniversityId() {
        return universityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    @Override
    public String toString() {
        return departmentId;
    }
}
