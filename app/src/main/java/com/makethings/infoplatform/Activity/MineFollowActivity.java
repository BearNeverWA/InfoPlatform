
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

public class MineFollowActivity extends AppCompatActivity {
    private RecyclerView mineFollowClubRecyclerView;
    private List<MineFollowActivity.Club> clubList = new ArrayList();
    private mineFollowRecycleAdapter ClubAdapter;


    private RecyclerView mineFollowActivityRecyclerView;
    private List<MineFollowActivity.Club> activityList = new ArrayList();
    private mineFollowActivityRecycleAdapter ActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_follow);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的关注");
        initClubData();
        initActivityData();
        mineFollowClubRecyclerView = (RecyclerView) findViewById(R.id.mine_follow_club_recycler_view);
        mineFollowClubRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ClubAdapter = new mineFollowRecycleAdapter();
        ClubAdapter.setItemListener(new mineFollowItemOnClick() {
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
        mineFollowClubRecyclerView.setAdapter(ClubAdapter);


        // 配置Activity的适配器
        mineFollowActivityRecyclerView = (RecyclerView) findViewById(R.id.mine_follow_activity_recycler_view);
        mineFollowActivityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActivityAdapter = new mineFollowActivityRecycleAdapter();
        ActivityAdapter.setItemListener(new mineFollowItemOnClick() {
            @Override
            public void itemOnClick(final View view, int pos) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(),"Click Activity ",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mineFollowActivityRecyclerView.setAdapter(ActivityAdapter);


        // 设置两个RecycleView 的显示情况
        mineFollowClubRecyclerView.setVisibility(View.VISIBLE);
        mineFollowActivityRecyclerView.setVisibility(View.GONE);
        // 设置两个按钮进行RecycleView切换
        findViewById(R.id.mine_follow_club_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mineFollowClubRecyclerView.getVisibility() == View.GONE){
                    mineFollowClubRecyclerView.setVisibility(View.VISIBLE);
                    mineFollowActivityRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.mine_follow_event_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mineFollowActivityRecyclerView.getVisibility() == View.GONE){
                    mineFollowClubRecyclerView.setVisibility(View.GONE);
                    mineFollowActivityRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void initClubData(){

        OkHttpClient mOkHttpClient1 = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId", Publicdata.uId);
        encodingBuilder.add("page","1");
        final Request request1 = new Request.Builder()
                .url("http://community.stevenming.com.cn/c_1_jCollComList")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
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
                    JSONObject CollCom = memberJson.getJSONObject("CollCom");
                    JSONArray clubs = CollCom.getJSONArray("list");
//
                    for (int i = 0; i < clubs.length(); i++) {
                        JSONObject temp = (JSONObject) clubs.get(i);
                        String details = temp.getString("cIntroduction");
                        if (details == "null"){
                            details = "该社团暂未填写说明";
                        }
                        String stars = "";
                        for (int j = 0; j < temp.getInt("cStar"); j++) {
                            stars += "♥";
                        }
                        for (int j = 0; j < 5-temp.getInt("cStar"); j++) {
                            stars += "♡";
                        }
                        Club club = new Club(temp.getString("cName") + 1,stars,details);
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
        FormEncodingBuilder encodingBuilder1 = new FormEncodingBuilder();
        encodingBuilder1.add("uId",Publicdata.uId);
        encodingBuilder1.add("page","1");
        final Request request1 = new Request.Builder()
                .url("http://community.stevenming.com.cn/a_1_jCollActList")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
                .post(encodingBuilder1.build())
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
                    JSONObject activity = memberJson.getJSONObject("activity");
                    JSONArray activities = activity.getJSONArray("list");
//
                    for (int i = 0; i < activities.length(); i++) {
                        JSONObject temp = (JSONObject) activities.get(i);
                        String details = temp.getString("aIntroduction");
                        if (details == "null"){
                            details = "该活动暂未填写说明";
                        }
                        String stars = "";

                        Club club = new Club(temp.getString("aName"),stars,details);
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
        public String details;

        Club(String title,String stars,String details){
            this.title = title;
            this.stars = stars;
            this.details = details;
        }
    }

//    class Activity{
//                public String title;
//        public String details;
//
//        Activity(String title,String details){
//            this.title = title;
//            this.details = details;
//        }
//    }

    // 这个接口不是去给下边的适配器去实现的，而是让适配器增加一个点击事件接口数据成员，
    // 这样在new一个适配器，当你去set这个接口数据成员就需要去具体实现这个接口的方法，
    // 而适配器在bindView 的时候 给每个View增加一个点击事件，这个点击事件就是去调用接口的方法，
    // 将单个ViewHolder 的position 和View 传入这个接口中
    interface mineFollowItemOnClick{
        public void itemOnClick(View view,int pos);
    }

    class mineFollowRecycleAdapter extends RecyclerView.Adapter<mineFollowRecycleAdapter.mineFollowHolder> {

        mineFollowItemOnClick ItemListener;

        public void setItemListener(mineFollowItemOnClick itemListener) {
            ItemListener = itemListener;
        }

        @Override
        public mineFollowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mineFollowHolder holder = new mineFollowHolder(LayoutInflater.
                    from(MineFollowActivity.this)
                    .inflate(R.layout.mine_follow_item,parent,false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return clubList.size();
        }

        @Override
        public void onBindViewHolder(final mineFollowHolder holder, int position) {
            holder.title.setText(clubList.get(position).title);
            holder.stars.setText(clubList.get(position).stars);
            holder.details.setText(clubList.get(position).details);
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


        class mineFollowHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private TextView stars;
            private TextView details;
            private View view;

            public mineFollowHolder(View itemView) {
                super(itemView);
                view = itemView;
                title = (TextView) itemView.findViewById(R.id.mine_follow_item_title);
                stars = (TextView) itemView.findViewById(R.id.mine_follow_item_stars);
                details = (TextView) itemView.findViewById(R.id.mine_follow_item_details);

            }
        }
    }







    // 接下来是活动页面的RecycleView
//
//    // 这个接口不是去给下边的适配器去实现的，而是让适配器增加一个点击事件接口数据成员，
//    // 这样在new一个适配器，当你去set这个接口数据成员就需要去具体实现这个接口的方法，
//    // 而适配器在bindView 的时候 给每个View增加一个点击事件，这个点击事件就是去调用接口的方法，
//    // 将单个ViewHolder 的position 和View 传入这个接口中
//    interface mineFollowItemOnClick{
//        public void itemOnClick(View view,int pos);
//    }

    class mineFollowActivityRecycleAdapter extends RecyclerView.Adapter<mineFollowActivityRecycleAdapter.mineFollowActivityHolder> {

        mineFollowItemOnClick ItemListener;

        public void setItemListener(mineFollowItemOnClick itemListener) {
            ItemListener = itemListener;
        }

        @Override
        public mineFollowActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mineFollowActivityHolder holder = new mineFollowActivityHolder(LayoutInflater.
                    from(MineFollowActivity.this)
                    .inflate(R.layout.mine_follow_item,parent,false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return activityList.size();
        }

        @Override
        public void onBindViewHolder(final mineFollowActivityHolder holder, int position) {
            holder.title.setText(activityList.get(position).title);
            holder.details.setText(activityList.get(position).details);
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


        class mineFollowActivityHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private TextView details;
            private View view;

            public mineFollowActivityHolder(View itemView) {
                super(itemView);
                view = itemView;
                title = (TextView) itemView.findViewById(R.id.mine_follow_item_title);
                details = (TextView) itemView.findViewById(R.id.mine_follow_item_details);

            }
        }
    }

}
