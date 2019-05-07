package com.example.acerpc.wedknot.MyWedknotInside;

public class LifeStylePojo {

    public String aboutMe;
    public String food;
    public String drink;
    public String smoke;
    public String skinColor;
    public String bodyType;

    public LifeStylePojo() {
    }

    public LifeStylePojo(String aboutMe, String food, String drink, String smoke, String skinColor, String bodyType) {
        this.aboutMe = aboutMe;
        this.food = food;
        this.drink = drink;
        this.smoke = smoke;
        this.skinColor = skinColor;
        this.bodyType = bodyType;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getSkinColor() {
        return skinColor;
    }

    public void setSkinColor(String skinColor) {
        this.skinColor = skinColor;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }
}
