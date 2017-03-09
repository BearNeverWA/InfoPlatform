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

import com.makethings.infoplatform.Adapter.ClubDetailAdapter;
import com.makethings.infoplatform.ClubEventsIssued;
import com.makethings.infoplatform.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClubDetailActivity extends AppCompatActivity {

    private int clubID;
    private int userID;

    private TextView tvClubName;
    private ImageView ivClubPic;
    private ListView lvClubDetailInfo;
    private ListView lvClubEventsIssued;
    private Button btEnrollClub, btnModify, btnMember, btnIssue, btnManage;
    private int[] type = {0, 1, 2, 3};
    private int clubEventsIssuedCount;
    private int[] clubEventsIssuedID;

    private String clubName;
    private String clubCategory;
    private String clubMemberCount;
    private String clubFollowCount;
    private String clubIntroduction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("社团详情");
        }
        Intent intent = getIntent();
        clubID = intent.getIntExtra("clubID", 0);

        clubName = "社团" + clubID;
        clubCategory = "社团分类";
        clubMemberCount = "0";
        clubFollowCount = "0";
        clubIntroduction = "这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容这里是社团介绍内容";

        clubEventsIssuedCount = 3;
        clubEventsIssuedID = new int[clubEventsIssuedCount];
        clubEventsIssuedID[0] = 6;
        clubEventsIssuedID[1] = 7;
        clubEventsIssuedID[2] = 8;

        tvClubName = (TextView) findViewById(R.id.tvClubName);
        ivClubPic = (ImageView) findViewById(R.id.ivClubPic);
        lvClubDetailInfo = (ListView) findViewById(R.id.lvClubDetailInfo);
        lvClubEventsIssued = (ListView) findViewById(R.id.lvClubEventsIssued);
        btEnrollClub = (Button) findViewById(R.id.btEnrollClub);

        List<Map<String, Object>> listClubDetailInfo = GetClubDetailInfo();
        lvClubDetailInfo.setAdapter(new ClubDetailAdapter(this, listClubDetailInfo, type));
        SetListViewHeightBasedOnChildren(lvClubDetailInfo);

        List<Map<String, Object>> listClubActivityIssued = GetClubEventsIssued();
        lvClubEventsIssued.setAdapter(new ClubEventsIssued(this, listClubActivityIssued, clubEventsIssuedCount));
        SetListViewHeightBasedOnChildren(lvClubEventsIssued);

        tvClubName.setText(clubName);

        btEnrollClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        lvClubEventsIssued.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("eventsID", clubEventsIssuedID[i]);
                intent.setClass(view.getContext(), EventsDetailActivity.class);
                startActivity(intent);
            }
        });

        btnModify = (Button) findViewById(R.id.btn_modify_data);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent();
                i.setClass(ClubDetailActivity.this,ModifyClubDataActivity.class);
                startActivity(i);
            }
        });
        btnMember = (Button) findViewById(R.id.btn_mine_club_member);
        btnMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ClubDetailActivity.this,MineClubMembersActivity.class);
                startActivity(i);
            }
        });
        btnIssue = (Button) findViewById(R.id.btn_issue_event);
        btnIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ClubDetailActivity.this,IssueEventsActivity.class);
                startActivity(i);
            }
        });
        btnManage = (Button) findViewById(R.id.btn_check_event);
        btnManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ClubDetailActivity.this,CheckEventActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_club_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionClubFollow:
                Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Map<String, Object>> GetClubDetailInfo() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapClubCategory, mapClubMemberCount, mapClubFollowCount, mapClubIntroduction;

        mapClubCategory = new HashMap<>();
        mapClubMemberCount = new HashMap<>();
        mapClubFollowCount = new HashMap<>();
        mapClubIntroduction = new HashMap<>();

        mapClubCategory.put("clubCategory", clubCategory);
        mapClubMemberCount.put("clubMemberCount", clubMemberCount);
        mapClubFollowCount.put("clubFollowCount", clubFollowCount);
        mapClubIntroduction.put("clubIntroduction", clubIntroduction);

        list.add(mapClubCategory);
        list.add(mapClubMemberCount);
        list.add(mapClubFollowCount);
        list.add(mapClubIntroduction);
        return list;
    }

    public List<Map<String, Object>> GetClubEventsIssued() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < clubEventsIssuedCount; i++) {
            Map<String, Object> mapClubEvents = new HashMap<>();
            mapClubEvents.put("clubEventsIssuedName", "活动" + clubEventsIssuedID[i]);
            mapClubEvents.put("clubEventsIssuedInfo", "详情");
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