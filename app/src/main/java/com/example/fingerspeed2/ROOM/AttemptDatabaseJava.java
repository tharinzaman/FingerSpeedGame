package com.example.fingerspeed2.ROOM;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AttemptEntityJava.class}, version = 1)
public abstract class AttemptDatabaseJava extends RoomDatabase {
    public abstract AttemptDaoJava attemptDao();

}
