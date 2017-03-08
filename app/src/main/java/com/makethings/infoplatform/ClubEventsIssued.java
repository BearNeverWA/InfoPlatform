package com.makethings.infoplatform;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


/**
 * Created by bhL on 2017/1/29.
 */

public class ClubEventsIssued extends BaseAdapter {
    private List<Map<String, Object>> listClubEventsIssued;
    private LayoutInflater layoutInflater;
    private Context context;
    private int clubEventsIssuedCount;

    public ClubEventsIssued(Context context, List<Map<String, Object>> listClubEventsIssued, int clubEventsIssuedCount) {
        this.context = context;
        this.clubEventsIssuedCount = clubEventsIssuedCount;
        this.listClubEventsIssued = listClubEventsIssued;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Component {
        public TextView tvEventsIssuedName;
        public TextView tvEventsIssuedInfo;
    }

    @Override
    public int getCount() {
        return listClubEventsIssued.size();
    }

    @Override
    public Object getItem(int position) {
        return listClubEventsIssued.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Component component;
        component = new Component();
        convertView = layoutInflater.inflate(R.layout.list_club_events_issued, null);
        component.tvEventsIssuedName = (TextView) convertView.findViewById(R.id.tvClubEventsName);
        component.tvEventsIssuedInfo = (TextView) convertView.findViewById(R.id.tvClubEventsInfo);

        component.tvEventsIssuedName.setText((String) listClubEventsIssued.get(position).get("clubEventsIssuedName"));
        component.tvEventsIssuedInfo.setText((String) listClubEventsIssued.get(position).get("clubEventsIssuedInfo"));

        return convertView;
    }
}