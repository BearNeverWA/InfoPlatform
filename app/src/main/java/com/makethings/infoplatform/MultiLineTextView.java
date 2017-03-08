package com.makethings.infoplatform;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by bhL on 2017/2/4.
 */

public class MultiLineTextView extends TextView {

    Context context;

    public MultiLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public MultiLineTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MultiLineTextView(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Layout layout = getLayout();
        if (layout != null) {
            int height = (int) Math.ceil(getMaxLineHeight(this.getText().toString()))
                    + getCompoundPaddingTop() + getCompoundPaddingBottom();
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height);
        }
    }

    private float getMaxLineHeight(String str) {
        float height;
        float screenW = ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        float paddingLeft = ((LinearLayout) this.getParent()).getPaddingLeft();
        float paddingRight = ((LinearLayout) this.getParent()).getPaddingRight();
        int line = (int) Math.ceil((this.getPaint().measureText(str) / (screenW - paddingLeft - paddingRight)));
        height = (this.getPaint().getFontMetrics().descent - this.getPaint().getFontMetrics().ascent) * line;
        return height;
    }
}