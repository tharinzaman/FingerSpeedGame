package com.example.fingerspeed2.ROOM;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface AttemptDaoJava {

    @Insert
    void insert(AttemptEntityJava attemptEntity);

    @Query("SELECT * FROM `attempt-table`")
    List<AttemptEntityJava>getAll();

}
