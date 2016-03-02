package com.example.shruj.nytimesapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailsActivity extends AppCompatActivity {

    Intent intent;
    Story story;
    TextView textViewStoryTitle, textViewStoryByLine, textViewAbstractValue;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        intent = getIntent();

        if (intent.getExtras() != null) {
            story = (Story) getIntent().getExtras().getSerializable(Constants.ARRAY_LIST_OBJECT);
        }
        textViewStoryTitle = (TextView) findViewById(R.id.textViewStoryTitle);
        textViewStoryByLine = (TextView) findViewById(R.id.textViewStoryByLine);
        textViewAbstractValue = (TextView) findViewById(R.id.textViewAbstractValue);
        imageView = (ImageView) findViewById(R.id.imageView);

        textViewStoryTitle.setText(story.getStoryTitle());
        textViewStoryByLine.setText(story.getStoryByLine());
        textViewAbstractValue.setText(story.getStoryAbstract());


        if (story.getStoryNormalImageURL() != null) {
            Picasso.with(this)
                    .load(story.getStoryNormalImageURL().toString())
                    .into(imageView);
            //new GetImageAsyncTask().execute(story.getStoryNormalImageURL());
        } else {
            imageView.setImageBitmap(null);
        }
    }

    private class GetImageAsyncTask extends AsyncTask<String, Integer, Bitmap> {
        ProgressDialog progressDialog;
        Bitmap bitmapImage;
        InputStream inputStream;

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");// "POST"
                inputStream = httpURLConnection.getInputStream();
                bitmapImage = BitmapFactory.decodeStream(inputStream);
                return bitmapImage;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            progressDialog.dismiss();
            imageView.setImageBitmap(bitmap);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(DetailsActivity.this);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(Boolean.FALSE);
            progressDialog.show();
        }
    }
}
