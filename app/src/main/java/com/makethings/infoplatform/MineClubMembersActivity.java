package com.makethings.infoplatform;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class MineClubMembersActivity extends AppCompatActivity {
    ListView listMineClubMember;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_club_members);
        listMineClubMember= (ListView) findViewById(R.id.lv_mine_club_member);

    }
}
