package com.makethings.infoplatform.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.makethings.infoplatform.Activity.ClubDetailActivity;
import com.makethings.infoplatform.R;

/**
 * Created by Call Me Bear on 2017/2/26.
 */

public class FragmentClub extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_club,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btnClubDetail= (Button) getActivity().findViewById(R.id.btn_club_detail);
        btnClubDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ClubDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}
