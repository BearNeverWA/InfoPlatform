package com.makethings.infoplatform.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class MineEnrollActivity extends AppCompatActivity {


    private RecyclerView mineEnrollRecyclerView;
    private List<Club> clubList = new ArrayList();
    private mineEnrollRecycleAdapter adapter;

    private RecyclerView mineEnrollActivityRecyclerView;
    private List<Club> activityList = new ArrayList();
    private mineEnrollActivityRecycleAdapter ActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_enroll);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的报名");
        initClubData();
        initActivityData();
        mineEnrollRecyclerView = (RecyclerView) findViewById(R.id.mine_enroll_club_recycler_view);
        mineEnrollRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new mineEnrollRecycleAdapter();
        adapter.setItemListener(new mineEnrollItemOnClick() {
            @Override
            public void itemOnClick(final View view, int pos) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(),"Click Event ",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mineEnrollRecyclerView.setAdapter(adapter);


        mineEnrollActivityRecyclerView = (RecyclerView) findViewById(R.id.mine_enroll_activity_recycler_view);
        mineEnrollActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActivityAdapter = new mineEnrollActivityRecycleAdapter();
        ActivityAdapter.setItemListener(new mineEnrollItemOnClick() {
            @Override
            public void itemOnClick(final View view, int pos) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(),"Click Event ",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mineEnrollActivityRecyclerView.setAdapter(ActivityAdapter);

        mineEnrollRecyclerView.setVisibility(View.VISIBLE);
        mineEnrollActivityRecyclerView.setVisibility(View.GONE);


        findViewById(R.id.mine_enroll_club_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mineEnrollRecyclerView.getVisibility() == View.GONE){
                    mineEnrollRecyclerView.setVisibility(View.VISIBLE);
                    mineEnrollActivityRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.mine_enroll_event_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mineEnrollActivityRecyclerView.getVisibility() == View.GONE){
                    mineEnrollActivityRecyclerView.setVisibility(View.VISIBLE);
                    mineEnrollRecyclerView.setVisibility(View.GONE);
                }
            }
        });

    }

    private void initClubData(){



        OkHttpClient mOkHttpClient1 = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId", Publicdata.uId);
        encodingBuilder.add("page","0");
        final Request request1 = new Request.Builder()
                .url("http://community.stevenming.com.cn/ca_1_jUFind")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
                .post(encodingBuilder.build())
                .build();


        Call call1 = mOkHttpClient1.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                System.out.println(s);

                try {
                    JSONObject memberJson = new JSONObject(s);
//                    JSONObject activities = memberJson.getJSONObject("activity");
//                    JSONArray club = activities.getJSONArray("list");

//                    for (int i = 0; i < members.length(); i++) {
//                        JSONObject temp = (JSONObject) members.get(i);
//                        CheckEventActivity.Student student = new CheckEventActivity.Student(temp.getString("uId"),temp.getString("uName"));
//                        studentList.add(student);
//                    }
                    JSONObject communities = memberJson.getJSONObject("communityApply");
                    JSONArray clubs = communities.getJSONArray("list");
//
                    for (int i = 0; i < clubs.length(); i++) {
                        JSONObject temp = (JSONObject) clubs.get(i);
                        int statusNum = temp.getInt("cStatue");
                        String status = "";
                        if (statusNum == 0){
                            status = "审核中";
                        }else if (statusNum == 1){
                            status = "已通过";
                        }else if (statusNum == 2){
                            status = "未通过";
                        }
                        String stars = "";
//                        for (int j = 0; j < temp.getInt("cStar"); j++) {
//                            stars += "♥";
//                        }
//                        for (int j = 0; j < 5-temp.getInt("cStar"); j++) {
//                            stars += "♡";
//                        }
                        Club club = new Club(temp.getString("cId"),stars,status);
                        clubList.add(club);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

    }

    private void initActivityData(){



        OkHttpClient mOkHttpClient1 = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId",Publicdata.uId);
        encodingBuilder.add("page","0");
        final Request request1 = new Request.Builder()
                .url("http://community.stevenming.com.cn/aa_1_jUFind")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
                .post(encodingBuilder.build())
                .build();


        Call call1 = mOkHttpClient1.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                System.out.println(s);

                try {
                    JSONObject memberJson = new JSONObject(s);
//                    JSONObject activities = memberJson.getJSONObject("activity");
//                    JSONArray club = activities.getJSONArray("list");

//                    for (int i = 0; i < members.length(); i++) {
//                        JSONObject temp = (JSONObject) members.get(i);
//                        CheckEventActivity.Student student = new CheckEventActivity.Student(temp.getString("uId"),temp.getString("uName"));
//                        studentList.add(student);
//                    }
                    JSONObject activities = memberJson.getJSONObject("activityApply");
                    JSONArray activity = activities.getJSONArray("list");
//
                    for (int i = 0; i < activity.length(); i++) {
                        JSONObject temp = (JSONObject) activity.get(i);
                        int statusNum = temp.getInt("aStatue");
                        String status = "";
                        if (statusNum == 0){
                            status = "审核中";
                        }else if (statusNum == 1){
                            status = "已通过";
                        }else if (statusNum == 2){
                            status = "未通过";
                        }
                        String stars = "";
//                        for (int j = 0; j < temp.getInt("cStar"); j++) {
//                            stars += "♥";
//                        }
//                        for (int j = 0; j < 5-temp.getInt("cStar"); j++) {
//                            stars += "♡";
//                        }
                        Club club = new Club(temp.getString("aId"),stars,status);
                        activityList.add(club);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    class Club{
        public String title;
        public String stars;
        public String status;

        Club(String title,String stars,String status){
            this.title = title;
            this.stars = stars;
            this.status = status;
        }
    }
    // 这个接口不是去给下边的适配器去实现的，而是让适配器增加一个点击事件接口数据成员，
    // 这样在new一个适配器，当你去set这个接口数据成员就需要去具体实现这个接口的方法，
    // 而适配器在bindView 的时候 给每个View增加一个点击事件，这个点击事件就是去调用接口的方法，
    // 将单个ViewHolder 的position 和View 传入这个接口中
    interface mineEnrollItemOnClick{
        public void itemOnClick(View view,int pos);
    }

    class mineEnrollRecycleAdapter extends RecyclerView.Adapter<mineEnrollRecycleAdapter.mineEnrollHolder> {

        mineEnrollItemOnClick ItemListener;

        public void setItemListener(mineEnrollItemOnClick itemListener) {
            ItemListener = itemListener;
        }

        @Override
        public mineEnrollHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mineEnrollHolder holder = new mineEnrollHolder(LayoutInflater.
                    from(MineEnrollActivity.this)
                    .inflate(R.layout.mine_enroll_item,parent,false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return clubList.size();
        }

        @Override
        public void onBindViewHolder(final mineEnrollHolder holder, int position) {
            holder.title.setText(clubList.get(position).title);
            holder.stars.setText(clubList.get(position).stars);
            holder.status.setText(clubList.get(position).status);
            if(ItemListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        ItemListener.itemOnClick(holder.itemView,pos);
                    }
                });
            }
        }


        class mineEnrollHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private TextView stars;
            private TextView status;
            private View view;

            public mineEnrollHolder(View itemView) {
                super(itemView);
                view = itemView;
                title = (TextView) itemView.findViewById(R.id.mine_enroll_item_title);
                stars = (TextView) itemView.findViewById(R.id.mine_enroll_item_stars);
                status = (TextView) itemView.findViewById(R.id.mine_enroll_item_status);

            }
        }
    }


    class mineEnrollActivityRecycleAdapter extends RecyclerView.Adapter<mineEnrollActivityRecycleAdapter.mineEnrollActivityHolder> {

        mineEnrollItemOnClick ItemListener;

        public void setItemListener(mineEnrollItemOnClick itemListener) {
            ItemListener = itemListener;
        }

        @Override
        public mineEnrollActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mineEnrollActivityHolder holder = new mineEnrollActivityHolder(LayoutInflater.
                    from(MineEnrollActivity.this)
                    .inflate(R.layout.mine_enroll_item,parent,false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return activityList.size();
        }

        @Override
        public void onBindViewHolder(final mineEnrollActivityHolder holder, int position) {
            holder.title.setText(activityList.get(position).title);
            holder.status.setText(activityList.get(position).status);
            if(ItemListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        ItemListener.itemOnClick(holder.itemView,pos);
                    }
                });
            }
        }


        class mineEnrollActivityHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private TextView status;
            private View view;

            public mineEnrollActivityHolder(View itemView) {
                super(itemView);
                view = itemView;
                title = (TextView) itemView.findViewById(R.id.mine_enroll_item_title);
                status = (TextView) itemView.findViewById(R.id.mine_enroll_item_status);

            }
        }
    }

}
