package com.example.shahed.yararch;

public class DonationModel {
    private int donationId;
    private int profileUserId;
    private String donationAmount;
    private String donationCause;

    public DonationModel() {
    }

    public DonationModel(int donationId, int profileUserId, String donationAmount, String donationCause) {
        this.donationId = donationId;
        this.profileUserId = profileUserId;
        this.donationAmount = donationAmount;
        this.donationCause = donationCause;
    }

    public int getDonationId() {
        return donationId;
    }

    public void setDonationId(int donationId) {
        this.donationId = donationId;
    }

    public int getProfileUserId() {
        return profileUserId;
    }

    public void setProfileUserId(int profileUserId) {
        this.profileUserId = profileUserId;
    }

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getDonationCause() {
        return donationCause;
    }

    public void setDonationCause(String donationCause) {
        this.donationCause = donationCause;
    }
}
