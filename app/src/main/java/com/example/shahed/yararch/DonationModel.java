package com.example.shahed.yararch;

public class DonationModel {
    private int donationId;
    private int donationProfileId;
    private double donationAmount;
    private double totalDonationAmount;
    private String userName;
    private String donationCause;
    private String donationDate;

    public DonationModel() {
    }

    public DonationModel(int donationProfileId, double donationAmount, String userName, String donationCause) {
        this.donationProfileId = donationProfileId;
        this.donationAmount = donationAmount;
        this.userName = userName;
        this.donationCause = donationCause;
    }

    public DonationModel(int donationId, double donationAmount, String donationCause) {
        this.donationId = donationId;
        this.donationAmount = donationAmount;
        this.donationCause = donationCause;
    }

    public DonationModel(int donationId, int donationProfileId, double donationAmount, double totalDonationAmount, String userName, String donationCause, String donationDate) {
        this.donationId = donationId;
        this.donationProfileId = donationProfileId;
        this.donationAmount = donationAmount;
        this.totalDonationAmount = totalDonationAmount;
        this.userName = userName;
        this.donationCause = donationCause;
        this.donationDate = donationDate;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getDonationProfileId() {
        return donationProfileId;
    }

    public void setDonationProfileId(int donationProfileId) {
        this.donationProfileId = donationProfileId;
    }

    public double getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(double donationAmount) {
        this.donationAmount = donationAmount;
    }

    public double getTotalDonationAmount() {
        return totalDonationAmount;
    }

    public void setTotalDonationAmount(double totalDonationAmount) {
        this.totalDonationAmount = totalDonationAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDonationCause() {
        return donationCause;
    }

    public void setDonationCause(String donationCause) {
        this.donationCause = donationCause;
    }

    public String getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(String donationDate) {
        this.donationDate = donationDate;
    }

}
