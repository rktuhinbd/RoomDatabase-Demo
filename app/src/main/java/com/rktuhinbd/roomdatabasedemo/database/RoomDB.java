package com.rktuhinbd.roomdatabasedemo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MyData.class}, version = 1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {

    // - - - Create database instance - - - //
    private static RoomDB roomDB;

    // - - - Define database name - - - //
    private static String DATABASE_NAME = "database";

    // - - - Get instance of the database - - - //
    public synchronized static RoomDB getInstance(Context context) {
        if (roomDB == null) {
            // - - - If database is null, initialize database - - - //
            roomDB = Room.databaseBuilder(context.getApplicationContext(),
                    RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return roomDB;
    }

    public abstract DAO dao();

}
