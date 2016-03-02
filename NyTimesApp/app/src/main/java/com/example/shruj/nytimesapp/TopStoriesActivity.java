package com.example.shruj.nytimesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class TopStoriesActivity extends AppCompatActivity implements IActivity {

    Intent intent;
    String newsItem;
    static ListView listView;
    StoryAdapter storyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_stories);

        intent = getIntent();

        listView = (ListView) findViewById(R.id.listView);

        if (intent.getExtras() != null) {
            newsItem = intent.getExtras().getString(Constants.NEWS_ITEM);
            Log.d("demo", "topstories");
            new GetNewsAsyncTask(TopStoriesActivity.this).execute(newsItem);
        }

    }

    @Override
    public void setListView(ArrayList<Story> stories) {

        for (Story story : stories) {
            Log.d("demo", story.toString());
        }

        storyAdapter = new StoryAdapter(TopStoriesActivity.this, R.layout.listview_row_item_layout, stories);
        listView.setAdapter(storyAdapter);
        storyAdapter.setNotifyOnChange(Boolean.TRUE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
