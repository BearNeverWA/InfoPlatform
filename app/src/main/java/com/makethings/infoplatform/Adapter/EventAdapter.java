package com.makethings.infoplatform.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.makethings.infoplatform.Event;
import com.makethings.infoplatform.R;

import java.util.List;

/**
 * Created by Call Me Bear on 2017/3/9.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    private int resourceId;

    public EventAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Event> objects) {
        super(context, resource, objects);
        resourceId=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Event event=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView tvEventName= (TextView) view.findViewById(R.id.tv_event_name);
        TextView tvEventSummary= (TextView) view.findViewById(R.id.tv_event_summary);
        Button btnFollow= (Button) view.findViewById(R.id.btn_follow);
        tvEventName.setText(event.getName());
        tvEventSummary.setText(event.getSummary());
        if (event.getIsFollow())
            btnFollow.setText("♥");
        else
            btnFollow.setText("♡");
        return view;
    }
}
