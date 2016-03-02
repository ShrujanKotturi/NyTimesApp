package com.example.shruj.nytimesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by shruj on 02/29/2016.
 */
public class NewsUtil {
    static public class NewsJSONParser {
        static ArrayList<Story> parseStory(String inputStreamString) throws JSONException {
            ArrayList<Story> arrayListStories = new ArrayList<>();

            JSONObject jsonObjectRoot = new JSONObject(inputStreamString);
            JSONArray jsonArrayStories = jsonObjectRoot.getJSONArray(Constants.JSON_ROOT_NAME);

            for (int i = 0; i < jsonArrayStories.length(); i++) {
                JSONObject storyJSONObject = jsonArrayStories.getJSONObject(i);
                Story story = Story.createStoryFromJSON(storyJSONObject);
                arrayListStories.add(story);
            }

            return arrayListStories;
        }

    }
}
