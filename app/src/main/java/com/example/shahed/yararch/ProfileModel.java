package com.example.shahed.yararch;

import java.util.Date;

public class ProfileModel {
    private int profileId;
    private String name;
    private String fatherName;
    private String phoneNo;
    private String address;
    private String userDetails;
    private String registerDate;
    private String imagePath;

    public ProfileModel() {
    }

    public ProfileModel(int profileId, String name, String fatherName, String phoneNo, String address, String userDetails, String imagePath) {
        this.profileId = profileId;
        this.name = name;
        this.fatherName = fatherName;
        this.phoneNo = phoneNo;
        this.address = address;
        this.userDetails = userDetails;
        this.imagePath = imagePath;
    }

    public ProfileModel(String name, String fatherName, String phoneNo, String address, String userDetails, String registerDate, String imagePath) {
        this.name = name;
        this.fatherName = fatherName;
        this.phoneNo = phoneNo;
        this.address = address;
        this.userDetails = userDetails;
        this.registerDate = registerDate;
        this.imagePath = imagePath;
    }

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}
