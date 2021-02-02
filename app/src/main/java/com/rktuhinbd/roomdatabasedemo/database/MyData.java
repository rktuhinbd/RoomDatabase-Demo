package com.rktuhinbd.roomdatabasedemo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "data_table")
public class MyData implements Serializable {

    // = = = ID column = = = //
    @PrimaryKey(autoGenerate = true)
    private int ID;

    // = = = Text column = = = //
    @ColumnInfo(name = "text")
    private String text;


    // = = = Getters and Setters = = = //

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
