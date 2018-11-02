package com.ceribit.android.practicewithroom;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * Manages one or more data sources
 * This class wraps AsyncTasks around actions
 */
public class VideoRepository {
    private VideoDao mVideoDao;
    private LiveData<List<Video>> mAllVideos;

    VideoRepository(Application application){
        VideoRoomDatabase db = VideoRoomDatabase.getDatabase(application);
        mVideoDao = db.videoDao();
        mAllVideos = mVideoDao.getAllVideos();
    }

    LiveData<List<Video>> getAllVideos(){
        return mAllVideos;
    }

    public void insert(Video video){
        new insertAsyncTask(mVideoDao).execute(video);
    }

    private static class insertAsyncTask extends AsyncTask<Video,Void,Void> {

        private VideoDao mAsyncTaskDao;

        insertAsyncTask(VideoDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Video... videos) {
            mAsyncTaskDao.insert(videos[0]);
            return null;
        }
    }


}
