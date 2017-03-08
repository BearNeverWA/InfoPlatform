package com.makethings.infoplatform.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.makethings.infoplatform.Adapter.MessageAdapter;
import com.makethings.infoplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageActivity extends AppCompatActivity {

    private int userID;
    private int messageCount;


    private ListView lvMessage;
    private Button btMessageEdit;
    private Button btMessageClearRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("消息列表");

        messageCount = 5;

        lvMessage = (ListView) findViewById(R.id.lvMessage);
        btMessageEdit = (Button) findViewById(R.id.btMessageEdit);
        btMessageClearRead = (Button) findViewById(R.id.btMessageClearRead);

        List<Map<String, Object>> listMessage = GetMessage();
        lvMessage.setAdapter(new MessageAdapter(this, listMessage));
        SetListViewHeightBasedOnChildren(lvMessage);
    }

    public List<Map<String, Object>> GetMessage() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < messageCount; i++) {
            Map<String, Object> mapMessage = new HashMap<>();
            mapMessage.put("messageFrom", "消息"+i+" 来自xxx");
            mapMessage.put("messageDate", "2017/2/5");
            mapMessage.put("messageDetail", "消息详情");
            list.add(mapMessage);
        }
        return list;
    }

    public void SetListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));

        listView.setLayoutParams(params);
    }
}