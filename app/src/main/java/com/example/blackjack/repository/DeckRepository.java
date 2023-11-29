package com.example.blackjack.repository;

import com.example.blackjack.client.DeckOfCardsApi;
import com.example.blackjack.pojo.DeckResponse;
import com.example.blackjack.pojo.DrawCardResponse;

import retrofit2.Call;

// DeckRepository.java
public class DeckRepository {
    private DeckOfCardsApi apiService;

    public DeckRepository(DeckOfCardsApi apiService) {
        this.apiService = apiService;
    }

    public Call<DeckResponse> shuffleDeck(int deckCount) {
        return apiService.shuffleDeck(deckCount);
    }

    public Call<DrawCardResponse> drawCard(String deckId, int count) {
        return apiService.drawCard(deckId, count);
    }
}