package com.example.shruj.nytimesapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by shruj on 02/29/2016.
 */
public class GetNewsAsyncTask extends AsyncTask<String, Integer, ArrayList<Story>> {

    Activity activity;
    ProgressDialog progressDialog;
    TopStoriesActivity t = new TopStoriesActivity();

    public GetNewsAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Story> doInBackground(String... params) {

        RequestParam requestParam = new RequestParam(Constants.GET_METHOD, Constants.NEWS_APP_URL);
        requestParam.addParam(Constants.API_KEY, Constants.API_KEY_VALUE);
        requestParam.addParam(Constants.SECTION, params[0] + "." + Constants.RESPONSE_FORMAT_VALUE);

        Log.d("demo", "request" + requestParam.getEncodedUrl());
        try {
            HttpURLConnection httpURLConnection = requestParam.setUpConnection();
            int statusCode = httpURLConnection.getResponseCode();
            Log.d("demo", "statusCode" + statusCode);
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                Log.d("demo", "line" + line);
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                try {
                    Log.d("demo", "json" + stringBuilder.toString());
                    return NewsUtil.NewsJSONParser.parseStory(stringBuilder.toString());
                } catch (JSONException e) {
                    Log.d("demo", e.getMessage());
                    Log.d("demo", e.getLocalizedMessage());
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(Constants.PROGRESS_DIALOG_MESSAGE);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(Boolean.FALSE);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Story> stories) {
        super.onPostExecute(stories);
        Log.d("demo", "onpostexecute");
        progressDialog.dismiss();
        if (stories != null) {
            setTopStoriesActivityElements(stories);
        } else {
            activity.finish();
        }
    }

    private void setTopStoriesActivityElements(final ArrayList<Story> stories) {
        StoryAdapter storyAdapter = new StoryAdapter(activity, R.layout.listview_row_item_layout, stories);

        ListView listView = (ListView) activity.findViewById(R.id.listView);
        listView.setAdapter(storyAdapter);

        storyAdapter.setNotifyOnChange(Boolean.TRUE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo", stories.get(position).toString());
                Intent intent = new Intent(activity, DetailsActivity.class);
                intent.putExtra(Constants.ARRAY_LIST_OBJECT, (Serializable) stories.get(position));
                activity.startActivity(intent);
            }
        });
    }
}
