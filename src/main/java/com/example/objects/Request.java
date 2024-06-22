package com.example.objects;

import java.util.Date;

public class Request {

	private int requestId;
	private Date date;
	private String requesterId; 
	private String requesterName;
	private String employeeId;
	private String empName;
	private String memo;
	private Double dailyLimit;
	private Double monthlyLimit;
	private String pcardAdminId;
	private String approvalStatus;
		
	public Request(int requestId, Date date, String requesterId,  String employeeId, 
			String empName, String requesterName, String memo, Double dailyLimit, Double monthlyLimit, String pcardAdminId,
			String approvalStatus) {
		super();
		this.requestId = requestId;
		this.date = date;
		this.requesterId = requesterId;
		this.requesterName = requesterName;
		this.employeeId = employeeId;
		this.empName = empName;
		this.memo = memo;
		this.dailyLimit = dailyLimit;
		this.monthlyLimit = monthlyLimit;
		this.pcardAdminId = pcardAdminId;
		this.approvalStatus = approvalStatus;
	}

	public String getRequesterId() {
		return requesterId;
	}

	public void setRequesterId(String requesterId) {
		this.requesterId = requesterId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPcardAdminId() {
		return pcardAdminId;
	}

	public void setPcardAdminId(String pcardAdminId) {
		this.pcardAdminId = pcardAdminId;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}



	public Double getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(Double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public Double getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(Double monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getRequesterName() {
		return requesterName;
	}

	public void setRequesterName(String requesterName) {
		this.requesterName = requesterName;
	}
	

	
	
}
