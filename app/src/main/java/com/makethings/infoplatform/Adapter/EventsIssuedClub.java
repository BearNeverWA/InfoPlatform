package com.makethings.infoplatform.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makethings.infoplatform.R;

import java.util.List;
import java.util.Map;

/**
 * Created by bhL on 2017/2/4.
 */

public class EventsIssuedClub extends BaseAdapter {
    private List<Map<String, Object>> listEventsIssuedClub;
    private LayoutInflater layoutInflater;
    private Context context;
    private int eventsIssuedClubCount;

    public EventsIssuedClub(Context context, List<Map<String, Object>> listEventsIssuedClub, int eventsIssuedClubCount) {
        this.context = context;
        this.eventsIssuedClubCount = eventsIssuedClubCount;
        this.listEventsIssuedClub = listEventsIssuedClub;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Component {
        public TextView tvEventsIssuedClubName;
        public TextView tvEventsIssuedClubInfo;
    }

    @Override
    public int getCount() {
        return listEventsIssuedClub.size();
    }

    @Override
    public Object getItem(int position) {
        return listEventsIssuedClub.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Component component;
        component = new Component();
        convertView = layoutInflater.inflate(R.layout.list_events_issued_club, null);
        component.tvEventsIssuedClubName = (TextView) convertView.findViewById(R.id.tvEventsIssuedClubName);
        component.tvEventsIssuedClubInfo = (TextView) convertView.findViewById(R.id.tvEventsIssuedClubInfo);

        component.tvEventsIssuedClubName.setText((String) listEventsIssuedClub.get(position).get("eventsIssuedClubName"));
        component.tvEventsIssuedClubInfo.setText((String) listEventsIssuedClub.get(position).get("eventsIssuedClubInfo"));

        return convertView;
    }
}