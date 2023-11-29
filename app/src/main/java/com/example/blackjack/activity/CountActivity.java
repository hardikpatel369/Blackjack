package com.example.blackjack.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.blackjack.R;

import java.util.ArrayList;
import java.util.List;

public class CountActivity extends AppCompatActivity {

    private int runningCount = 0;
    private final int numberOfDecks = 8;
    private final int numberOfCards = numberOfDecks * 4;
    private TextView decksLeftTextView, trueCountTextView, winChanceTextView;
    private List<String> clickedValuesList = new ArrayList<>();
    private LinearLayout horizontalLayout;
    private final int[] clickCount = new int[13];

    private final int[] buttonIds = {
            R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
            R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.buttonJ,
            R.id.buttonQ, R.id.buttonK, R.id.buttonA
    };

    private final TextView[] cardTextViews = new TextView[13];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        initializeViews();
        updateDecksLeftTextView();


        for (int i = 0; i < buttonIds.length; i++) {
            setupButtonListener(findViewById(buttonIds[i]), i);
        }

        setupResetButtonListener();
    }

    private void initializeViews() {
        horizontalLayout = findViewById(R.id.horizontalLayout);
        decksLeftTextView = findViewById(R.id.decksLeftTextView);
        trueCountTextView = findViewById(R.id.trueCountTextView);
        winChanceTextView = findViewById(R.id.winChanceTextView);

        cardTextViews[0] = findViewById(R.id.tv2);
        cardTextViews[1] = findViewById(R.id.tv3);
        cardTextViews[2] = findViewById(R.id.tv4);
        cardTextViews[3] = findViewById(R.id.tv5);
        cardTextViews[4] = findViewById(R.id.tv6);
        cardTextViews[5] = findViewById(R.id.tv7);
        cardTextViews[6] = findViewById(R.id.tv8);
        cardTextViews[7] = findViewById(R.id.tv9);
        cardTextViews[8] = findViewById(R.id.tv10);
        cardTextViews[9] = findViewById(R.id.tvJ);
        cardTextViews[10] = findViewById(R.id.tvQ);
        cardTextViews[11] = findViewById(R.id.tvK);
        cardTextViews[12] = findViewById(R.id.tvA);
        updateText();
    }

    private void setupButtonListener(Button button, int index) {
        button.setOnClickListener(view -> {
            if (clickCount[index] < numberOfCards) {
                String cardValue = (index < 9) ? String.valueOf(index + 2) : indexToCardValue(index);
                updateRunningCount(cardValue, index);
                clickedValuesList.add(cardValue);
                clickCount[index]++;
                if (clickCount[index] == 24) button.setEnabled(false);
                updateList();
            }
        });
    }

    private String indexToCardValue(int index) {
        return (index == 9) ? "J" : (index == 10) ? "Q" : (index == 11) ? "K" : "A";
    }

    private void setupResetButtonListener() {
        findViewById(R.id.resetButton).setOnClickListener(view -> resetGame());
    }

    private void updateRunningCount(String cardValue, int index) {
        int numericValue = parseCardValue(cardValue);
        runningCount += (numericValue >= 2 && numericValue <= 6) ? 1 : (numericValue >= 10) ? -1 : 0;
        updateTextView(cardTextViews[index]);
        updateDecksLeftTextView();
        updateTrueCountTextView();
        updateWinChanceTextView();
    }

    private int parseCardValue(String cardValue) {
        try {
            return Integer.parseInt(cardValue);
        } catch (NumberFormatException e) {
            return (cardValue.equals("J")) ? 11 : (cardValue.equals("Q")) ? 12 :
                    (cardValue.equals("K")) ? 13 : (cardValue.equals("A")) ? 1 : 0;
        }
    }

    private void updateTextView(TextView textView) {
        int newValue = Math.max(0, Integer.parseInt(textView.getText().toString()) - 1);
        textView.setText(String.valueOf(newValue));
    }

    private void updateList() {
        horizontalLayout.removeAllViews();
        for (int i = clickedValuesList.size() - 1; i >= 0; i--) {
            TextView textView = createTextView(clickedValuesList.get(i));
            horizontalLayout.addView(textView);
        }
    }

    private void updateText() {
        cardTextViews[0].setText(String.valueOf(numberOfCards));
        cardTextViews[1].setText(String.valueOf(numberOfCards));
        cardTextViews[2].setText(String.valueOf(numberOfCards));
        cardTextViews[3].setText(String.valueOf(numberOfCards));
        cardTextViews[4].setText(String.valueOf(numberOfCards));
        cardTextViews[5].setText(String.valueOf(numberOfCards));
        cardTextViews[6].setText(String.valueOf(numberOfCards));
        cardTextViews[7].setText(String.valueOf(numberOfCards));
        cardTextViews[8].setText(String.valueOf(numberOfCards));
        cardTextViews[9].setText(String.valueOf(numberOfCards));
        cardTextViews[10].setText(String.valueOf(numberOfCards));
        cardTextViews[11].setText(String.valueOf(numberOfCards));
        cardTextViews[12].setText(String.valueOf(numberOfCards));
    }

    private TextView createTextView(String text) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(6, 0, 6, 0);
        textView.setLayoutParams(params);
        textView.setText(text);
        return textView;
    }


    private void updateDecksLeftTextView() {
        decksLeftTextView.setText("Decks Left: " + numberOfDecks);
    }

    private void updateTrueCountTextView() {
        double trueCount = (double) runningCount / numberOfDecks;
        trueCountTextView.setText("True Count: " + String.format("%.2f", trueCount));
    }

    private void updateWinChanceTextView() {
        // Adjust this calculation based on your specific strategy and house rules
        double winChance = calculateWinningPercentage();
        winChanceTextView.setText("Winning Chance: " + String.format("%.2f", winChance) + "%");
    }

    private double calculateWinningPercentage() {
        // Adjust this calculation based on your strategy and rules
        double playerAdvantage = 0.5 * runningCount; // Adjust this factor based on your strategy
        double winChance = 50 + playerAdvantage;
        return Math.max(0, Math.min(100, winChance)); // Ensure the result is between 0 and 100
    }

    private void resetGame() {
        runningCount = 0;
        clickedValuesList.clear();

        // Reset click counts for each button
        for (int i = 0; i < clickCount.length; i++) {
            clickCount[i] = 0;
        }

        // Enable all buttons
        int[] buttonIds = {
                R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6,
                R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.buttonJ,
                R.id.buttonQ, R.id.buttonK, R.id.buttonA
        };

        for (int buttonId : buttonIds) {
            Button button = findViewById(buttonId);
            button.setEnabled(true);
        }

        updateText();
        updateDecksLeftTextView();
        updateTrueCountTextView();
        updateWinChanceTextView();
        updateList();
    }
}