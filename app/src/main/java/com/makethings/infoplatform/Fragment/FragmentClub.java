package com.makethings.infoplatform.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.makethings.infoplatform.Activity.ClubDetailActivity;
import com.makethings.infoplatform.Adapter.ClubAdapter;
import com.makethings.infoplatform.Club;
import com.makethings.infoplatform.Event;
import com.makethings.infoplatform.Publicdata;
import com.makethings.infoplatform.R;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Call Me Bear on 2017/2/26.
 */

public class FragmentClub extends Fragment {

    private List<Club> clubList = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_club, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        ClubAdapter adapter = new ClubAdapter(getActivity(), R.layout.content_list_club, clubList);
        ListView listClub = (ListView) getActivity().findViewById(R.id.lv_club);
        listClub.setAdapter(adapter);
        listClub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Club club = clubList.get(position);
                Intent intent = new Intent(getActivity(), ClubDetailActivity.class);
                String str = clubList.get(position).getcId();
                intent.putExtra("cId", str);
                intent.putExtra("cName", clubList.get(position).getName());
                //int Id=Integer.parseInt(str);
//                intent.putExtra("cId", str);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        OkHttpClient mOkHttpClient1 = new OkHttpClient();
        FormEncodingBuilder encodingBuilder = new FormEncodingBuilder();
        encodingBuilder.add("uId", Publicdata.uId);
        encodingBuilder.add("page", "0");
        final Request request1 = new Request.Builder()
                .url("http://community.stevenming.com.cn/c_0_jComList")//;jsessionid="+swe.substring(swe.indexOf("=")+1)
                .post(encodingBuilder.build())
                .build();


        Call call1 = mOkHttpClient1.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                final String s = response.body().string();
                System.out.println(s);

                try {
                    JSONObject json = new JSONObject(s);
                    JSONObject community = json.getJSONObject("communityList");
                    JSONArray communities = community.getJSONArray("list");

                    for (int i = 1; i < communities.length(); i++) {
                        JSONObject temp = (JSONObject) communities.get(i);
                        String stars = "";
                        for (int j = 0; j < temp.getInt("cStar"); j++) {
                            stars += "★";
                        }
                        for (int j = 0; j < 5 - temp.getInt("cStar"); j++) {
                            stars += "☆";
                        }
                        Club club = new Club(temp.getString("cId"), temp.getString("cName"), stars, false);
                        clubList.add(club);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }
}
