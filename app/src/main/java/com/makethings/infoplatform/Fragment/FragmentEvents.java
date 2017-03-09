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
import com.makethings.infoplatform.R;

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
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        EventAdapter adapter=new EventAdapter(getActivity(),R.layout.content_list_event,eventList);
        ListView lvEvents = (ListView) getActivity().findViewById(R.id.lv_events);
        lvEvents.setAdapter(adapter);
        lvEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event=eventList.get(position);
                Intent intent=new Intent(getActivity(),EventsDetailActivity.class);
                String str=event.getName().substring(4,5);
                int Id=Integer.parseInt(str);
                intent.putExtra("eventsID",Id);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        for (int i = 0; i < 15; i++){
            Event event=new Event("测试名称"+i,"测试简介"+i,false);
            eventList.add(event);
        }
    }
}
