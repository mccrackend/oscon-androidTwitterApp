package com.sagetech.oscon;

import java.util.concurrent.Callable;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Dan on 7/20/14.
 */
public interface TwitterService {
    public static final String API_URL = "https://api.twitter.com/1.1";

    @GET("/search/tweets.json?q=OSCON")
    public void searchTweets(
            Callback<SearchResponse> callback
    );
}
