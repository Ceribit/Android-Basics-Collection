package com.ceribit.android.practicewithroom;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;


@Database(entities = {Video.class}, version = 1)
public abstract class VideoRoomDatabase extends RoomDatabase{
    public abstract VideoDao videoDao();

    // Make the database a singleton
    private static volatile VideoRoomDatabase INSTANCE;

    static VideoRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (VideoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    VideoRoomDatabase.class, "video_database")
                        .addCallback(sRoomDatabaseCallback)
                    .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final VideoDao mDao;

        PopulateDbAsync(VideoRoomDatabase db) {
            mDao = db.videoDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Video video = new Video("Hello");
            mDao.insert(video);
            video = new Video("World");
            mDao.insert(video);
            return null;
        }
    }
}
