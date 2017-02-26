package com.makethings.infoplatform;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;


/**
 * Created by Call Me Bear on 2017/2/26.
 */

//控制滑动速度工具类
public class FixedSpeedScroller extends Scroller {

    private int mDuration = 1500;

    public FixedSpeedScroller(Context context) {
        super(context);
    }

    public FixedSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy);
    }

    public void setmDuration(int time) {
        mDuration = time;
    }


    public int getmDuration() {
        return mDuration;
    }
}
