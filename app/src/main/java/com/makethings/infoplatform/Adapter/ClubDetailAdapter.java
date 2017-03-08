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
 * Created by bhL on 2017/1/29.
 */

public class ClubDetailAdapter extends BaseAdapter {
    private List<Map<String, Object>> listClubDetailInfo;
    private LayoutInflater layoutInflater;
    private Context context;
    private int[] type;

    public ClubDetailAdapter(Context context, List<Map<String, Object>> listClubDetailInfo, int[] type) {
        this.context = context;
        this.type = type;
        this.listClubDetailInfo = listClubDetailInfo;
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
        return listClubDetailInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listClubDetailInfo.get(position);
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
                convertView = layoutInflater.inflate(R.layout.list_club_category, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvClubCategory);
                component.tv.setText((String) listClubDetailInfo.get(position).get("clubCategory"));
                break;
            case 1:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_club_member_count, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvClubMemberCount);
                component.tv.setText((String) listClubDetailInfo.get(position).get("clubMemberCount"));
                break;
            case 2:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_club_follow_count, null);
                component.tv = (TextView) convertView.findViewById(R.id.tvClubFollowCount);
                component.tv.setText((String) listClubDetailInfo.get(position).get("clubFollowCount"));
                break;
            case 3:
                component = new Component();
                convertView = layoutInflater.inflate(R.layout.list_club_introduction, null);
                component.multiTV = (MultiLineTextView) convertView.findViewById(R.id.tvClubIntroduction);
                component.multiTV.setText((String) listClubDetailInfo.get(position).get("clubIntroduction"));
                break;
        }

        return convertView;
    }
}