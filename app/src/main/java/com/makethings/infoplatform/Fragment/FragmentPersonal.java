package com.makethings.infoplatform.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.makethings.infoplatform.Activity.MineClubActivity;
import com.makethings.infoplatform.Activity.MineEnrollActivity;
import com.makethings.infoplatform.Activity.MineFollowActivity;
import com.makethings.infoplatform.Activity.ModifyClubDataActivity;
import com.makethings.infoplatform.R;

/**
 * Created by Call Me Bear on 2017/2/26.
 */

public class FragmentPersonal extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button btnMineClub = (Button) getActivity().findViewById(R.id.btn_mine_club);
        btnMineClub.setOnClickListener(this);
        Button btnMineEnroll = (Button) getActivity().findViewById(R.id.btn_mine_enroll);
        btnMineEnroll.setOnClickListener(this);
        Button btnMineFollow = (Button) getActivity().findViewById(R.id.btn_mine_follow);
        btnMineFollow.setOnClickListener(this);
        Button btnModifyData = (Button) getActivity().findViewById(R.id.btn_modify_data);
        btnModifyData.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){

            case R.id.btn_mine_club:
                intent=new Intent(getActivity(), MineClubActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_mine_enroll:
                intent=new Intent(getActivity(), MineEnrollActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_mine_follow:
                intent=new Intent(getActivity(), MineFollowActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_modify_data:
                intent=new Intent(getActivity(), ModifyClubDataActivity.class);
                startActivity(intent);
                break;
        }
    }
}
