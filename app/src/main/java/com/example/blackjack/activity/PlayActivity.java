package com.example.blackjack.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blackjack.adapter.CardAdapter;
import com.example.blackjack.pojo.Card;
import com.example.blackjack.viewmodel.DeckViewModel;
import com.example.blackjack.R;

import java.util.ArrayList;
import java.util.List;

public class PlayActivity extends AppCompatActivity {

    private Button dealButton, clearButton;
    private RelativeLayout llp1, llp2, llp3, lld;
    private LinearLayout llButtons;
    private DeckViewModel playViewModel;
    private CardAdapter dealerAdapter;
    private CardAdapter player1Adapter;
    private CardAdapter player2Adapter;
    private CardAdapter player3Adapter;
    String deckId;
    int position = 0;
    int p1Total = 0;
    int p2Total = 0;
    int p3Total = 0;
    int dTotal = 0;
    TextView tvP1, tvP2, tvP3, tvd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        RecyclerView dealerRecyclerView = findViewById(R.id.delerRecyclerView);
        RecyclerView player1RecyclerView = findViewById(R.id.player1RecyclerView);
        RecyclerView player2RecyclerView = findViewById(R.id.player2RecyclerView);
        RecyclerView player3RecyclerView = findViewById(R.id.player3RecyclerView);
        clearButton = findViewById(R.id.clearButton);
        dealButton = findViewById(R.id.dealButton);
        Button hitButton = findViewById(R.id.hitButton);
        Button standButton = findViewById(R.id.standButton);
        Button splitButton = findViewById(R.id.splitButton);
        Button doubleButton = findViewById(R.id.doubleButton);
        llButtons = findViewById(R.id.llButtons);
        llp1 = findViewById(R.id.llp1);
        llp2 = findViewById(R.id.llp2);
        llp3 = findViewById(R.id.llp3);
        lld = findViewById(R.id.lld);
        tvP1 = findViewById(R.id.tvP1);
        tvP2 = findViewById(R.id.tvP2);
        tvP3 = findViewById(R.id.tvP3);
        tvd = findViewById(R.id.tvd);

        // Initialize ViewModel
        playViewModel = new ViewModelProvider(this).get(DeckViewModel.class);

        // Initialize RecyclerView Adapters
        dealerAdapter = new CardAdapter(new ArrayList<>());
        player1Adapter = new CardAdapter(new ArrayList<>());
        player2Adapter = new CardAdapter(new ArrayList<>());
        player3Adapter = new CardAdapter(new ArrayList<>());

        // Set Layout Managers for RecyclerViews
        dealerRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        player1RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        player2RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        player3RecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Set Adapters for RecyclerViews
        dealerRecyclerView.setAdapter(dealerAdapter);
        player1RecyclerView.setAdapter(player1Adapter);
        player2RecyclerView.setAdapter(player2Adapter);
        player3RecyclerView.setAdapter(player3Adapter);

        playViewModel.shuffleDeck(6).observe(this, deckResponse -> {
            deckId = deckResponse.getDeckId();

        });

        clearButton.setOnClickListener(v -> {
            lld.setBackgroundResource(0);
            dealButton.setVisibility(View.VISIBLE);
            clearButton.setVisibility(View.GONE);
            dealerAdapter.clear();
            player1Adapter.clear();
            player2Adapter.clear();
            player3Adapter.clear();
            position = 0;
            p1Total = 0;
            p2Total = 0;
            p3Total = 0;
            dTotal = 0;
        });

        // Button click to shuffle and draw cards
        dealButton.setOnClickListener(v -> {
            drawCards(7, "draw", 0);
            llButtons.setVisibility(View.VISIBLE);
            dealButton.setVisibility(View.GONE);
            llp1.setBackgroundResource(R.drawable.border_highlight);
        });

        hitButton.setOnClickListener(v -> {
            drawCards(1, "hit", position);
        });

        standButton.setOnClickListener(v -> {
            standClickEvent(position);
        });

        doubleButton.setOnClickListener(v -> {
            drawCards(1, "double", position);

            standClickEvent(position);
        });

