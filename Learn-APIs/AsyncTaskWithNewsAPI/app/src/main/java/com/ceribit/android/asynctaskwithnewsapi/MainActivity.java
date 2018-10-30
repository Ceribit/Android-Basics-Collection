package com.ceribit.android.asynctaskwithnewsapi;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ceribit.android.asynctaskwithnewsapi.Utility.QueryUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.tv_news_description);

        RetrieveNewsAsyncTask task = new RetrieveNewsAsyncTask();
        try{
            String title = task.execute().get();
            textView.setText(title);
        } catch (InterruptedException e){
            Log.e("MainActivity", e.getMessage());
        } catch (ExecutionException e){
            Log.e("MainActivity", e.getMessage());
        }
    }

    static class RetrieveNewsAsyncTask extends AsyncTask<Void, Void, String>{

        @Override
        protected String doInBackground(Void... voids) {
            String url = "https://newsapi.org/v2/everything?" +
                    "q=Apple&" +
                    "from=2018-10-30&" +
                    "sortBy=popularity&" +
                    "apiKey=ca94863bfd564da79762e6ab6c0c8690";
            List<String> titleList = QueryUtils.fetchNewsData(url);
            return titleList.get(0);
        }


    }
}
