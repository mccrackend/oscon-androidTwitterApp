package com.sagetech.oscon;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Dan on 7/20/14.
 */
public class SearchResponse {
    @SerializedName("statuses")
    List<Tweet> tweets;
}
