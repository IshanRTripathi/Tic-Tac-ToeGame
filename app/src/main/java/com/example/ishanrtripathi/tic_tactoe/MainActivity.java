package com.example.ishanrtripathi.tic_tactoe;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        boolean gameActive = true;
        int player = 0;
        MediaPlayer mp1, mp2, mp3;
        int tagAll[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
        int winpos[][] = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

        android.support.v7.widget.GridLayout gl;

        LinearLayout layout;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mp1 = MediaPlayer.create(this, R.raw.tick);
            mp2 = MediaPlayer.create(this, R.raw.tac);
            mp3 = MediaPlayer.create(this, R.raw.background);
            mp3.start();
            mp3.setLooping(true);
            gl=findViewById(R.id.gridLayout);
            layout=findViewById(R.id.playAgainLayout);
        }

        @Override
        protected void onPause () {
            super.onPause();
            mp3.pause();
        }

        @Override
        protected void onResume () {
            super.onResume();
            mp3.start();
        }

        public void drop (View view){
            ImageView c = (ImageView) view;
            int tagNumber = Integer.parseInt(c.getTag().toString());

            if (tagAll[tagNumber] == 2 && gameActive) {

                if (player == 0) {
                    tagAll[tagNumber] = player;
                    c.setImageResource(R.drawable.x);
                    mp1.start();
                    player = 1;
                } else {
                    tagAll[tagNumber] = player;
                    c.setImageResource(R.drawable.o);
                    player = 0;
                    mp2.start();
                }

                c.setAlpha(0f);
                c.setScaleX(0f);
                c.setScaleY(0f);
                c.animate().alpha(1f).scaleX(1f).scaleY(1f).setDuration(500);
                for (int i : tagAll) {
                    Log.i("TAG", String.valueOf(i));
                }
                for (int win[] : winpos) {
                    if (tagAll[win[0]] == tagAll[win[1]] && tagAll[win[1]] == tagAll[win[2]] && tagAll[win[0]] != 2) {
                        if (player == 1) {
                            Toast.makeText(this, "Player X wins !!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Player O wins !!", Toast.LENGTH_SHORT).show();
                        }

                        LinearLayout layout = findViewById(R.id.playAgainLayout);
                        layout.setVisibility(View.VISIBLE);
                        gl.setAlpha(0.3f);
                        gl.animate().alpha(1f).setDuration(1000);

                        gameActive = false;
                        return;

                    } else {
                        gameActive = false;
                        for (int counter : tagAll) {
                            if (counter == 2)
                                gameActive = true;
                        }
                    }
                }
                if (!gameActive) {
                    Toast.makeText(this, "It's a Draw!", Toast.LENGTH_SHORT).show();
                    layout.setVisibility(View.VISIBLE);
                    gl.setAlpha(0.3f);

                }
            }
        }

        public void playAgain (View v)
        {
            layout.setVisibility(View.GONE);
            gl.setAlpha(1f);

            player = 0;
            for (int i = 0; i <= 8; i++) {
                tagAll[i] = 2;
            }
            gameActive = true;

            android.support.v7.widget.GridLayout gl = findViewById(R.id.gridLayout);
            for (int i = 0; i < gl.getChildCount(); i++) {
                ((ImageView) gl.getChildAt(i)).setImageResource(0);
            }
        }
    }