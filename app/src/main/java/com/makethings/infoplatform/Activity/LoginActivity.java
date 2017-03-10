package com.makethings.infoplatform.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.makethings.infoplatform.Publicdata;
import com.makethings.infoplatform.R;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import static com.makethings.infoplatform.Publicdata.uJoinCom;


public class LoginActivity extends AppCompatActivity implements OnClickListener {
    // 声明控件对象
    private EditText et_name, et_pass;
    private Button mLoginButton, mLoginError, mRegister;
    boolean isReLogin = false;
    private Button bt_username_clear;
    private Button bt_pwd_clear;
    private Button bt_pwd_eye;
    private TextWatcher username_watcher;
    private TextWatcher password_watcher;
    private String username, password;
    String url = "http://community.stevenming.com.cn/u_0_jLogin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //不显示系统的标题栏

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        et_name = (EditText) findViewById(R.id.username);
        et_pass = (EditText) findViewById(R.id.password);
        et_name.setText("1001");
        et_pass.setText("123456");

        bt_username_clear = (Button) findViewById(R.id.bt_username_clear);
        bt_pwd_clear = (Button) findViewById(R.id.bt_pwd_clear);
        bt_pwd_eye = (Button) findViewById(R.id.bt_pwd_eye);
        bt_username_clear.setOnClickListener(this);
        bt_pwd_clear.setOnClickListener(this);
        bt_pwd_eye.setOnClickListener(this);
        initWatcher();
        et_name.addTextChangedListener(username_watcher);
        et_pass.addTextChangedListener(password_watcher);

        mLoginButton = (Button) findViewById(R.id.login);
        mLoginError = (Button) findViewById(R.id.login_error);
        mRegister = (Button) findViewById(R.id.register);
        mLoginButton.setOnClickListener(this);
        mLoginError.setOnClickListener(this);
        mRegister.setOnClickListener(this);
    }

    /**
     * 手机号，密码输入控件公用这一个watcher
     */
    private void initWatcher() {
        username_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                et_pass.setText("");
                if (s.toString().length() > 0) {
                    bt_username_clear.setVisibility(View.VISIBLE);
                } else {
                    bt_username_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
        password_watcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    bt_pwd_clear.setVisibility(View.VISIBLE);
                } else {
                    bt_pwd_clear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }

    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        switch (arg0.getId()) {
            case R.id.login:  //登陆
                sendRequestWithOkhttp();
                //Intent login_intent=new Intent();
                //login_intent.setClass(LoginActivity.this,ModifyPersonalDataActivity.class);
                //startActivity(login_intent);
                //startActivity(WelcomeActivity.class);
                break;
            case R.id.login_error: //无法登陆(忘记密码了吧)
                //   Intent login_error_intent=new Intent();
                //   login_error_intent.setClass(LoginActivity.this, ForgetCodeActivity.class);
                //   startActivity(login_error_intent);
                break;
            case R.id.register:    //注册新的用户
                //   Intent intent=new Intent();
                //   intent.setClass(LoginActivity.this, ValidatePhoneNumActivity.class);
                //   startActivity(intent);
                break;
            case R.id.bt_username_clear:
                et_name.setText("");
                et_pass.setText("");
                break;
            case R.id.bt_pwd_clear:
                et_pass.setText("");
                break;
            case R.id.bt_pwd_eye:
                if (et_pass.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    bt_pwd_eye.setBackgroundResource(R.mipmap.shantu);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                } else {
                    bt_pwd_eye.setBackgroundResource(R.mipmap.ic_launcher);
                    et_pass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                et_pass.setSelection(et_pass.getText().toString().length());
                break;
        }
    }

    /**
     * 监听Back键按下事件,方法2:
     * 注意:
     * 返回值表示:是否能完全处理该事件
     * 在此处返回false,所以会继续传播该事件.
     * 在具体项目中此处的返回值视情况而定.
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (isReLogin) {
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                LoginActivity.this.startActivity(mHomeIntent);
            } else {
                LoginActivity.this.finish();
            }
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    private void sendRequestWithOkhttp() {
        username = et_name.getText().toString();
        password = et_pass.getText().toString();
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId", username);
        encodingBuilder.add("uPassword", password);
        final Request request = new Request.Builder()
                .url(url)
                .post(encodingBuilder.build())
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("success") == 1) {
                        Publicdata.uId = jsonObject.getString("uId");
                        Publicdata.userName = jsonObject.getString("userName");
                        Publicdata.session = jsonObject.getString("session");
                        Publicdata.userCommunity = jsonObject.getJSONObject("userCommunity");

                        String uJoinComRaw = Publicdata.userCommunity.getString("uJoinCom");
                        System.out.println("uJoinComRaw "+uJoinComRaw);
                        uJoinComRaw.replace("\\[","");
                        uJoinComRaw.replace("\\]","");
                        StringTokenizer tk1=new StringTokenizer(uJoinComRaw,",");
                        while(tk1.hasMoreTokens()){
                            Publicdata.uJoinCom.add(tk1.nextToken());
                        }

                        String uJoinActRaw = Publicdata.userCommunity.getString("uJoinAct");
                        uJoinActRaw.replace("\\[","");
                        uJoinActRaw.replace("\\]","");
                        StringTokenizer tk2=new StringTokenizer(uJoinActRaw,",");
                        while(tk2.hasMoreTokens()){
                            Publicdata.uJoinAct.add(tk2.nextToken());
                        }


                        String uCollectComRaw = Publicdata.userCommunity.getString("uCollectCom");
                        uCollectComRaw.replace("\\[","");
                        uCollectComRaw.replace("\\]","");
                        StringTokenizer tk3=new StringTokenizer(uCollectComRaw,",");
                        while(tk3.hasMoreTokens()){
                            Publicdata.uCollectCom.add(tk3.nextToken());
                        }
                        String uCollectActRaw = Publicdata.userCommunity.getString("uCollectAct");
                        uCollectActRaw.replace("\\[","");
                        uCollectActRaw.replace("\\]","");
                        StringTokenizer tk4=new StringTokenizer(uCollectActRaw,",");
                        while(tk4.hasMoreTokens()){
                            Publicdata.uCollectAct.add(tk4.nextToken());
                        }


                        Publicdata.uCommBoss = Publicdata.userCommunity.getString("uCommBoss");
                        Publicdata.uCommAdmin = Publicdata.userCommunity.getString("uCommAdmin");
                        Publicdata.communityName = jsonObject.getJSONObject("communityName");
                        Publicdata.activityName = jsonObject.getJSONObject("activityName");
                        Intent login_intent = new Intent();
                        login_intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(login_intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "登陆失败，请输入正确的账号密码", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(s);
            }
        });
    }
}
