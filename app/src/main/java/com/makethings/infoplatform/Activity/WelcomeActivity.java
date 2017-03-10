package com.makethings.infoplatform.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.makethings.infoplatform.Activity.LoginActivity;
import com.makethings.infoplatform.R;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler();
        handler.postDelayed(new Loading(), 1000);
    }
    class Loading implements Runnable{
        @Override
        public void run() {
// TODO Auto-generated method stub
            startActivity(new Intent(getApplication(),LoginActivity.class));
            WelcomeActivity.this.finish();
        }
    }
}
