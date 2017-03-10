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
import com.makethings.infoplatform.Adapter.ClubEventsIssued;
import com.makethings.infoplatform.Publicdata;
import com.makethings.infoplatform.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public class ClubDetailActivity extends AppCompatActivity {

    private String cId;

    // 全局变量
    private String uId;
    private String uPassword;

    private TextView tvClubName;
    private ImageView ivClubPic;
    private ListView lvClubDetailInfo;
    private ListView lvClubEventsIssued;
    private Button btEnrollClub;
    private int[] type = {0, 1, 2, 3, 4, 5};

    private String uName;

    private String cName;
    private String cType;
    private String cJoinPeople;
    private String cCollectPeople;
    private String cIntroduction;
    private String cWechatId;
    private String cWechatName;
    //private String[] cActivity;
    private int cActivityCount;
    private String[] cActivityName;
    private int[] cActivityId;
    private String[] cActivityLocation;


    private String uCollectAct;
    private String uCollectCom;
    private String uJoinAct;
    private String uJoinCom;

    private boolean collectEn;
    private boolean joinEn;

    private JSONObject community;

    private MenuItem itActionFollow;

    private boolean syncFlag;
    int collectRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("社团详情");

        /*
        Intent intnt=new Intent();
        intnt.setClass(this,IssueEventsActivity.class);
        intnt.putExtra("aId","1");
        intnt.putExtra("cId","1");
        startActivity(intnt);
        */


        syncFlag = false;

        Intent intent = getIntent();
        cId = intent.getStringExtra("cId");
        cName=intent.getStringExtra("cName");

        uId = Publicdata.uId;

        //cId = "2";

/*      uCollectCom = "[1,2,3]";
        uJoinCom = "[1,2,3]";
*/

        System.out.println(Publicdata.uCollectCom);
        System.out.println(Publicdata.uJoinCom);
        collectEn = Publicdata.uCollectCom.contains(cId);
        joinEn = Publicdata.uJoinCom.contains(cId);



        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        //encodingBuilder.add("uId",uId);
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


                    community = communityInfoJson.getJSONObject("community");
                    JSONObject cPeople = communityInfoJson.getJSONObject("communityPeople");

                    cName = community.getString("cName");
                    cType = community.getString("cType");
                    cIntroduction = community.getString("cIntroduction");
                    cWechatId = community.getString("cWechatId");
                    cWechatName = community.getString("cWechatName");

                    cJoinPeople = String.valueOf(cPeople.getInt("cJoinPeople"));
                    cCollectPeople = String.valueOf(cPeople.getInt("cCollectPeople"));


/*
                        String imageURL="http://community.stevenming.com.cn/images/cPicture/"+cId+".jpg";
                        try {
                            URL url = new URL(imageURL);

                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            conn.setDoInput(true);
                            conn.connect();
                            InputStream is = conn.getInputStream();
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            is.close();

                        }catch(MalformedURLException e) {
                            e.printStackTrace();
                        }
*/



                    JSONArray activityArray = communityInfoJson.getJSONArray("activity");
                    cActivityCount = activityArray.length();
                    System.out.println(activityArray);
                    System.out.println(cActivityCount);

                    cActivityName = new String[cActivityCount];
                    cActivityId = new int[cActivityCount];
                    cActivityLocation = new String[cActivityCount];

                    for (int i = 0; i < cActivityCount; i++) {
                        JSONObject activityTemp = activityArray.getJSONObject(i);

                        cActivityName[i] = activityTemp.getString("aName");
                        System.out.println(cActivityName[i]);
                        cActivityId[i] = activityTemp.getInt("aId");
                        System.out.println(cActivityId[i]);
                        cActivityLocation[i] = activityTemp.getString("aLocation");
                    }

                    syncFlag = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            dataInit();
                        }
                    });

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        });


        tvClubName = (TextView) findViewById(R.id.tvClubName);
        ivClubPic = (ImageView) findViewById(R.id.ivClubPic);
        lvClubDetailInfo = (ListView) findViewById(R.id.lvClubDetailInfo);
        lvClubEventsIssued = (ListView) findViewById(R.id.lvClubEventsIssued);
        btEnrollClub = (Button) findViewById(R.id.btEnrollClub);

        btEnrollClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncFlag = false;
                OkHttpClient mOkHttpClient = new OkHttpClient();
                FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                encodingBuilder.add("uId", uId);
                encodingBuilder.add("cId", cId);

                final Request request = new Request.Builder()
                        .url("http://community.stevenming.com.cn/ca_1_jApply")
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

                Toast.makeText(view.getContext(), collectRes == 1 ? "申请加入成功" : "申请加入失败", Toast.LENGTH_SHORT).show();
                Publicdata.uJoinCom.add(cId);

                btEnrollClub.setEnabled(false);

            }
        });

    }

    public void dataInit() {

        tvClubName.setText(cName);
        try {
            if (community.getString("cPicture").equals("1")) {
                int cPic = Integer.parseInt(cId);
                ivClubPic.setImageResource(R.drawable.cpic1 + cPic - 1);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        List<Map<String, Object>> listClubDetailInfo = GetClubDetailInfo();
        lvClubDetailInfo.setAdapter(new ClubDetailAdapter(this, listClubDetailInfo, type));
        SetListViewHeightBasedOnChildren(lvClubDetailInfo);

        List<Map<String, Object>> listClubActivityIssued = GetClubEventsIssued();
        lvClubEventsIssued.setAdapter(new ClubEventsIssued(this, listClubActivityIssued, cActivityCount));
        SetListViewHeightBasedOnChildren(lvClubEventsIssued);
        lvClubEventsIssued.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("aId", String.valueOf(cActivityId[i]));
                intent.setClass(view.getContext(), EventsDetailActivity.class);
                startActivity(intent);
            }
        });


        if (joinEn) {
            btEnrollClub.setEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_club_detail, menu);

        itActionFollow = menu.findItem(R.id.actionClubFollow);

        if (collectEn) {
            itActionFollow.setTitle("取消关注");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.actionClubFollow:
                syncFlag = false;
                OkHttpClient mOkHttpClient = new OkHttpClient();
                FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                encodingBuilder.add("uId", uId);
                encodingBuilder.add("uCollectCom", cId);


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
                    Publicdata.uCollectCom.add(cId);
                    collectEn = true;
                } else {
                    Toast.makeText(this, collectRes == 1 ? "取消关注成功" : "取消关注失败", Toast.LENGTH_SHORT).show();
                    itActionFollow.setTitle("关注");
                    Publicdata.uCollectCom.remove(cId);
                    collectEn = false;
                }
                break;
            default:
                this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Map<String, Object>> GetClubDetailInfo() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapClubCategory, mapClubMemberCount, mapClubFollowCount, mapClubIntroduction, mapClubWechatId, mapClubWechatName;

        mapClubCategory = new HashMap<>();
        mapClubMemberCount = new HashMap<>();
        mapClubFollowCount = new HashMap<>();
        mapClubIntroduction = new HashMap<>();
        mapClubWechatId = new HashMap<>();
        mapClubWechatName = new HashMap<>();

        mapClubCategory.put("cType", cType);
        mapClubMemberCount.put("cJoinPeople", cJoinPeople);
        mapClubFollowCount.put("cCollectPeople", cCollectPeople);
        mapClubIntroduction.put("cIntroduction", cIntroduction);
        mapClubWechatId.put("cWechatId", cWechatId);
        mapClubWechatName.put("cWechatName", cWechatName);

        list.add(mapClubCategory);
        list.add(mapClubMemberCount);
        list.add(mapClubFollowCount);
        list.add(mapClubWechatId);
        list.add(mapClubWechatName);
        list.add(mapClubIntroduction);

        return list;
    }

    public List<Map<String, Object>> GetClubEventsIssued() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < cActivityCount; i++) {
            Map<String, Object> mapClubEvents = new HashMap<>();
            mapClubEvents.put("aName", cActivityName[i]);
            mapClubEvents.put("aLocation", cActivityLocation[i]);
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