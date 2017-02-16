package com.example.yanyining.zhihudaily.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by YanYiNing on 2017/2/15.
 */

public class FirstData {
    public String date;

    @SerializedName("stories")
    public List<Story> stories;

    @SerializedName("top_stories")
    public List<TopStory> topStories;

    public class Story {
        public List<String> images;
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
    }

    public class TopStory {
        public String image;
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
    }

}
