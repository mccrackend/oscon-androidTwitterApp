package com.sagetech.oscon;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Dan on 7/20/14.
 */
public interface OSCONService {
    public static final String BASE_API = "http://www.oreilly.com";

    @GET("/pub/sc/osconfeed")
    void getSchedule(Callback<ScheduleResponse> callback);
}
