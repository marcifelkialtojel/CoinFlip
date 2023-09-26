package com.example.coinflip;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ImageView coinImage;
    private Button headsButton;
    private Button tailsButton;
    private TextView dobas;
    private TextView win;
    private TextView vesztes;

    private boolean isHeads = true;
    private int throwsCount = 0;
    private int winsCount = 0;
    private int lossesCount = 0;

    private final int MAX_THROWS = 5;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coinImage = findViewById(R.id.coinImage);
        headsButton = findViewById(R.id.headsButton);
        tailsButton = findViewById(R.id.tailsButton);
        dobas = findViewById(R.id.dobas);
        win = findViewById(R.id.win);
        vesztes = findViewById(R.id.vesztes);

        headsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("head");
            }
        });

        tailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("tails");
            }
        });
    }

    private void playGame(final String choice) {

        Random random = new Random();
        int eredmeny = random.nextInt(2);


        if (eredmeny == 0) {
            coinImage.setImageResource(isHeads ? R.drawable.tails : R.drawable.heads);
            isHeads = !isHeads;
        } else {
            coinImage.setImageResource(isHeads ? R.drawable.heads : R.drawable.tails);
            isHeads = !isHeads;
        }


        throwsCount++;
        dobas.setText("Dobások: " + throwsCount);

        if (eredmeny == 0) {
            winsCount++;
            win.setText("Győzelem: " + winsCount);
        }
        else {
            lossesCount++;
            vesztes.setText("Vereség: " + lossesCount);
        }


        String toastMessage = (eredmeny == 0) ? "Eredmény: Fej" : "Eredmény: Írás";
        Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();


        if (throwsCount >= MAX_THROWS || winsCount > lossesCount) {
            showGameResultDialog();
        }
    }

    private void showGameResultDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (winsCount > lossesCount) {
            builder.setTitle("Győzelem");
            builder.setMessage("Gratulálok, nyertél!");
        } else {
            builder.setTitle("Vereség");
            builder.setMessage("Szeretnél újra játszani?");
        }

        builder.setPositiveButton("Igen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });

        builder.setNegativeButton("Nem", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.show();
    }

    private void resetGame() {
        throwsCount = 0;
        winsCount = 0;
        lossesCount = 0;
        dobas.setText("Dobások: 0");
        win.setText("Győzelem: 0");
        vesztes.setText("Vereség: 0");
        isHeads = true;
        coinImage.setImageResource(R.drawable.heads);
    }
}