package com.makethings.infoplatform.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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

public class IssueEventsActivity extends AppCompatActivity {

    private String cId;
    private String aId;

    private String eventsName;
    private String eventsStartTime;
    private String eventsEndTime;
    private String eventsStopRegTime;

    private String eventsPlace;

    private int eventsCapacity;

    private int eventsIsVisible;

    private String eventsIntroduction;

    private EditText etEventsName;
    private EditText etEventsStartTime;
    private EditText etEventsEndTime;
    private EditText etEventsStopRegistTime;

    private RadioButton rbYes;
    private RadioButton rbNo;

    private EditText etEventsPlace;
    private EditText etEventsCapacity;

    private EditText etEventsIntroduction;
    private Button btUploadEventsPic;

    private Button btConfirmIssue;

    private boolean syncFlag;
    private JSONObject activity;

    private int collectRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_events);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();

        aId = intent.getStringExtra("aId");
        cId = intent.getStringExtra("cId");

        if (aId == null) {
            actionBar.setTitle("发布活动");
        } else {
            actionBar.setTitle("修改活动");
        }

        etEventsName = (EditText) findViewById(R.id.etEventsName);
        etEventsStartTime = (EditText) findViewById(R.id.etEventsStartTime);
        etEventsEndTime = (EditText) findViewById(R.id.etEventsEndTime);
        etEventsStopRegistTime = (EditText) findViewById(R.id.etEventsStopRegistTime);

        etEventsPlace = (EditText) findViewById(R.id.etEventsPlace);
        etEventsCapacity = (EditText) findViewById(R.id.etEventsCapacity);
        etEventsIntroduction = (EditText) findViewById(R.id.etEventsIntroduction);

        rbYes = (RadioButton) findViewById(R.id.radioButtonYes);
        rbNo = (RadioButton) findViewById(R.id.radioButtonNo);

        btUploadEventsPic = (Button) findViewById(R.id.btUploadEventsPic);
        btConfirmIssue = (Button) findViewById(R.id.btConfirmIssue);


        btUploadEventsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btConfirmIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                syncFlag = false;
                try {
                    eventsName = etEventsName.getText().toString();
                    eventsStartTime = etEventsStartTime.getText().toString();
                    eventsEndTime = etEventsEndTime.getText().toString();
                    eventsStopRegTime = etEventsStopRegistTime.getText().toString();
                    eventsPlace = etEventsPlace.getText().toString();
                    eventsCapacity = Integer.parseInt(etEventsCapacity.getText().toString());
                    eventsIntroduction = etEventsIntroduction.getText().toString();

                    OkHttpClient mOkHttpClient = new OkHttpClient();
                    FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
                    encodingBuilder.add("aName", eventsName);
                    encodingBuilder.add("cId", cId);
                    encodingBuilder.add("aIntroduction", eventsIntroduction);
                    encodingBuilder.add("aLocation", eventsPlace);
                    encodingBuilder.add("aMaxPeople", String.valueOf(eventsCapacity));
                    encodingBuilder.add("aStartTime", eventsStartTime);
                    encodingBuilder.add("aEndTime", eventsEndTime);
                    encodingBuilder.add("aStopRegistTime", eventsStopRegTime);
                    encodingBuilder.add("aVisible", String.valueOf(eventsIsVisible));

                    if (aId == null) {
                        final Request request = new Request.Builder()
                                .url("http://community.stevenming.com.cn/a_2_jRelease")
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
                                    JSONObject jsonObject = new JSONObject(s);
                                    System.out.println(jsonObject);
                                    collectRes = jsonObject.getInt("success");
                                    syncFlag = true;
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }
                        });
                    } else {

                        encodingBuilder.add("aId", aId);
                        final Request request = new Request.Builder()
                                .url("http://community.stevenming.com.cn/a_2_jUpdateAct")
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
                                    JSONObject jsonObject = new JSONObject(s);
                                    System.out.println(jsonObject);
                                    collectRes = jsonObject.getInt("success");
                                    syncFlag = true;
                                } catch (JSONException e) {

                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    while (!syncFlag) {
                    }

                    if (collectRes == 1)
                        Toast.makeText(view.getContext(), "发布活动成功", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(view.getContext(), "发布活动失败", Toast.LENGTH_SHORT).show();

                } catch (NumberFormatException e) {
                    Toast.makeText(view.getContext(), "输入内容格式有误，请重新输入", Toast.LENGTH_SHORT).show();
                }

            }
        });


        if (aId != null) {
            syncFlag = false;
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
                        JSONObject jRes = new JSONObject(s);
                        activity = jRes.getJSONObject("activity");
                        System.out.println(activity);

                        syncFlag = true;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });

            while (!syncFlag) {
            }
            try {
                etEventsName.setText(activity.getString("aName"));
                etEventsStartTime.setText(activity.getString("aStartTime").replace("T", " "));
                etEventsEndTime.setText(activity.getString("aEndTime").replace("T", " "));
                etEventsStopRegistTime.setText(activity.getString("aStopRegistTime").replace("T", " "));
                etEventsPlace.setText(activity.getString("aLocation"));
                etEventsCapacity.setText(activity.getString("aMaxPeople"));
                etEventsIntroduction.setText(activity.getString("aIntroduction"));

                if (activity.getString("aVisible").equals("1")) {
                    rbYes.performClick();
                    //rbYes.setChecked(true);
                } else {
                    rbNo.performClick();
                    //rbNo.setChecked(true);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void onRadioClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.radioButtonYes:
                if (checked) {
                    eventsIsVisible = 1;
                }
                break;
            case R.id.radioButtonNo:
                if (checked) {
                    eventsIsVisible = 0;
                }
                break;
        }
    }
}
