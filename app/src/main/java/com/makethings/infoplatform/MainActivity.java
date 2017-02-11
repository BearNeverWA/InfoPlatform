package com.makethings.infoplatform;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button button9,button13,button14;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button9= (Button) findViewById(R.id.btn_test9);
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MineClubActivity.class);
                startActivity(intent);
            }
        });
        button13= (Button) findViewById(R.id.btn_test13);
        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ModifyClubDataActivity.class);
                startActivity(intent);
            }
        });
        button14= (Button) findViewById(R.id.btn_test14);
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MineClubMembersActivity.class);
                startActivity(intent);
            }
        });
    }
}
