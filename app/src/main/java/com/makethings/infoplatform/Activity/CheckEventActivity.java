package com.makethings.infoplatform.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class CheckEventActivity extends AppCompatActivity {
    private RecyclerView checkEventRecyclerView;
    private List<Student> studentList = new ArrayList();
    private CheckEventRecycleAdapter adapter;
    private String aId;

    private TextView eventNameText;
    private TextView eventSummaryText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_events);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("活动审核");

        eventNameText = (TextView) findViewById(R.id.check_event_name);
        eventSummaryText = (TextView) findViewById(R.id.check_event_introduction);

        Intent intent = getIntent();
        aId = intent.getStringExtra("aId");

        eventNameText.setText(intent.getStringExtra("aName"));
        eventSummaryText.setText(intent.getStringExtra("aSummary"));

        initStudentData();
        checkEventRecyclerView = (RecyclerView) findViewById(R.id.check_event_recyclerView);
        checkEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CheckEventRecycleAdapter();

        checkEventRecyclerView.setAdapter(adapter);

    }

    private void initStudentData(){

        OkHttpClient mOkHttpClient1 = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("aId",aId);
        encodingBuilder.add("uId", Publicdata.uId);
        encodingBuilder.add("page","0");
        final Request request1 = new Request.Builder()
                .url("http://community.stevenming.com.cn/aa_2_jActFind")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
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
                    JSONObject activityPeople = memberJson.getJSONObject("activityApply");
                    JSONArray members = activityPeople.getJSONArray("list");

                    for (int i = 0; i < members.length(); i++) {
                        JSONObject temp = (JSONObject) members.get(i);
                        if (temp.getInt("aStatue") == 0){
                            Student student = new Student(temp.getString("uId"),temp.getString("uName"));
                            studentList.add(student);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });






    }

    class Student{
        public String studentNum;
        public String studentName;

        Student(String studentNum,String studentName){
            this.studentNum = studentNum;
            this.studentName = studentName;
        }
    }
    // 这个接口不是去给下边的适配器去实现的，而是让适配器增加一个点击事件接口数据成员，
    // 这样在new一个适配器，当你去set这个接口数据成员就需要去具体实现这个接口的方法，
    // 而适配器在bindView 的时候 给每个View增加一个点击事件，这个点击事件就是去调用接口的方法，
    // 将单个ViewHolder 的position 和View 传入这个接口中

    class CheckEventRecycleAdapter extends RecyclerView.Adapter<CheckEventRecycleAdapter.CheckEventHolder> {


        @Override
        public CheckEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CheckEventHolder holder = new CheckEventHolder(LayoutInflater.
                    from(CheckEventActivity.this)
                    .inflate(R.layout.check_event_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final CheckEventHolder holder, final int position) {
            holder.studentNum.setText(studentList.get(position).studentNum);
            holder.studentName.setText(studentList.get(position).studentName);

            holder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OkHttpClient mOkHttpClient1 = new OkHttpClient();
                    FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                    encodingBuilder.add("uId",Publicdata.uId);
                    encodingBuilder.add("aId","1");
                    encodingBuilder.add("id",studentList.get(position).studentNum);
                    encodingBuilder.add("value","1");
                    final Request request1 = new Request.Builder()
                            .url("http://community.stevenming.com.cn/aa_2_jApplyUpdate")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
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
                                JSONObject json = new JSONObject(s);
                                if (json.getInt("success") == 1){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            holder.accept.setEnabled(false);
                                            holder.accept.setBackgroundColor(getResources().getColor(R.color.grey));

                                            holder.refuse.setEnabled(false);
                                            holder.refuse.setBackgroundColor(getResources().getColor(R.color.grey));
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }
            });
//


            holder.refuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OkHttpClient mOkHttpClient1 = new OkHttpClient();
                    FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                    encodingBuilder.add("uId",Publicdata.uId);
                    encodingBuilder.add("aId","1");
                    encodingBuilder.add("id",studentList.get(position).studentNum);
                    encodingBuilder.add("value","2");
                    final Request request1 = new Request.Builder()
                            .url("http://community.stevenming.com.cn/aa_2_jApplyUpdate")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
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
                                JSONObject json = new JSONObject(s);
                                if (json.getInt("success") == 1){
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            holder.accept.setEnabled(false);
                                            holder.accept.setBackgroundColor(getResources().getColor(R.color.grey));

                                            holder.refuse.setEnabled(false);
                                            holder.refuse.setBackgroundColor(getResources().getColor(R.color.grey));
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }
            });
//


        }

        @Override
        public int getItemCount() {
            return studentList.size();
        }


        class CheckEventHolder extends RecyclerView.ViewHolder{
            private TextView studentNum;
            private TextView studentName;
            private Button accept;
            private Button refuse;
            private View view;

            CheckEventHolder(View itemView) {
                super(itemView);
                view = itemView;
                studentNum = (TextView) itemView.findViewById(R.id.check_event_item_studentNum);
                studentName = (TextView) itemView.findViewById(R.id.check_event_item_studentName);
                accept = (Button) itemView.findViewById(R.id.check_event_item_accept_btn);
                refuse = (Button) itemView.findViewById(R.id.check_event_item_refuse_btn);
            }
        }
    }
}
