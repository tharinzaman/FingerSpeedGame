package com.example.fingerspeed2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fingerspeed2.ROOM.databaseMethods;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity {

    //Declare all variables
    private TextView tvTimer, tvNumber;
    private FrameLayout btnTap;
    private Button btnStart, btnReset;

    private CountDownTimer countdownTimer;

    private final long initialCountdownInMillis = 60000;
    private final int timeInterval = 1000;
    private int remainingTime = 60;

    private int number = 500;

    private int amountTapped = 0;
    private int tapsPerSecond;

    private MediaPlayer player;

    public static final String MyPREFERENCES = "MyPrefs";
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    public Result result;
    Calendar c;
    Date dateTime;
    SimpleDateFormat sdf;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        // Initialise all variables

        tvTimer = findViewById(R.id.tvTime);
        tvTimer.setText(Integer.toString(remainingTime));
        tvNumber = findViewById(R.id.tvNumber);
        tvNumber.setText(Integer.toString(number));

        btnTap = findViewById(R.id.flTapButton);
        btnTap.setEnabled(false);
        btnTap.setBackground(getDrawable(R.drawable.grey_filled_circular_button));
        btnStart = findViewById(R.id.btnStart);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setEnabled(false);
        btnReset.setBackgroundColor(getResources().getColor(R.color.button_disabled));

        // Set on click listeners for start button and reset button:
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame(btnStart);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTheGame(btnReset);
            }
        });

        prefs = PlayActivity.this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor = prefs.edit();

        c = Calendar.getInstance();
        dateTime = c.getTime();
        sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
        date = sdf.format(dateTime);

    }

    // Start game
    public void startGame(View v) {
        btnStart.setEnabled(false);
        btnStart.setBackgroundColor(getResources().getColor(R.color.button_disabled));

        // in cases of restarting game restart the countdown timer:
        if (countdownTimer != null) {
            countdownTimer.cancel();
            countdownTimer = null;
        }

        // Timer going down
        countdownTimer = new CountDownTimer(initialCountdownInMillis, timeInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime = (int) millisUntilFinished / 1000;
                tvTimer.setText(Integer.toString(remainingTime));
            }

            // If lost
            @Override
            public void onFinish() {
                btnTap.setEnabled(false);
                btnTap.setBackground(getDrawable(R.drawable.grey_filled_circular_button));
                tapsPerSecond = (int) (amountTapped / (60 - remainingTime));
                showAlert(R.string.lost_title, R.string.reset_game);
                Toast.makeText(PlayActivity.this, "You tapped at " +
                        tapsPerSecond + " taps per second!", Toast.LENGTH_LONG).show();
                if (prefs.getInt("highestScore", 0) < tapsPerSecond){
                    editor.putInt("highestScore", tapsPerSecond).apply();
                }
                try {
                    player = MediaPlayer.create(getApplicationContext(), R.raw.lose);
                    player.setLooping(false);
                    player.start();
                } catch (Exception e){
                    e.printStackTrace();
                }
                result = Result.Lost;
                databaseMethods.insertAttempt(date, result, tapsPerSecond, amountTapped, HistoryActivity.attemptDao);
            }
        };
        countdownTimer.start();

        // Sort out tap button and its on click:
        btnTap.setEnabled(true);
        btnTap.setBackground(getDrawable(R.drawable.white_filled_circular_button));
        btnTap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number--;
                tvNumber.setText(Integer.toString(number));
                amountTapped++;

                // If won
                if (remainingTime > 0 && number <= 0) {
                    btnTap.setEnabled(false);
                    btnTap.setBackground(getDrawable(R.drawable.grey_filled_circular_button));
                    countdownTimer.cancel();
                    tapsPerSecond = (int) (amountTapped / (60 - remainingTime));
                    showAlert(R.string.won_title, R.string.reset_game);
                    Toast.makeText(PlayActivity.this, "You tapped at " +
                            tapsPerSecond + " taps per second!", Toast.LENGTH_LONG).show();
                    if (prefs.getInt("highestScore", 0) < tapsPerSecond){
                        editor.putInt("highestScore", tapsPerSecond).apply();
                    }
                    try {
                        player = MediaPlayer.create(getApplicationContext(), R.raw.win);
                        player.setLooping(false);
                        player.start();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    result = Result.Won;
                    databaseMethods.insertAttempt(date, result, tapsPerSecond, amountTapped, HistoryActivity.attemptDao);
                }

            }
        });
    }

    // Reset game when offered.
    private void resetTheGame() {

        number = 1000;
        tvNumber.setText(Integer.toString(number));

        remainingTime = 60;
        tvTimer.setText(Integer.toString(remainingTime));

        btnStart.setEnabled(true);
        btnStart.setBackgroundColor(getResources().getColor(R.color.white));
    }

    //Reset game using reset button.
    public void resetTheGame(View v) {
        btnReset.setEnabled(false);
        btnReset.setBackgroundColor(getResources().getColor(R.color.button_disabled));

        number = 1000;
        tvNumber.setText(Integer.toString(number));

        remainingTime = 60;
        tvTimer.setText(Integer.toString(remainingTime));

        btnStart.setEnabled(true);
        btnStart.setBackgroundColor(getResources().getColor(R.color.white));
    }

    // End alert
    private void showAlert(int title, int message) {
        AlertDialog alterDialog = new AlertDialog.Builder(PlayActivity.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetTheGame();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        btnReset.setEnabled(true);
                        btnReset.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                })
                .show();
        alterDialog.setCancelable(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        countdownTimer.cancel();
    }
}