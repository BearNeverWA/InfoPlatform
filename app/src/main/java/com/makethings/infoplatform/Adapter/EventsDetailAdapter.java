package com.makethings.infoplatform.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makethings.infoplatform.MultiLineTextView;
import com.makethings.infoplatform.R;

import java.util.List;
import java.util.Map;

/**
 * Created by bhL on 2017/2/4.
 */

public class EventsDetailAdapter extends BaseAdapter {

    private List<Map<String, Object>> listEventsDetailInfo;
    private LayoutInflater layoutInflater;
    private Context context;
    private int[] type;

    public EventsDetailAdapter(Context context, List<Map<String, Object>> listEventsDetailInfo, int[] type) {
        this.context = context;
        this.type = type;
        this.listEventsDetailInfo = listEventsDetailInfo;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Component {
        public TextView tv;
        public MultiLineTextView multiTV;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public int getCount() {
        return listEventsDetailInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listEventsDetailInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Component component;
        switch (type[position]) {
            case 0:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_start_time, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvEventsStartTime);
                component.tv.setText((String) listEventsDetailInfo.get(position).get("aStartTime"));
                break;
            case 1:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_stop_reg_time, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvEventsStopRegTime);
                component.tv.setText((String) listEventsDetailInfo.get(position).get("aStopRegTime"));
                break;
            case 2:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_end_time, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvEventsEndTime);
                component.tv.setText((String) listEventsDetailInfo.get(position).get("aEndTime"));
                break;
            case 3:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_place, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvEventsPlace);
                component.tv.setText((String) listEventsDetailInfo.get(position).get("aLocation"));
                break;
            case 4:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_max_capacity, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvEventsMaxCapacity);
                component.tv.setText((String) listEventsDetailInfo.get(position).get("aMaxPeople"));
                break;
            case 5:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_enrolled_count, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvEventsEnrolledCount);
                component.tv.setText((String) listEventsDetailInfo.get(position).get("aSetPeople"));
                break;
            case 6:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_is_visible, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvEventsIsVisible);
                String isVis=(String)listEventsDetailInfo.get(position).get("aVisible");
                if(isVis.equals("1"))
                    component.tv.setText("是");
                else
                    component.tv.setText("否");
                break;
            case 7:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_events_introduction, null);
                component.multiTV = (MultiLineTextView) convertView.findViewById(R.id.tvEventsIntroduction);
                component.multiTV.setText((String) listEventsDetailInfo.get(position).get("aIntroduction"));
                break;
        }

        return convertView;
    }
}