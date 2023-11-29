package com.example.blackjack.pojo;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("svg")
    private String svg;

    @SerializedName("png")
    private String png;

    // Getters and Setters

    public String getSvg() {
        return svg;
    }

    public void setSvg(String svg) {
        this.svg = svg;
    }

    public String getPng() {
        return png;
    }

    public void setPng(String png) {
        this.png = png;
    }
}
