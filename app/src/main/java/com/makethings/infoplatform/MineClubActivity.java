package com.makethings.infoplatform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;

public class MineClubActivity extends AppCompatActivity {

    private String[] data={"1","2","3","4","5","6","7","8","9","10","11","12","13","14"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_club);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MineClubActivity.this,android.R.layout.simple_list_item_1,data);
        ListView listJoin= (ListView) findViewById(R.id.mine_club_join_list);
        ListView listManager= (ListView) findViewById(R.id.mine_club_manager_list);
        listJoin.setAdapter(adapter);
        listManager.setAdapter(adapter);

        //初始化TabHost
        TabHost tabHost= (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec("tab_join").setIndicator("我加入的社团").setContent(R.id.tab_mine_club_join));
        tabHost.addTab(tabHost.newTabSpec("tab_manager").setIndicator("我管理的社团").setContent(R.id.tab_mine_club_manager));
    }
}
