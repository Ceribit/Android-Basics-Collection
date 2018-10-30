package com.ceribit.android.asynctaskwithnewsapi;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ceribit.android.asynctaskwithnewsapi.Utility.QueryUtils;
import com.ceribit.android.asynctaskwithnewsapi.models.Article;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Updates the TextViews from the activity_main.xml to the news retrieved over the internet
     * @param articles
     */
    private void updateDescription(List<Article> articles){
        TextView titleTextView = findViewById(R.id.tv_news_title);
        TextView descriptionTextView = findViewById(R.id.tv_news_description);
        TextView contentTextView = findViewById(R.id.tv_news_content);

        Article article = articles.get(0);

        titleTextView.setText(article.getTitle());
        descriptionTextView.setText(article.getDescription());
        contentTextView.setText(article.getContent());
    }

    /**
     * Executes the asynchronous task to retrieve data
     * @param view Button that is called for the asynchronous task
     */
    public void retrieveData(View view){
        EditText mTopicsTv = findViewById(R.id.et_news_search);
        String queryTopic = mTopicsTv.getText().toString();
        RetrieveNewsAsyncTask task = new RetrieveNewsAsyncTask(this);
        task.execute(queryTopic);
    }

    /**
     * Retrieves data from the News API asynchronously
     */
    private static class RetrieveNewsAsyncTask extends AsyncTask<String, Void, List<Article>>{
        WeakReference<MainActivity> mainActivityWeakReference;

        private RetrieveNewsAsyncTask(MainActivity activity) {
            super();
            this.mainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected List<Article> doInBackground(String ... strings) {
            String url = "https://newsapi.org/v2/everything?" +
                    "q=" + strings[0] + "&" +
                    "from=2018-10-30&" +
                    "sortBy=popularity&" +
                    "apiKey=ca94863bfd564da79762e6ab6c0c8690";
            return QueryUtils.fetchNewsData(url);
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            super.onPostExecute(articles);
                MainActivity activity = mainActivityWeakReference.get();
            activity.updateDescription(articles);
        }
    }

}
