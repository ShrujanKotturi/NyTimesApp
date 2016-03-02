package com.example.shruj.nytimesapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by shruj on 02/29/2016.
 */
public class StoryAdapter extends ArrayAdapter<Story> {
    List<Story> mData;
    Context mContext;
    int mResource;

    public StoryAdapter(Context context, int resource, List<Story> objects) {
        super(context, resource, objects);
        this.mData = objects;
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);

            holder = new ViewHolder();
            holder.textViewNewsTitle = (TextView) convertView.findViewById(R.id.textViewNewsTitle);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.textViewDate);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);

        }

        Story story = mData.get(position);

        holder = (ViewHolder) convertView.getTag();
        TextView textViewNewsTitle = holder.textViewNewsTitle;
        TextView textViewDate = holder.textViewDate;
        ImageView imageView = holder.imageView;

        textViewNewsTitle.setText(story.getStoryTitle());
        String[] date = story.getStoryCreatedDate().split("-");

        textViewDate.setText(date[1] + "/" + date[2].substring(0, 2));
        if (story.getStoryThumbImageURL() != null) {
            Picasso.with(mContext)
                    .load(story.getStoryThumbImageURL().toString())
                    .into(imageView);

            Log.d("demo", story.getStoryThumbImageURL());
        }
        return convertView;
    }

    static class ViewHolder {
        TextView textViewNewsTitle;
        TextView textViewDate;
        ImageView imageView;
    }
}
