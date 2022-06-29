package com.example.fingerspeed2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.example.fingerspeed2.ROOM.AttemptDaoJava;
import com.example.fingerspeed2.ROOM.AttemptDatabaseJava;
import com.example.fingerspeed2.ROOM.AttemptEntityJava;
import com.example.fingerspeed2.ROOM.databaseMethods;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

public class HistoryActivity extends AppCompatActivity {

    int highestScore;
    TextView tvHighestScore;
    public static RecyclerView rvHistory;
    SharedPreferences prefs;

    public static AttemptDatabaseJava db;
    public static AttemptDaoJava attemptDao;
    public static List<AttemptEntityJava> attemptsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tvHighestScore = findViewById(R.id.tvHighestScore);


        prefs = HistoryActivity.this.getSharedPreferences(PlayActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        highestScore = prefs.getInt("highestScore", 0);
        tvHighestScore.setText(String.valueOf(highestScore));

        db = databaseMethods.buildDatbase(getApplicationContext());
        attemptDao = db.attemptDao();
        attemptsList = attemptDao.getAll();

        HistoryAdapter historyAdapter = new HistoryAdapter(attemptsList);
        rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new LinearLayoutManager(this));
        rvHistory.setAdapter(historyAdapter);

    }

}