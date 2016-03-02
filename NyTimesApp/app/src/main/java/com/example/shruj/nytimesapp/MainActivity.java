package com.example.shruj.nytimesapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String value[] = {"home", "world", "national", "politics", "nyregion", "business", "opinion", "technology",
                "science", "health", "sports", "arts", "fashion", "dining", "travel", "magazine", "realestate"};
        final Spinner spin = (Spinner) findViewById(R.id.spinner);
        Button submitbtn = (Button) findViewById(R.id.submitbutton);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, value);
        spin.setAdapter(adapter);

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedOnline()) {
                    Intent intent = new Intent(MainActivity.this, TopStoriesActivity.class);
                    intent.putExtra("NewsItem", spin.getSelectedItem().toString());
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isConnectedOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
