package com.example.shruj.nytimesapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by shruj on 02/29/2016.
 */
public class Story implements Serializable {

    String storyTitle, storyByLine, storyAbstract, storyCreatedDate, storyThumbImageURL, storyNormalImageURL;

    public String getStoryTitle() {
        return storyTitle;
    }

    public void setStoryTitle(String storyTitle) {
        this.storyTitle = storyTitle;
    }

    public String getStoryByLine() {
        return storyByLine;
    }

    public void setStoryByLine(String storyByLine) {
        this.storyByLine = storyByLine;
    }

    public String getStoryAbstract() {
        return storyAbstract;
    }

    public void setStoryAbstract(String storyAbstract) {
        this.storyAbstract = storyAbstract;
    }

    public String getStoryCreatedDate() {
        return storyCreatedDate;
    }

    public void setStoryCreatedDate(String storyCreatedDate) {
        this.storyCreatedDate = storyCreatedDate;
    }

    public String getStoryThumbImageURL() {
        return storyThumbImageURL;
    }

    public void setStoryThumbImageURL(String storyThumbImageURL) {
        this.storyThumbImageURL = storyThumbImageURL;
    }

    public String getStoryNormalImageURL() {
        return storyNormalImageURL;
    }

    public void setStoryNormalImageURL(String storyNormalImageURL) {
        this.storyNormalImageURL = storyNormalImageURL;
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyTitle='" + storyTitle + '\'' +
                ", storyByLine='" + storyByLine + '\'' +
                ", storyAbstract='" + storyAbstract + '\'' +
                ", storyCreatedDate='" + storyCreatedDate + '\'' +
                ", storyThumbImageURL='" + storyThumbImageURL + '\'' +
                ", storyNormalImageURL='" + storyNormalImageURL + '\'' +
                '}';
    }

    public static Story createStoryFromJSON(JSONObject storyJSONObject) throws JSONException {
        Story story = new Story();

        try {

            story.setStoryTitle(storyJSONObject.getString(Constants.JSON_ROOT_TITLE));
            story.setStoryAbstract(storyJSONObject.getString(Constants.JSON_ROOT_ABSTRACT));
            story.setStoryByLine(storyJSONObject.getString(Constants.JSON_ROOT_BY_LINE));
            story.setStoryCreatedDate(storyJSONObject.getString(Constants.JSON_ROOT_CREATED_DATE));
            String multimediaArray = storyJSONObject.getString(Constants.JSON_ROOT_MULTIMEDIA);
            if ((!multimediaArray.isEmpty()) || (!multimediaArray.toString().equals(""))) {
                if (storyJSONObject.getJSONArray(Constants.JSON_ROOT_MULTIMEDIA).getClass() == JSONArray.class) {
                    JSONArray jsonArrayURLs = storyJSONObject.getJSONArray(Constants.JSON_ROOT_MULTIMEDIA);
                    story.setStoryThumbImageURL(jsonArrayURLs.getJSONObject(0).getString(Constants.JSON_ROOT_NORMAL_IMAGE) == null ? null : jsonArrayURLs.getJSONObject(0).getString(Constants.JSON_ROOT_NORMAL_IMAGE));
                    story.setStoryNormalImageURL(jsonArrayURLs.getJSONObject(2).getString(Constants.JSON_ROOT_THUMB_IMAGE) == null ? null : jsonArrayURLs.getJSONObject(2).getString(Constants.JSON_ROOT_THUMB_IMAGE));


                } else {
                    story.setStoryNormalImageURL(null);
                    story.setStoryThumbImageURL(null);
                }

            } else {
                story.setStoryNormalImageURL(null);
                story.setStoryThumbImageURL(null);
            }
        } catch (
                JSONException e
                )

        {
            e.printStackTrace();
        }

        return story;
    }
}
