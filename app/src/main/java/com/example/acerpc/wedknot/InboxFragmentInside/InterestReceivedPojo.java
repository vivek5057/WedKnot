package com.example.acerpc.wedknot.InboxFragmentInside;

public class InterestReceivedPojo {
    public String friendName;
    public String friendCity;
    public String friendCountry;
    public String friendPic;
    public String friendEmail;


    public InterestReceivedPojo() {
    }

    public InterestReceivedPojo(String friendName, String friendCity, String friendCountry, String friendPic, String friendEmail) {
        this.friendName = friendName;
        this.friendCity = friendCity;
        this.friendCountry = friendCountry;
        this.friendPic = friendPic;
        this.friendEmail = friendEmail;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendCity() {
        return friendCity;
    }

    public void setFriendCity(String friendCity) {
        this.friendCity = friendCity;
    }

    public String getFriendCountry() {
        return friendCountry;
    }

    public void setFriendCountry(String friendCountry) {
        this.friendCountry = friendCountry;
    }

    public String getFriendPic() {
        return friendPic;
    }

    public void setFriendPic(String friendPic) {
        this.friendPic = friendPic;
    }

    public String getFriendEmail() {
        return friendEmail;
    }

    public void setFriendEmail(String friendEmail) {
        this.friendEmail = friendEmail;
    }

}
