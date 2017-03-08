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
 * Created by bhL on 2017/2/5.
 */

public class MessageAdapter extends BaseAdapter {

    private List<Map<String, Object>> listMessage;
    private LayoutInflater layoutInflater;
    private Context context;

    public MessageAdapter(Context context, List<Map<String, Object>> listMessage) {
        this.context = context;
        this.listMessage = listMessage;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public final class Component {
        public TextView tvMessageFrom;
        public TextView tvMessageDate;
        public MultiLineTextView tvMessageDetail;
    }

    @Override
    public int getCount() {
        return listMessage.size();
    }

    @Override
    public Object getItem(int position) {
        return listMessage.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Component component;
        component = new Component();
        convertView = layoutInflater.inflate(R.layout.list_message, null);
        component.tvMessageFrom = (TextView) convertView.findViewById(R.id.tvMessageFrom);
        component.tvMessageDate = (TextView) convertView.findViewById(R.id.tvMessageDate);
        component.tvMessageDetail = (MultiLineTextView) convertView.findViewById(R.id.tvMessageDetail);

        component.tvMessageFrom.setText((String) listMessage.get(position).get("messageFrom"));
        component.tvMessageDate.setText((String) listMessage.get(position).get("messageDate"));
        component.tvMessageDetail.setText((String) listMessage.get(position).get("messageDetail"));

        return convertView;
    }
}