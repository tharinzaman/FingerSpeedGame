package com.example.fingerspeed2.ROOM;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.fingerspeed2.Result;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "attempt-table")
public class AttemptEntityJava implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date")
    String date;

    @ColumnInfo(name = "taps completed")
    int tapsCompleted;

    @ColumnInfo(name = "result")
    Result result;

    @ColumnInfo(name = "Taps per second")
    int tapsPerSecond;

    public int getId() {
        return id;
    }

    public void setId(int ID) {
        this.id = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public int getTapsPerSecond() {
        return tapsPerSecond;
    }

    public void setTapsPerSecond(int tapsPerSecond) {
        this.tapsPerSecond = tapsPerSecond;
    }

    public int getTapsCompleted(){
        return tapsCompleted;
    }

    public void setTapsCompleted(int tapsCompleted) {
        this.tapsCompleted = tapsCompleted;
    }
}
