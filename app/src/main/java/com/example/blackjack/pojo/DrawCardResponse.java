package com.example.blackjack.pojo;

import com.example.blackjack.pojo.Card;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DrawCardResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("deck_id")
    private String deckId;

    @SerializedName("cards")
    private List<Card> cards;

    @SerializedName("remaining")
    private int remaining;

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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }
}
