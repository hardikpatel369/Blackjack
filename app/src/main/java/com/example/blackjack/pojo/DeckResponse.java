package com.example.blackjack.pojo;

import com.google.gson.annotations.SerializedName;

public class DeckResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("deck_id")
    private String deckId;

    @SerializedName("remaining")
    private int remaining;

    @SerializedName("shuffled")
    private boolean shuffled;

    // Getters and Setters

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDeckId() {
        return deckId;
    }

    public void setDeckId(String deckId) {
        this.deckId = deckId;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public boolean isShuffled() {
        return shuffled;
    }

    public void setShuffled(boolean shuffled) {
        this.shuffled = shuffled;
    }
}

