package com.example.fingerspeed2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.example.fingerspeed2.ROOM.AttemptDaoJava;
import com.example.fingerspeed2.ROOM.AttemptDatabaseJava;
import com.example.fingerspeed2.ROOM.AttemptEntityJava;
import com.example.fingerspeed2.ROOM.databaseMethods;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout btnStart = findViewById(R.id.flStartButton);
        FrameLayout btnHistory = findViewById(R.id.flHistoryButton);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PlayActivity.class);
                startActivity(i);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), HistoryActivity.class));
            }
        });
    }

}