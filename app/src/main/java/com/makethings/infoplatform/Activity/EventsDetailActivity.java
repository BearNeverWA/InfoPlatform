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

import com.makethings.infoplatform.Adapter.EventsDetailAdapter;
import com.makethings.infoplatform.Adapter.EventsIssuedClub;
import com.makethings.infoplatform.Publicdata;
import com.makethings.infoplatform.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventsDetailActivity extends AppCompatActivity {

    private String aId;
    private String uId;
    private String cId;

    private String cIntroduction;

    private TextView tvEventsName;
    private ImageView ivEventsPic;
    private ListView lvEventsDetailInfo;
    private ListView lvEventsIssuedClub;
    private Button btEnrollEvents;
    private Button btCheckEvents;
    private MenuItem itActionFollow;


    private String aName;
    private String aSummary;
    private String acticityName;

    /*
    private int eventsIssuedClubCount;
    private int[] eventsIssuedClubID;
    */
    private String cName;

    private int[] type = {0, 1, 2, 3, 4, 5, 6, 7};

    private String aStartTime;
    private String aLocation;
    private String aMaxPeople;
    private String aSetPeople;
    private String aIntroduction;
    private String aVisible;
    private String aEndTime;
    private String aStopRegistTime;

    private String uCollectAct;
    private String uJoinAct;

    private boolean collectEn;
    private boolean joinEn;


    private int collectRes;
    private boolean syncFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_detail);
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("活动详情");

        uId = Publicdata.uId;

        syncFlag = false;

        final Intent intent = getIntent();
        aId = intent.getStringExtra("aId");

        //aId="1";

        /*
        uCollectAct = "[1,2,3]";
        uJoinAct = "[1,2,3]";
*/

        Intent intent2 = getIntent();
        aSummary = intent2.getStringExtra("aSummary");
        acticityName = intent2.getStringExtra("aName");

        collectEn = Publicdata.uCollectAct.contains(aId);
        joinEn = Publicdata.uJoinAct.contains(aId);

        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("aId", aId);


        final Request request = new Request.Builder()
                .url("http://community.stevenming.com.cn/a_0_jActDetail")
                .post(encodingBuilder.build())
                .build();


        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                try {

                    JSONObject activityJson = new JSONObject(s);
                    System.out.println(activityJson);
                    JSONObject activity = activityJson.getJSONObject("activity");

                    aName = activity.getString("aName");
                    aLocation = activity.getString("aLocation");
                    aStartTime = activity.getString("aStartTime").replace("T", " ");
                    aEndTime = activity.getString("aEndTime").replace("T", " ");
                    aStopRegistTime = activity.getString("aStopRegistTime").replace("T", " ");

                    aMaxPeople = activity.getString("aMaxPeople");
                    aSetPeople = activity.getString("aSetPeople");

                    aVisible = String.valueOf(activity.getInt("aVisible"));

                    aIntroduction = activity.getString("aIntroduction");
                    cId = activity.getString("cId");

                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                    encodingBuilder.add("cId", cId);


                    final Request request = new Request.Builder()
                            .url("http://community.stevenming.com.cn/c_0_jComDetail")
                            .post(encodingBuilder.build())
                            .build();


                    Call call = mOkHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Request request, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Response response) throws IOException {
                            final String s = response.body().string();
                            try {
                                JSONObject communityInfoJson = new JSONObject(s);
                                System.out.println(communityInfoJson);
                                JSONObject community = communityInfoJson.getJSONObject("community");
                                cName = community.getString("cName");
                                cIntroduction = community.getString("cIntroduction");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            syncFlag = true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dataInit();
                                }
                            });
                        }
                    });

                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });

        tvEventsName = (TextView) findViewById(R.id.tvEventsName);
        ivEventsPic = (ImageView) findViewById(R.id.ivClubPic);
        lvEventsDetailInfo = (ListView) findViewById(R.id.lvEventsDetailInfo);
        lvEventsIssuedClub = (ListView) findViewById(R.id.lvEventsIssuedClub);
        btEnrollEvents = (Button) findViewById(R.id.btEnrollEvents);
        btCheckEvents = (Button) findViewById(R.id.btCheckEvents);

        btCheckEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(EventsDetailActivity.this,CheckEventActivity.class);
                intent1.putExtra("aId",aId);
                intent1.putExtra("aName",acticityName);
                intent1.putExtra("aSummary",aSummary);
                startActivity(intent1);
            }
        });

        btEnrollEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncFlag = false;
                OkHttpClient mOkHttpClient = new OkHttpClient();
                FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                encodingBuilder.add("uId", uId);
                encodingBuilder.add("aId", aId);


                final Request request = new Request.Builder()
                        .url("http://community.stevenming.com.cn/aa_1_jApply")
                        .post(encodingBuilder.build())
                        .build();


                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String s = response.body().string();
                        try {
                            JSONObject jRes = new JSONObject(s);
                            collectRes = jRes.getInt("success");
                            syncFlag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                while (!syncFlag) {
                }

                Toast.makeText(view.getContext(), collectRes == 1 ? "申请参加活动成功" : "申请参加活动失败", Toast.LENGTH_SHORT).show();
                Publicdata.uJoinAct.add(aId);
                btEnrollEvents.setEnabled(false);

            }
        });

    }

    public void dataInit() {
        tvEventsName.setText(aName);
        List<Map<String, Object>> listEventsDetailInfo = GetEventsDetailInfo();
        lvEventsDetailInfo.setAdapter(new EventsDetailAdapter(this, listEventsDetailInfo, type));
        SetListViewHeightBasedOnChildren(lvEventsDetailInfo);

        List<Map<String, Object>> listEventsIssuedClub = GetEventsIssuedClub();
        lvEventsIssuedClub.setAdapter(new EventsIssuedClub(this, listEventsIssuedClub, 1/*eventsIssuedClubCount*/));
        SetListViewHeightBasedOnChildren(lvEventsIssuedClub);

        lvEventsIssuedClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("cId", cId/*eventsIssuedClubID[i]*/);
                intent.setClass(view.getContext(), ClubDetailActivity.class);
                startActivity(intent);
            }
        });

        if (joinEn || aVisible.equals("0")) {
            btEnrollEvents.setEnabled(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_events_detail, menu);

        itActionFollow = menu.findItem(R.id.actionEventsFollow);

        if (collectEn) {
            itActionFollow.setTitle("取消关注");
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actionEventsFollow:

                syncFlag = false;
                OkHttpClient mOkHttpClient = new OkHttpClient();
                FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                encodingBuilder.add("uId", uId);
                encodingBuilder.add("uCollectAct", aId);


                final Request request = new Request.Builder()
                        .url("http://community.stevenming.com.cn/uc_1_jCollect")
                        .post(encodingBuilder.build())
                        .build();


                Call call = mOkHttpClient.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {
                        final String s = response.body().string();
                        try {
                            JSONObject jRes = new JSONObject(s);
                            collectRes = jRes.getInt("success");
                            syncFlag = true;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
                while (!syncFlag) {
                }

                if (!collectEn) {
                    Toast.makeText(this, collectRes == 1 ? "关注成功" : "关注失败", Toast.LENGTH_SHORT).show();
                    itActionFollow.setTitle("取消关注");
                    Publicdata.uCollectAct.add(aId);
                    collectEn = true;
                } else {
                    Toast.makeText(this, collectRes == 1 ? "取消关注成功" : "取消关注失败", Toast.LENGTH_SHORT).show();
                    itActionFollow.setTitle("关注");
                    Publicdata.uCollectAct.remove(aId);
                    collectEn = false;
                }
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Map<String, Object>> GetEventsDetailInfo() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapEventsStartTime, mapEventsEndTime, mapEventsStopRegTime, mapEventsIsVisible, mapEventsPlace, mapEventsMaxCapacity, mapEventsEnrolledCount, mapEventsIntroduction;

        mapEventsStartTime = new HashMap<>();
        mapEventsStopRegTime = new HashMap<>();
        mapEventsEndTime = new HashMap<>();
        mapEventsPlace = new HashMap<>();
        mapEventsMaxCapacity = new HashMap<>();
        mapEventsEnrolledCount = new HashMap<>();
        mapEventsIntroduction = new HashMap<>();
        mapEventsIsVisible = new HashMap<>();

        mapEventsStartTime.put("aStartTime", aStartTime);
        mapEventsStopRegTime.put("aStopRegTime", aStopRegistTime);
        mapEventsEndTime.put("aEndTime", aEndTime);

        mapEventsPlace.put("aLocation", aLocation);
        mapEventsMaxCapacity.put("aMaxPeople", aMaxPeople);
        mapEventsEnrolledCount.put("aSetPeople", aSetPeople);
        mapEventsIsVisible.put("aVisible", aVisible);

        mapEventsIntroduction.put("aIntroduction", aIntroduction);

        list.add(mapEventsStartTime);
        list.add(mapEventsStopRegTime);
        list.add(mapEventsEndTime);
        list.add(mapEventsPlace);
        list.add(mapEventsMaxCapacity);
        list.add(mapEventsEnrolledCount);
        list.add(mapEventsIsVisible);
        list.add(mapEventsIntroduction);
        return list;
    }

    public List<Map<String, Object>> GetEventsIssuedClub() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < 1/*eventsIssuedClubCount*/; i++) {
            Map<String, Object> mapClubEvents = new HashMap<>();
            mapClubEvents.put("eventsIssuedClubName", cName);
            mapClubEvents.put("eventsIssuedClubInfo", cIntroduction);
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