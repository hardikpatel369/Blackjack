package com.example.blackjack.pojo;

import com.google.gson.annotations.SerializedName;

public class Card {

    @SerializedName("code")
    private String code;

    @SerializedName("image")
    private String image;

    @SerializedName("images")
    private Images images;

    @SerializedName("value")
    private String value;

    @SerializedName("suit")
    private String suit;

    // Getters and Setters

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Images getImages() {
        return images;
    }

    public void setImages(Images images) {
        this.images = images;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }
}