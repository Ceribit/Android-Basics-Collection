package com.ceribit.android.practicewithroom;

import android.support.annotation.NonNull;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "video_table")
public class Video {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String title;

//    @ColumnInfo(name = "description")
//    private String description;
//
//    @ColumnInfo(name = "url")
//    private String url;
//
//    @ColumnInfo(name = "creator_name")
//    private String creator_name;

    public Video(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getTitle(){
        return this.title;
    }
}
