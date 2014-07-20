package com.sagetech.oscon;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.apache.http.entity.StringEntity;

import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RestAdapter client = new RestAdapter.Builder()
                        .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.BASIC)
                        .setEndpoint(TwitterService.API_URL)
                        .setRequestInterceptor(new RequestInterceptor() {
                            @Override
                            public void intercept(RequestFacade requestFacade) {
                                requestFacade.addHeader("Authorization", TwitterService.AUTH_HEADER);
                            }
                        })
                        .build();

                // generate an implementation for the service
                TwitterService service = client.create(TwitterService.class);

                final EditText searchQuery = (EditText) findViewById(R.id.query);

                // access the api
                service.searchTweets(searchQuery.getText().toString(), new Callback<SearchResponse>() {
                    @Override
                    public void success(SearchResponse scheduleResponse, Response response) {
                        List<Tweet> events = scheduleResponse.tweets;

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
        });


    }

    private static class ListAdapter extends ArrayAdapter<Tweet> {
        private Context mContext;
        private int mResourceId;
        private List<Tweet> mItems;

        public ListAdapter(Context context, int resourceId, List<Tweet> items) {
            super(context, resourceId, items);
            mContext = context;
            mResourceId = resourceId;
            mItems = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            Tweet item = mItems.get(position);
            view = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
            TextView tv = (TextView) view.findViewById(R.id.label);
            tv.setText(item.text);

            ImageView avatar = (ImageView) view.findViewById(R.id.avatar);
            Picasso.with(mContext)
                    .load(item.user.profileImageUrl)
                    .placeholder(R.drawable.ic_launcher)
                    .into(avatar);

            return view;
        }
    }
}
