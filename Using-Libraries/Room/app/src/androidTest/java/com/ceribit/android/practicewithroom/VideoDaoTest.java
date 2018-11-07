package com.ceribit.android.practicewithroom;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class VideoDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private VideoDao mVideoDao;
    private VideoRoomDatabase mDb;

    @Before
    public void createDb(){
        Context context = InstrumentationRegistry.getTargetContext();

        mDb = Room.inMemoryDatabaseBuilder(context, VideoRoomDatabase.class)
                .allowMainThreadQueries()
                .build();
        mVideoDao = mDb.videoDao();
    }

    @After
    public void closeDb(){
        mDb.close();
    }

    @Test
    public void insertAndGetVideo() throws Exception {
        Video video = new Video("sampleVideo");
        mVideoDao.insert(video);
        List<Video> allVideos = LiveDataTestUtil.getValue(mVideoDao.getAllVideos());
        assertEquals(allVideos.get(0).getTitle(), video.getTitle());
    }

    @Test
    public void getAllVideos() throws Exception {
        Video video = new Video("sampleVideo1");
        mVideoDao.insert(video);
        Video video1 = new Video("sampleVideo2");
        mVideoDao.insert(video1);
        List<Video> allVideos = LiveDataTestUtil.getValue(mVideoDao.getAllVideos());
        assertEquals(allVideos.get(0).getTitle(), video.getTitle());
        assertEquals(allVideos.get(1).getTitle(), video1.getTitle());
    }

    @Test
    public void deleteAll() throws Exception {
        Video video = new Video("sampleVideo1");
        mVideoDao.insert(video);
        Video video1 = new Video("sampleVideo2");
        mVideoDao.insert(video1);
        mVideoDao.deleteAll();
        List<Video> allVideos = LiveDataTestUtil.getValue(mVideoDao.getAllVideos());
        assertTrue(allVideos.isEmpty());
    }

}
