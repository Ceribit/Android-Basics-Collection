package com.ceribit.android.practicewithroom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    class VideoViewHolder extends RecyclerView.ViewHolder {
        private final TextView videoItemView;

        private VideoViewHolder(View itemView) {
            super(itemView);
            videoItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Video> mVideos; // Cached copy of words

    VideoListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new VideoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        if (mVideos != null) {
            Video current = mVideos.get(position);
            holder.videoItemView.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.videoItemView.setText("No Word");
        }
    }

    void setVideos(List<Video> videos){
        mVideos = videos;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mVideos != null)
            return mVideos.size();
        else return 0;
    }
}