package com.venturedive.venturediveprocom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Eric Bhatti on 4/4/2017.
 */

public class StoryViewHolder extends RecyclerView.ViewHolder {

    public TextView textViewName, textViewStory;
    public ImageView imageViewPhoto;

    public StoryViewHolder(View itemView) {
        super(itemView);
        textViewName = (TextView) itemView.findViewById(R.id.textViewName);
        textViewStory = (TextView) itemView.findViewById(R.id.textViewStory);
        imageViewPhoto = (ImageView) itemView.findViewById(R.id.imageViewPhoto);
    }
}
