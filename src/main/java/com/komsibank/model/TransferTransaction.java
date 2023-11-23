package com.komsibank.model;

public class TransferTransaction {
    String senderAccNumber;
    String recipientAccNumber;
    double transferAmount;

    public String getSenderAccNumber() {
        return senderAccNumber;
    }

    public void setSenderAccNumber(String senderAccNumber) {
        this.senderAccNumber = senderAccNumber;
    }

    public String getRecipientAccNumber() {
        return recipientAccNumber;
    }

    public void setRecipientAccNumber(String recipientAccNumber) {
        this.recipientAccNumber = recipientAccNumber;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }
}
