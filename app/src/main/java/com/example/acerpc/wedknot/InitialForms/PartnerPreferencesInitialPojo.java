package com.example.acerpc.wedknot.InitialForms;


public class PartnerPreferencesInitialPojo {

    public String minAge;
    public String maxAge;
    public String minHeight;
    public String maxHeight;
    public String religion;
    public String motherTongue;
    public String country;

    public String income;
    public String education;
    public String maritalStatus;

    public PartnerPreferencesInitialPojo() {
    }

    public PartnerPreferencesInitialPojo(String minAge, String maxAge, String minHeight, String maxHeight, String religion, String motherTongue, String country, String income, String education, String maritalStatus) {
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
        this.religion = religion;
        this.motherTongue = motherTongue;
        this.country = country;
        this.income = income;
        this.education = education;
        this.maritalStatus = maritalStatus;
    }

    public String getMinAge() {
        return minAge;
    }

    public void setMinAge(String minAge) {
        this.minAge = minAge;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(String minHeight) {
        this.minHeight = minHeight;
    }

    public String getMaxHeight() {
        return maxHeight;
    }

    public void setMaxHeight(String maxHeight) {
        this.maxHeight = maxHeight;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getMotherTongue() {
        return motherTongue;
    }

    public void setMotherTongue(String motherTongue) {
        this.motherTongue = motherTongue;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
}