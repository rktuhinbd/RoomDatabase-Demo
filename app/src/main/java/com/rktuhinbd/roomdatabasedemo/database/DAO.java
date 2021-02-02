package com.rktuhinbd.roomdatabasedemo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DAO {

    // = = = Insert data = = = //
    @Insert(onConflict = REPLACE)
    void insert(MyData myDB);

    // = = = Delete data = = = //
    @Delete()
    void delete(MyData myDB);

    // = = = Delete all data = = = //
    @Delete()
    void deleteAll(List<MyData> myDBList);

    // = = = Update data = = = //
    @Query("UPDATE DATA_TABLE SET text = :updateText Where ID = :updateID")
    void update(int updateID, String updateText);

    // = = = Get all data = = = //
    @Query("SELECT * FROM DATA_TABLE")
    List<MyData> getAllData();

}
