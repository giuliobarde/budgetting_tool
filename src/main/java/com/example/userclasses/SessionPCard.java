package com.example.userclasses;

public class SessionPCard {

	private String cardNumber;
	private double currentDailyAmount;
	private double currentMonthlyAmount;
	private double dailyLimit;
	private double monthlyLimit;

	public double getCurrentDailyAmount() {
		return currentDailyAmount;
	}

	public void setCurrentDailyAmount(double currentDailyAmount) {
		this.currentDailyAmount = currentDailyAmount;
	}

	public double getCurrentMonthlyAmount() {
		return currentMonthlyAmount;
	}

	public void setCurrentMonthlyAmount(double currentMonthlyAmount) {
		this.currentMonthlyAmount = currentMonthlyAmount;
	}

	public double getDailyLimit() {
		return dailyLimit;
	}

	public void setDailyLimit(double dailyLimit) {
		this.dailyLimit = dailyLimit;
	}

	public double getMonthlyLimit() {
		return monthlyLimit;
	}

	public void setMonthlyLimit(double monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}
	// called when logging in
	public SessionPCard(String cardNumber, double dailyLimit, double monthlyLimit) {
		super();
		this.cardNumber = cardNumber;
		this.dailyLimit = dailyLimit;
		this.monthlyLimit = monthlyLimit;
	}

	public SessionPCard() {
		this.cardNumber = "0";
		this.currentDailyAmount = 0;
		this.currentMonthlyAmount = 0;
		this.dailyLimit = 0;
		this.monthlyLimit = 0;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

}
