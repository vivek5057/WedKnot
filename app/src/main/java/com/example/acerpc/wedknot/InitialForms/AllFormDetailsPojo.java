package com.example.acerpc.wedknot.InitialForms;

public class AllFormDetailsPojo {

    //PersonalDetails
    public String personalDetailsGender;
    public String personalDetailsDob;
    public String personalDetailsAge;
    public String personalDetailsHeight;
    public String personalDetailsCountry;
    public String personalDetailsState;
    public String personalDetailsCity;

    //SocialDetails
    public String socialDetailsMartialStatus;
    public String socialDetailsMotherTongue;
    public String socialDetailsReligion;

    //CareerDetails
    public String careerDetailsHighestEducation;
    public String careerDetailsCollege;
    public String careerDetailsWorkArea;
    public String careerDetailsAnnualIncome;

    //LoginDetails
    public String loginDetailsFullName;
    public String loginDetailsEmailId;
    public String loginDetailsPhoneNo;


    public AllFormDetailsPojo() {
    }

    public AllFormDetailsPojo(String personalDetailsGender, String personalDetailsDob, String personalDetailsAge, String personalDetailsHeight, String personalDetailsCountry, String personalDetailsState, String personalDetailsCity, String socialDetailsMartialStatus, String socialDetailsMotherTongue, String socialDetailsReligion, String careerDetailsHighestEducation, String careerDetailsCollege, String careerDetailsWorkArea, String careerDetailsAnnualIncome, String loginDetailsFullName, String loginDetailsEmailId, String loginDetailsPhoneNo) {
        this.personalDetailsGender = personalDetailsGender;
        this.personalDetailsDob = personalDetailsDob;
        this.personalDetailsAge = personalDetailsAge;
        this.personalDetailsHeight = personalDetailsHeight;
        this.personalDetailsCountry = personalDetailsCountry;
        this.personalDetailsState = personalDetailsState;
        this.personalDetailsCity = personalDetailsCity;
        this.socialDetailsMartialStatus = socialDetailsMartialStatus;
        this.socialDetailsMotherTongue = socialDetailsMotherTongue;
        this.socialDetailsReligion = socialDetailsReligion;
        this.careerDetailsHighestEducation = careerDetailsHighestEducation;
        this.careerDetailsCollege = careerDetailsCollege;
        this.careerDetailsWorkArea = careerDetailsWorkArea;
        this.careerDetailsAnnualIncome = careerDetailsAnnualIncome;
        this.loginDetailsFullName = loginDetailsFullName;
        this.loginDetailsEmailId = loginDetailsEmailId;
        this.loginDetailsPhoneNo = loginDetailsPhoneNo;
    }
}
