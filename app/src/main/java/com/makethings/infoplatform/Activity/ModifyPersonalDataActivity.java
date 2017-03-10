package com.makethings.infoplatform.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class ModifyPersonalDataActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_age,bt_sex,bt_phone,bt_email,bt_qq,bt_address,bt_interest,bt_confirm;
    EditText et_id,et_age,et_sex,et_phone,et_email,et_qq,et_address,et_interest,et_department;
    boolean flag_age,flag_phone,flag_email,flag_qq,flag_address,flag_interest;
    private String uId= Publicdata.uId;
    private String url_get="http://community.stevenming.com.cn/ub_1_jShow",url_post="http://community.stevenming.com.cn/ub_1_jUpdate";
    private String[] sexArry = new String[] { "男", "女" ,"保密"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_personal_data);
        bt_age=(Button) findViewById(R.id.bt_age);
        bt_age.setOnClickListener(this);
        bt_sex=(Button) findViewById(R.id.bt_sex);
        bt_sex.setOnClickListener(this);
        bt_phone=(Button)findViewById(R.id.bt_phone);
        bt_phone.setOnClickListener(this);
        bt_email=(Button)findViewById(R.id.bt_email);
        bt_email.setOnClickListener(this);
        bt_qq=(Button)findViewById(R.id.bt_qq);
        bt_qq.setOnClickListener(this);
        bt_address=(Button)findViewById(R.id.bt_address);
        bt_address.setOnClickListener(this);
        bt_interest=(Button)findViewById(R.id.bt_interest);
        bt_interest.setOnClickListener(this);
        bt_confirm=(Button)findViewById(R.id.bt_confirm);
        bt_confirm.setOnClickListener(this);

        et_id=(EditText)findViewById(R.id.et_account);
        et_age=(EditText)findViewById(R.id.et_age);
        et_sex = (EditText)findViewById(R.id.et_sex);
        et_phone=(EditText)findViewById(R.id.et_phone);
        et_email=(EditText)findViewById(R.id.et_email);
        et_qq=(EditText)findViewById(R.id.et_qq);
        et_address=(EditText)findViewById(R.id.et_address);
        et_interest=(EditText) findViewById(R.id.et_interest);
        et_department=(EditText)findViewById(R.id.et_department);
        sendRequestWithOkhttp();
        init();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_age:
                if(flag_age==false){
                    et_age.setFocusableInTouchMode(true);
                    et_age.setCursorVisible(true);
                    et_age.setFocusable(true);
                    bt_age.setBackgroundResource(R.mipmap.shantu);
                    flag_age=true;
                }else{
                    et_age.setFocusableInTouchMode(false);
                    et_age.setCursorVisible(false);
                    et_age.setFocusable(false);
                    bt_age.setBackgroundResource(R.mipmap.ic_launcher);
                    flag_age=false;
                }
                break;
            case R.id.bt_sex:
                if(et_sex.getText().toString().equals(sexArry[0])){
                    showSexChooseDialog(0);
                }
                else if(et_sex.getText().toString().equals(sexArry[1]))
                {
                    showSexChooseDialog(1);
                }
                else if(et_sex.getText().toString().equals(sexArry[2])){
                    showSexChooseDialog(2);
                }
                else{
                    showSexChooseDialog(-1);
                }
                break;
            case R.id.bt_phone:
                if(flag_phone==false){
                    et_phone.setFocusableInTouchMode(true);
                    et_phone.setCursorVisible(true);
                    et_phone.setFocusable(true);
                    bt_phone.setBackgroundResource(R.mipmap.shantu);
                    flag_phone=true;
                }else{
                    et_phone.setFocusableInTouchMode(false);
                    et_phone.setCursorVisible(false);
                    et_phone.setFocusable(false);
                    bt_phone.setBackgroundResource(R.mipmap.ic_launcher);
                    flag_phone=false;
                }
                break;
            case R.id.bt_email:
                if(flag_email==false){
                    et_email.setFocusableInTouchMode(true);
                    et_email.setCursorVisible(true);
                    et_email.setFocusable(true);
                    bt_email.setBackgroundResource(R.mipmap.shantu);
                    flag_email=true;
                }else{
                    et_email.setFocusableInTouchMode(false);
                    et_email.setCursorVisible(false);
                    et_email.setFocusable(false);
                    bt_email.setBackgroundResource(R.mipmap.ic_launcher);
                    flag_email=false;
                }
                break;
            case R.id.bt_qq:
                if(flag_qq==false){
                    et_qq.setFocusableInTouchMode(true);
                    et_qq.setCursorVisible(true);
                    et_qq.setFocusable(true);
                    bt_qq.setBackgroundResource(R.mipmap.shantu);
                    flag_qq=true;
                }else{
                    et_qq.setFocusableInTouchMode(false);
                    et_qq.setCursorVisible(false);
                    et_qq.setFocusable(false);
                    bt_qq.setBackgroundResource(R.mipmap.ic_launcher);
                    flag_qq=false;
                }
                break;
            case R.id.bt_address:
                if(flag_address==false){
                    et_address.setFocusableInTouchMode(true);
                    et_address.setCursorVisible(true);
                    et_address.setFocusable(true);
                    bt_address.setBackgroundResource(R.mipmap.shantu);
                    flag_address=true;
                }else{
                    et_address.setFocusableInTouchMode(false);
                    et_address.setCursorVisible(false);
                    et_address.setFocusable(false);
                    bt_address.setBackgroundResource(R.mipmap.ic_launcher);
                    flag_address=false;
                }
                break;
            case R.id.bt_interest:
                if(flag_interest==false){
                    et_interest.setFocusableInTouchMode(true);
                    et_interest.setCursorVisible(true);
                    et_interest.setFocusable(true);
                    bt_interest.setBackgroundResource(R.mipmap.shantu);
                    flag_interest=true;
                }else{
                    et_interest.setFocusableInTouchMode(false);
                    et_interest.setCursorVisible(false);
                    et_interest.setFocusable(false);
                    bt_interest.setBackgroundResource(R.mipmap.ic_launcher);
                    flag_interest=false;
                }
                break;
            case R.id.bt_confirm:
                postData();
                //Intent ModifyEvents_intent=new Intent();
                //ModifyEvents_intent.setClass(ModifyPersonalDataActivity.this);
                //startActivity(ModifyEvents_intent);
        }
    }

    public void init(){
        et_age.setFocusableInTouchMode(false);
        et_age.setCursorVisible(false);
        et_age.setFocusable(false);
        flag_age=false;
        et_phone.setFocusableInTouchMode(false);
        et_phone.setCursorVisible(false);
        et_phone.setFocusable(false);
        flag_phone=false;
        et_email.setFocusableInTouchMode(false);
        et_email.setCursorVisible(false);
        et_email.setFocusable(false);
        flag_email=false;
        et_qq.setFocusableInTouchMode(false);
        et_qq.setCursorVisible(false);
        et_qq.setFocusable(false);
        flag_qq=false;
        et_address.setFocusableInTouchMode(false);
        et_address.setCursorVisible(false);
        et_address.setFocusable(false);
        flag_address=false;
        et_interest.setFocusableInTouchMode(false);
        et_interest.setCursorVisible(false);
        et_interest.setFocusable(false);
        flag_interest=false;
    }

    private void showSexChooseDialog(int sex_number) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框

        builder.setSingleChoiceItems(sexArry, sex_number, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                et_sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }

    private void sendRequestWithOkhttp(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId",uId);
        final Request request = new Request.Builder()
                .url(url_get)
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
                    JSONObject jsonObject=new JSONObject(s);
                    System.out.println(s);
                    if(jsonObject.getInt("success")==1){
                        JSONObject userBasic =jsonObject.getJSONObject("userBasic");
                        Publicdata.userBasic=userBasic;
                        Publicdata.uGender=userBasic.getString("uGender");
                        Publicdata.uAge=userBasic.getString("uAge");
                        Publicdata.uPhone=userBasic.getString("uPhone");
                        Publicdata.uAddress=userBasic.getString("uAddress");
                        Publicdata.uEmail=userBasic.getString("uEmail");
                        Publicdata.uQq=userBasic.getString("uQq");
                        Publicdata.uInterest =userBasic.getString("uInterest");
                        Publicdata.uDepartment=userBasic.getString("uDepartment");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!Publicdata.uId.equals("null")) {
                                    et_id.setText(Publicdata.uId);
                                }
                                if(!Publicdata.uDepartment.equals("null")) {
                                    et_department.setText(Publicdata.uDepartment);
                                }
                                if(!Publicdata.uAge.equals("null")) {
                                    et_age.setText(Publicdata.uAge);
                                }
                                if(!Publicdata.uPhone.equals("null")) {
                                    et_phone.setText(Publicdata.uPhone);
                                }
                                if(!Publicdata.uEmail.equals("null")) {
                                    et_email.setText(Publicdata.uEmail);
                                }
                                if(!Publicdata.uAddress.equals("null")) {
                                    et_address.setText(Publicdata.uAddress);
                                }
                                if(!Publicdata.uInterest.equals("null")) {
                                    et_interest.setText(Publicdata.uInterest);
                                }
                                if(!Publicdata.uQq.equals("null")) {
                                    et_qq.setText(Publicdata.uQq);
                                }
                                if(!Publicdata.uGender.equals("null")) {
                                    et_sex.setText(Publicdata.uGender);
                                }
                            }
                        });
                    }
                    else if(jsonObject.getInt("success")==0){
                        System.out.print("连接错误");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    public void postData(){
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId",uId);
        encodingBuilder.add("uAge",et_age.getText().toString());
        encodingBuilder.add("uGender",et_sex.getText().toString());
        encodingBuilder.add("uPhone",et_phone.getText().toString());
        encodingBuilder.add("uAddress",et_address.getText().toString());
        encodingBuilder.add("uEmail",et_email.getText().toString());
        encodingBuilder.add("uQq",et_qq.getText().toString());
        encodingBuilder.add("uInterest",et_interest.getText().toString());
        encodingBuilder.add("uDepartment",et_department.getText().toString());
        final Request request = new Request.Builder()
                .url(url_post)
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
                    JSONObject jsonObject=new JSONObject(s);
                    System.out.println(s);
                    if(jsonObject.getInt("success")==1){
                       System.out.println("post成功");
                    }
                    else if(jsonObject.getInt("success")==0){
                        System.out.println("post失败");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
