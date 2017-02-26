package com.makethings.infoplatform;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;

import java.lang.reflect.Field;

/**
 * Created by Call Me Bear on 2017/2/26.
 */

public class CommonUtils {
    private static FixedSpeedScroller mScroller=null;
    public static void controlViewPagerSpeed(Context context, ViewPager viewPager,int DurationSwitch){
        try {
            Field mField;
            mField=ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            mScroller=new FixedSpeedScroller(context,new AccelerateInterpolator());
            mScroller.setmDuration(DurationSwitch);
            mField.set(viewPager,mScroller);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
