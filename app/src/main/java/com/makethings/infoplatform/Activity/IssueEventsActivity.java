package com.makethings.infoplatform.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.makethings.infoplatform.R;

public class IssueEventsActivity extends AppCompatActivity {

    private int clubID;
    private int eventsID;

    private String eventsName;
    private String eventsDate;
    private String eventsPlace;
    private int eventsCapacity;
    private boolean eventsIsOpenToPublic;
    private String eventsBriefInfo;
    private String eventsIntroduction;

    private EditText etEventsName;
    private EditText etEventsDate;
    private EditText etEventsPlace;
    private EditText etEventsCapacity;
    private EditText etEventsBriefInfo;
    private EditText etEventsIntroduction;
    private Button btUploadEventsPic;

    private Button btClearEventsInfoInput;
    private Button btConfirmIssue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_events);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("发布活动");

        etEventsName = (EditText) findViewById(R.id.etEventsName);
        etEventsDate = (EditText) findViewById(R.id.etEventsDate);
        etEventsPlace = (EditText) findViewById(R.id.etEventsPlace);
        etEventsCapacity = (EditText) findViewById(R.id.etEventsCapacity);
        etEventsBriefInfo = (EditText) findViewById(R.id.etEventsBriefInfo);
        etEventsIntroduction = (EditText) findViewById(R.id.etEventsIntroduction);
        btUploadEventsPic = (Button) findViewById(R.id.btUploadEventsPic);
        btClearEventsInfoInput = (Button) findViewById(R.id.btClearEventsInfoInput);
        btConfirmIssue = (Button) findViewById(R.id.btConfirmIssue);

        btUploadEventsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btClearEventsInfoInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btConfirmIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventsName = etEventsName.getText().toString();
                eventsDate = etEventsDate.getText().toString();
                eventsPlace = etEventsPlace.getText().toString();
                eventsCapacity = Integer.parseInt(etEventsCapacity.getText().toString());
                eventsBriefInfo = etEventsBriefInfo.getText().toString();
                eventsIntroduction = etEventsIntroduction.getText().toString();
            }
        });
    }

    public void onRadioClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        switch (v.getId()) {
            case R.id.radioButtonYes:
                if (checked) {
                    eventsIsOpenToPublic = true;
                }
                break;
            case R.id.radioButtonNo:
                if (checked) {
                    eventsIsOpenToPublic = false;
                }
                break;
        }
    }
}
