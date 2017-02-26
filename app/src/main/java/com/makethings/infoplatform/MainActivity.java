package com.makethings.infoplatform;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_event:
                        pager.setCurrentItem(0, false);
                        break;
                    case R.id.rb_club:
                        pager.setCurrentItem(1, false);
                        break;
                    case R.id.rb_personal:
                        pager.setCurrentItem(2, false);
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
                        break;
                    case 1:
                        radioGroup.check(R.id.rb_club);
                        break;
                    case 2:
                        radioGroup.check(R.id.rb_personal);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