        splitButton.setOnClickListener(v -> {

        });
    }

    private void standClickEvent(int pos) {
        switch (pos) {
            case 0:
                llp1.setBackgroundResource(0);
                llp3.setBackgroundResource(0);
                lld.setBackgroundResource(0);
                llp2.setBackgroundResource(R.drawable.border_highlight);
                position = 1; // Check if this is the correct position increment logic
                break;
            case 1:
                llp1.setBackgroundResource(0);
                llp2.setBackgroundResource(0);
                lld.setBackgroundResource(0);
                llp3.setBackgroundResource(R.drawable.border_highlight);
                position = 2; // Check if this is the correct position increment logic
                break;
            case 2:
                llp1.setBackgroundResource(0);
                llp2.setBackgroundResource(0);
                llp3.setBackgroundResource(0);
                lld.setBackgroundResource(R.drawable.border_highlight);
                position = 3; // Check if this is the correct position increment logic
                break;
            default:
                // Handle default case or do nothing
        }
    }

    private void drawCards(int count, String msg, int pos) {
        playViewModel.drawCard(deckId, count).observe(this, drawCardResponse -> {
            if (drawCardResponse != null) {
                List<Card> cards = drawCardResponse.getCards();
                for (int i = 0; i < Math.min(cards.size(), count); i++) {
                    Card card = cards.get(i);
                    // Update the corresponding RecyclerView based on the position
                    if (msg.equalsIgnoreCase("draw")) {
                        updateRecyclerView(i, card);
                    } else {
                        updateRecyclerView(pos, card);
                    }
                }
            } else {
                // Handle null response or error
            }
        });
    }

    private void updateRecyclerView(int position, Card card) {
        int cardValue;

        switch (position) {
            case 0:
            case 4:
                player1Adapter.addCard(card);
                cardValue = calculateCardValue(card.getValue(), p1Total);
                p1Total += cardValue;
                checkBust("P1", p1Total);
                tvP1.setText(String.valueOf(p1Total));
                break;
            case 1:
            case 5:
                player2Adapter.addCard(card);
                cardValue = calculateCardValue(card.getValue(), p2Total);
                p2Total += cardValue;
                checkBust("P2", p2Total);
                tvP2.setText(String.valueOf(p2Total));
                break;
            case 2:
            case 6:
                player3Adapter.addCard(card);
                cardValue = calculateCardValue(card.getValue(), p3Total);
                p3Total += cardValue;
                checkBust("P3", p3Total);
                tvP3.setText(String.valueOf(p3Total));
                break;
            case 3:
                dealerAdapter.addCard(card);
                cardValue = calculateCardValue(card.getValue(), dTotal);
                dTotal += cardValue;
                checkDealerAction();
                checkBust("Dealer", dTotal);
                tvd.setText(String.valueOf(dTotal));
                break;
            // Add more cases if needed
        }
    }

    private void checkDealerAction() {
        if (dTotal >= 17) {
            llButtons.setVisibility(View.GONE);
            lld.setBackgroundResource(0);
            llp1.setBackgroundResource(0);
            llp2.setBackgroundResource(0);
            llp3.setBackgroundResource(0);
            clearButton.setVisibility(View.VISIBLE);
        }
    }

    private void checkBust(String playerName, int total) {
        if (total == 21) {
            Log.d("BustLog", playerName + " has Blackjack!");
            switch (playerName.toUpperCase()) {
                case "P1":
                    llp1.setBackgroundResource(0);
                    llp3.setBackgroundResource(0);
                    lld.setBackgroundResource(0);
                    llp2.setBackgroundResource(R.drawable.border_highlight);
                    position = 1;
                    break;
                case "P2":
                    llp1.setBackgroundResource(0);
                    llp2.setBackgroundResource(0);
                    lld.setBackgroundResource(0);
                    llp3.setBackgroundResource(R.drawable.border_highlight);
                    position = 2;
                    break;
                case "P3":
                    llp1.setBackgroundResource(0);
                    llp2.setBackgroundResource(0);
                    llp3.setBackgroundResource(0);
                    lld.setBackgroundResource(R.drawable.border_highlight);
                    position = 3;
                    break;
                case "DEALER":
                    llButtons.setVisibility(View.GONE);
                    llp1.setBackgroundResource(0);
                    llp2.setBackgroundResource(0);
                    llp3.setBackgroundResource(0);
                    lld.setBackgroundResource(0);
                    clearButton.setVisibility(View.VISIBLE);
                    break;
                default:
                    // Handle default case or do nothing
            }
        } else if (total > 21) {
            Log.d("BustLog", playerName + " has busted!");
            // Handle bust logic here
            switch (playerName.toUpperCase()) {
                case "P1":
                    llp1.setBackgroundResource(0);
                    llp3.setBackgroundResource(0);
                    lld.setBackgroundResource(0);
                    llp2.setBackgroundResource(R.drawable.border_highlight);
                    position = 1;
                    break;
                case "P2":
                    llp1.setBackgroundResource(0);
                    llp2.setBackgroundResource(0);
                    lld.setBackgroundResource(0);
                    llp3.setBackgroundResource(R.drawable.border_highlight);
                    position = 2;
                    break;
                case "P3":
                    llp1.setBackgroundResource(0);
                    llp2.setBackgroundResource(0);
                    llp3.setBackgroundResource(0);
                    lld.setBackgroundResource(R.drawable.border_highlight);
                    position = 3;
                    break;
                case "DEALER":
                    llButtons.setVisibility(View.GONE);
                    llp1.setBackgroundResource(0);
                    llp2.setBackgroundResource(0);
                    llp3.setBackgroundResource(0);
                    lld.setBackgroundResource(0);
                    clearButton.setVisibility(View.VISIBLE);
                    break;
                default:
                    // Handle default case or do nothing
            }
        }
    }

    private int calculateCardValue(String cardValue, int playerTotal) {
        if (cardValue.equals("ACE")) {
            // If adding 11 doesn't bust, use 11; otherwise, use 1
            return (playerTotal + 11 <= 21) ? 11 : 1;
        } else if (cardValue.equals("KING") || cardValue.equals("QUEEN") || cardValue.equals("JACK")) {
            // Face cards have a value of 10
            return 10;
        } else {
            // Numeric cards have values equal to their number
            return Integer.parseInt(cardValue);
        }
    }

}
