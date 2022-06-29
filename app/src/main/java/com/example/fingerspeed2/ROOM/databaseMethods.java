package com.example.fingerspeed2.ROOM;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import com.example.fingerspeed2.HistoryActivity;
import com.example.fingerspeed2.HistoryAdapter;
import com.example.fingerspeed2.Result;

import java.util.ArrayList;
import java.util.List;

public class databaseMethods {

    public static AttemptDatabaseJava buildDatbase(Context context) {
        AttemptDatabaseJava db = Room.databaseBuilder(context,
                AttemptDatabaseJava.class, "attempt-table").allowMainThreadQueries().build();
        return db;
    }

    public static void insertAttempt(String date, Result result, int tapsPerSecond, int amountTapped, AttemptDaoJava dao) {
        AttemptEntityJava attempt = new AttemptEntityJava();
        attempt.setDate(date);
        attempt.setResult(result);
        attempt.setTapsPerSecond(tapsPerSecond);
        attempt.setTapsCompleted(amountTapped);

        insertAttemptPrivate(attempt, dao);
    }

    private static void insertAttemptPrivate(final AttemptEntityJava attempt, AttemptDaoJava attemptDao) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                attemptDao.insert(attempt);
                return null;
            }
        }.execute();
    }


}
