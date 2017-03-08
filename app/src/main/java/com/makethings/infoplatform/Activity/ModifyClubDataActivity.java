package com.makethings.infoplatform.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.makethings.infoplatform.R;

import java.util.ArrayList;
import java.util.List;

public class ModifyClubDataActivity extends AppCompatActivity {
    private Spinner spinner;
    private EditText etClubIntroduction;
    private List<String> spinner_list;
    private ArrayAdapter<String> adapter;
    private TextView tvClubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_club_data);
        etClubIntroduction = (EditText) findViewById(R.id.et_club_introduction);
        spinner= (Spinner) findViewById(R.id.spinner_club_type);
        tvClubName= (TextView) findViewById(R.id.tv_mine_club_name);

        spinner_list=new ArrayList<String>();
        spinner_list.add("文艺");
        spinner_list.add("体育");
        spinner_list.add("学习");
        spinner_list.add("4");
        spinner_list.add("5");
        spinner_list.add("6");

        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,spinner_list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
