package com.makethings.infoplatform.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.makethings.infoplatform.Activity.EventsDetailActivity;
import com.makethings.infoplatform.Adapter.EventAdapter;
import com.makethings.infoplatform.Event;
import com.makethings.infoplatform.Publicdata;
import com.makethings.infoplatform.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Call Me Bear on 2017/2/26.
 */

public class FragmentEvents extends Fragment {

    private List<Event> eventList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
//        onActivityCreated(savedInstanceState);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        ListView lvEvents= (ListView) getActivity().findViewById(R.id.lv_events);
        EventAdapter adapter=new EventAdapter(getActivity(),R.layout.content_list_event,eventList);
        lvEvents.setAdapter(adapter);
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event=eventList.get(position);
                Intent intent=new Intent(getActivity(),EventsDetailActivity.class);
                String str=eventList.get(position).getaId();
                intent.putExtra("aId",str);
                intent.putExtra("aName",eventList.get(position).getName());
                intent.putExtra("aSummary",eventList.get(position).getSummary());
                startActivity(intent);
            }
        });
    }

    private void initData() {

        OkHttpClient mOkHttpClient1 = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId", Publicdata.uId);
        encodingBuilder.add("page","0");
        final Request request1 = new Request.Builder()
                .url("http://community.stevenming.com.cn/a_0_jActList")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
                .post(encodingBuilder.build())
                .build();


        Call call1 = mOkHttpClient1.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                System.out.println(s);

                try {
                    JSONObject json = new JSONObject(s);
                    JSONObject activity = json.getJSONObject("activity");
                    JSONArray activities = activity.getJSONArray("list");

                    for (int i = 1; i < activities.length(); i++) {
                        JSONObject temp = (JSONObject) activities.get(i);
                        Event event=new Event(temp.getString("aId"),temp.getString("aName"),temp.getString("aIntroduction"),false);
                        eventList.add(event);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
