package com.example.blackjack.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.blackjack.client.DeckOfCardsApi;
import com.example.blackjack.repository.DeckRepository;
import com.example.blackjack.client.RetrofitClient;
import com.example.blackjack.pojo.DeckResponse;
import com.example.blackjack.pojo.DrawCardResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeckViewModel extends ViewModel {
    private DeckRepository repository;

    public DeckViewModel() {
        this.repository = new DeckRepository(RetrofitClient.getRetrofitInstance().create(DeckOfCardsApi.class));
    }

    public LiveData<DeckResponse> shuffleDeck(int deckCount) {
        MutableLiveData<DeckResponse> liveData = new MutableLiveData<>();
        repository.shuffleDeck(deckCount).enqueue(new Callback<DeckResponse>() {
            @Override
            public void onResponse(Call<DeckResponse> call, Response<DeckResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<DeckResponse> call, Throwable t) {
                // Handle failure
            }
        });
        return liveData;
    }

    public LiveData<DrawCardResponse> drawCard(String deckId, int count) {
        MutableLiveData<DrawCardResponse> liveData = new MutableLiveData<>();
        repository.drawCard(deckId, count).enqueue(new Callback<DrawCardResponse>() {
            @Override
            public void onResponse(Call<DrawCardResponse> call, Response<DrawCardResponse> response) {
                if (response.isSuccessful()) {
                    liveData.setValue(response.body());
                } else {
                    // Handle error
                }
            }

            @Override
            public void onFailure(Call<DrawCardResponse> call, Throwable t) {
                // Handle failure
            }
        });
        return liveData;
    }
}