package com.makethings.infoplatform.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.makethings.infoplatform.Activity.ClubDetailActivity;
import com.makethings.infoplatform.Adapter.EventsDetailAdapter;
import com.makethings.infoplatform.EventsIssuedClub;
import com.makethings.infoplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsDetailActivity extends AppCompatActivity {

    private int eventsID;
    private int userID;

    private TextView tvEventsName;
    private ImageView ivEventsPic;
    private ListView lvEventsDetailInfo;
    private ListView lvEventsIssuedClub;
    private Button btEnrollEvents;

    private String eventsName;
    private int eventsIssuedClubCount;
    private int[] eventsIssuedClubID;

    private int[] type = {0, 1, 2, 3, 4, 5};

    private String eventsTime;
    private String eventsPlace;
    private String eventsMaxCapacity;
    private String eventsEnrolledCount;
    private String eventsFollowCount;
    private String eventsIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("活动详情");

        Intent intent = getIntent();
        eventsID = intent.getIntExtra("eventsID", 0);

        eventsName = "活动" + eventsID;
        eventsTime = "2017/2/4 20:00";
        eventsPlace = "图书馆";
        eventsMaxCapacity = "200";
        eventsEnrolledCount = "30";
        eventsFollowCount = "50";
        eventsIntroduction = "这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍这里是活动介绍";

        eventsIssuedClubCount = 1;
        eventsIssuedClubID = new int[eventsIssuedClubCount];
        eventsIssuedClubID[0] = 1;

        tvEventsName = (TextView) findViewById(R.id.tvEventsName);
        ivEventsPic = (ImageView) findViewById(R.id.ivClubPic);
        lvEventsDetailInfo = (ListView) findViewById(R.id.lvEventsDetailInfo);
        lvEventsIssuedClub = (ListView) findViewById(R.id.lvEventsIssuedClub);
        btEnrollEvents = (Button) findViewById(R.id.btEnrollEvents);

        tvEventsName.setText(eventsName);


        List<Map<String, Object>> listEventsDetailInfo = GetEventsDetailInfo();
        lvEventsDetailInfo.setAdapter(new EventsDetailAdapter(this, listEventsDetailInfo, type));
        SetListViewHeightBasedOnChildren(lvEventsDetailInfo);

        List<Map<String, Object>> listEventsIssuedClub = GetEventsIssuedClub();
        lvEventsIssuedClub.setAdapter(new EventsIssuedClub(this, listEventsIssuedClub, eventsIssuedClubCount));
        SetListViewHeightBasedOnChildren(lvEventsIssuedClub);

        btEnrollEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lvEventsIssuedClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("clubID", eventsIssuedClubID[i]);
                intent.setClass(view.getContext(), ClubDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_events_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionEventsFollow:
                Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Map<String, Object>> GetEventsDetailInfo() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapEventsTime, mapEventsPlace, mapEventsMaxCapacity, mapEventsEnrolledCount, mapEventsFollowCount, mapEventsIntroduction;

        mapEventsTime = new HashMap<>();
        mapEventsPlace = new HashMap<>();
        mapEventsMaxCapacity = new HashMap<>();
        mapEventsEnrolledCount = new HashMap<>();
        mapEventsFollowCount = new HashMap<>();
        mapEventsIntroduction = new HashMap<>();

        mapEventsTime.put("eventsTime", eventsTime);
        mapEventsPlace.put("eventsPlace", eventsPlace);
        mapEventsMaxCapacity.put("eventsMaxCapacity", eventsMaxCapacity);
        mapEventsEnrolledCount.put("eventsEnrolledCount", eventsEnrolledCount);
        mapEventsFollowCount.put("eventsFollowCount", eventsFollowCount);
        mapEventsIntroduction.put("eventsIntroduction", eventsIntroduction);

        list.add(mapEventsTime);
        list.add(mapEventsPlace);
        list.add(mapEventsMaxCapacity);
        list.add(mapEventsEnrolledCount);
        list.add(mapEventsFollowCount);
        list.add(mapEventsIntroduction);
        return list;
    }

    public List<Map<String, Object>> GetEventsIssuedClub() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < eventsIssuedClubCount; i++) {
            Map<String, Object> mapClubEvents = new HashMap<>();
            mapClubEvents.put("eventsIssuedClubName", "社团" + eventsIssuedClubID[i]);
            mapClubEvents.put("eventsIssuedClubInfo", "简介");
            list.add(mapClubEvents);
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