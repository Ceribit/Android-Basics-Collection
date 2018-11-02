package com.ceribit.android.practicewithroom;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ceribit.android.practicewithroom.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VideoViewModel mVideoViewModel;

    public static final int NEW_VIDEO_ACTIVITY_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign recyclerview items to the recyclerview
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final VideoListAdapter adapter = new VideoListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the ViewModel
        mVideoViewModel = ViewModelProviders.of(this).get(VideoViewModel.class);

        // Get all the videos from the ViewModel
        mVideoViewModel.getAllVideos().observe(this, new Observer<List<Video>>() {
            // Update the videos every time there is a change in the list
            @Override
            public void onChanged(@Nullable List<Video> videos) {
                adapter.setVideos(videos);
            }
        });


        Button button = findViewById(R.id.fab_fake_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewVideoActivity.class);
                startActivityForResult(intent, NEW_VIDEO_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_VIDEO_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Video video = new Video(data.getStringExtra(NewVideoActivity.EXTRA_REPLY));
            mVideoViewModel.insert(video);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}
