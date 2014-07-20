package com.sagetech.oscon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.entity.StringEntity;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        RestAdapter client = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.BASIC)
                .setEndpoint(OSCONService.BASE_API)
                .build();

        // generate an implementation for the service
        OSCONService service = client.create(OSCONService.class);

        // access the api
        service.getSchedule(new Callback<ScheduleResponse>() {
            @Override
            public void success(ScheduleResponse scheduleResponse, Response response) {
                List<Event> events = scheduleResponse.schedule.events;

                ListView listView = (ListView) findViewById(R.id.list);

                ListAdapter adapter = new ListAdapter(
                        MyActivity.this, R.layout.list_row_view, events
                );

                listView.setAdapter(adapter);
            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });
    }

    private static class ListAdapter extends ArrayAdapter<Event> {
        private Context mContext;
        private int mResourceId;
        private List<Event> mItems;

        public ListAdapter(Context context, int resourceId, List<Event> items) {
            super(context, resourceId, items);
            mContext = context;
            mResourceId = resourceId;
            mItems = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            Event item = mItems.get(position);
            view = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
            TextView tv = (TextView) view.findViewById(R.id.label);
            tv.setText(item.name);
            return view;
        }
    }
}
