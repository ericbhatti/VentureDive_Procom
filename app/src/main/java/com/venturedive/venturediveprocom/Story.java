package com.venturedive.venturediveprocom;

/**
 * Created by Eric Bhatti on 4/4/2017.
 */

public class Story {

    String name,story, imageURL;

    public Story() {
    }

    public Story(String name, String story, String imageURL) {
        this.name = name;
        this.story = story;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
