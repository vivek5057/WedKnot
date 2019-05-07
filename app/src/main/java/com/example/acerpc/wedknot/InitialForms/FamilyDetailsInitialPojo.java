package com.example.acerpc.wedknot.InitialForms;

public class FamilyDetailsInitialPojo {

    //familyDetails
    public String familyfatherOccupation;
    public String familymotherOccupation;
    public String familybrothers;
    public String familysisters;
    public String familyIncome;
    public String familyStatus;
    public String familyTypeSpinner;
    public String familylivingWithParents;

    public FamilyDetailsInitialPojo() {
    }

    public FamilyDetailsInitialPojo(String familyfatherOccupation, String familymotherOccupation, String familybrothers, String familysisters, String familyIncome, String familyStatus, String familyTypeSpinner, String familylivingWithParents) {
        this.familyfatherOccupation = familyfatherOccupation;
        this.familymotherOccupation = familymotherOccupation;
        this.familybrothers = familybrothers;
        this.familysisters = familysisters;
        this.familyIncome = familyIncome;
        this.familyStatus = familyStatus;
        this.familyTypeSpinner = familyTypeSpinner;
        this.familylivingWithParents = familylivingWithParents;
    }

    public String getFamilyfatherOccupation() {
        return familyfatherOccupation;
    }

    public void setFamilyfatherOccupation(String familyfatherOccupation) {
        this.familyfatherOccupation = familyfatherOccupation;
    }

    public String getFamilymotherOccupation() {
        return familymotherOccupation;
    }

    public void setFamilymotherOccupation(String familymotherOccupation) {
        this.familymotherOccupation = familymotherOccupation;
    }

    public String getFamilybrothers() {
        return familybrothers;
    }

    public void setFamilybrothers(String familybrothers) {
        this.familybrothers = familybrothers;
    }

    public String getFamilysisters() {
        return familysisters;
    }

    public void setFamilysisters(String familysisters) {
        this.familysisters = familysisters;
    }

    public String getFamilyIncome() {
        return familyIncome;
    }

    public void setFamilyIncome(String familyIncome) {
        this.familyIncome = familyIncome;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public String getFamilyTypeSpinner() {
        return familyTypeSpinner;
    }

    public void setFamilyTypeSpinner(String familyTypeSpinner) {
        this.familyTypeSpinner = familyTypeSpinner;
    }

    public String getFamilylivingWithParents() {
        return familylivingWithParents;
    }

    public void setFamilylivingWithParents(String familylivingWithParents) {
        this.familylivingWithParents = familylivingWithParents;
    }
}
