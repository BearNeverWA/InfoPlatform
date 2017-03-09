package com.makethings.infoplatform.Activity;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.makethings.infoplatform.Adapter.MainFragmentPageAdapter;
import com.makethings.infoplatform.Fragment.FragmentClub;
import com.makethings.infoplatform.Fragment.FragmentEvents;
import com.makethings.infoplatform.Fragment.FragmentPersonal;
import com.makethings.infoplatform.R;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {
    ViewPager pager;
    RadioGroup radioGroup;
    RadioButton rbEvents, rbClub, rbPersonal;
    ArrayList<Fragment> fragmentArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
    }

    private void initView() {
        pager = (ViewPager) findViewById(R.id.view_pager);
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        rbEvents = (RadioButton) findViewById(R.id.rb_event);
        rbClub = (RadioButton) findViewById(R.id.rb_club);
        rbPersonal = (RadioButton) findViewById(R.id.rb_personal);
        rbEvents.setChecked(true);
        rbEvents.setTextColor(Color.BLUE);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_event:
                        pager.setCurrentItem(0, false);
                        rbEvents.setTextColor(Color.BLUE);
                        rbClub.setTextColor(Color.BLACK);
                        rbPersonal.setTextColor(Color.BLACK);
                        break;
                    case R.id.rb_club:
                        pager.setCurrentItem(1, false);
                        rbClub.setTextColor(Color.BLUE);
                        rbEvents.setTextColor(Color.BLACK);
                        rbPersonal.setTextColor(Color.BLACK);
                        break;
                    case R.id.rb_personal:
                        pager.setCurrentItem(2, false);
                        rbPersonal.setTextColor(Color.BLUE);
                        rbClub.setTextColor(Color.BLACK);
                        rbEvents.setTextColor(Color.BLACK);
                        break;
                }
            }
        });
    }

    private void initViewPager() {
        FragmentEvents events = new FragmentEvents();
        FragmentClub club = new FragmentClub();
        FragmentPersonal personal = new FragmentPersonal();
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(events);
        fragmentArrayList.add(club);
        fragmentArrayList.add(personal);
        pager.setAdapter(new MainFragmentPageAdapter(getSupportFragmentManager(), fragmentArrayList));
        pager.setCurrentItem(0);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        radioGroup.check(R.id.rb_event);
                        rbEvents.setTextColor(Color.BLUE);
                        rbClub.setTextColor(Color.BLACK);
                        rbPersonal.setTextColor(Color.BLACK);
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_club);
                        rbClub.setTextColor(Color.BLUE);
                        rbEvents.setTextColor(Color.BLACK);
                        rbPersonal.setTextColor(Color.BLACK);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_personal);
                        rbPersonal.setTextColor(Color.BLUE);
                        rbClub.setTextColor(Color.BLACK);
                        rbEvents.setTextColor(Color.BLACK);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
