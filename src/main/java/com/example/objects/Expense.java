package com.example.objects;

import java.util.Date;

public class Expense {
	private int expenseId;
	private String employeeId;
	private String employeeName;
	private String approvalStatus;
	private double amount;
	private String expenseType;
	private Date date;
	private String memo;
	private String cardType;
	private String cardNumber;
	private String approverId;
	
	public Expense(int expenseId, String employeeId, String employeeName, String approvalStatus, double amount, String expenseType, Date date, String memo,
			String cardType, String cardNumber, String approverId) {
		super();
		this.expenseId = expenseId;
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.approvalStatus = approvalStatus;
		this.amount = amount;
		this.expenseType = expenseType;
		this.date = date;
		this.memo = memo;
		this.cardType = cardType;
		this.cardNumber = cardNumber;
		this.approverId = approverId;
	}

	public int getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approvalStatus;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}


}
