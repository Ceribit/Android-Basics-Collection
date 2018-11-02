package com.ceribit.android.practicewithroom;

import android.arch.lifecycle.LiveData;

import java.util.List;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface VideoDao {

    @Insert
    void insert(Video video);

    @Query("DELETE FROM video_table")
    void deleteAll();

    @Query("SELECT * FROM video_table ORDER BY title ASC")
    LiveData<List<Video>> getAllVideos();
}
