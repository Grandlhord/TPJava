package com.example.tpjava;

public class TransactionModel {
    private int transactionIcons;
    private String transactionTitle;
    private String transactionMessage;
    private String transactionTimeStamp;
    private String transactionAmount;
    private String transactionStatus;

    public TransactionModel(int transactionIcons, String transactionTitle, String transactionMessage, String transactionTimeStamp, String transactionAmount, String transactionStatus) {
        this.transactionIcons = transactionIcons;
        this.transactionTitle = transactionTitle;
        this.transactionMessage = transactionMessage;
        this.transactionTimeStamp = transactionTimeStamp;
        this.transactionAmount = transactionAmount;
        this.transactionStatus = transactionStatus;
    }

    public int getTransactionIcons() {
        return transactionIcons;
    }

    public void setTransactionIcons(int transactionIcons) {
        this.transactionIcons = transactionIcons;
    }

    public String getTransactionTitle() {
        return transactionTitle;
    }

    public void setTransactionTitle(String transactionTitle) {
        this.transactionTitle = transactionTitle;
    }

    public String getTransactionMessage() {
        return transactionMessage;
    }

    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage;
    }

    public String getTransactionTimeStamp() {
        return transactionTimeStamp;
    }

    public void setTransactionTimeStamp(String transactionTimeStamp) {
        this.transactionTimeStamp = transactionTimeStamp;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "transactionIcons=" + transactionIcons +
                ", transactionTitle='" + transactionTitle + '\'' +
                ", transactionMessage='" + transactionMessage + '\'' +
                ", transactionTimeStamp='" + transactionTimeStamp + '\'' +
                ", transactionAmount='" + transactionAmount + '\'' +
                ", transactionStatus='" + transactionStatus + '\'' +
                '}';
    }
}
