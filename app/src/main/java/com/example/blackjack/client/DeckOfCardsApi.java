package com.example.blackjack.client;

import com.example.blackjack.pojo.DeckResponse;
import com.example.blackjack.pojo.DrawCardResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface DeckOfCardsApi {
    @GET("api/deck/new/shuffle/")
    Call<DeckResponse> shuffleDeck(@Query("deck_count") int deckCount);

    @GET("api/deck/{deck_id}/draw/")
    Call<DrawCardResponse> drawCard(@Path("deck_id") String deckId, @Query("count") int count);
}