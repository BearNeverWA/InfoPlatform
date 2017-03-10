package com.makethings.infoplatform.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.makethings.infoplatform.Club;
import com.makethings.infoplatform.R;


import java.util.List;

/**
 * Created by Call Me Bear on 2017/3/9.
 */

public class ClubAdapter extends ArrayAdapter<Club> {

    private int resourceId;

    public ClubAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Club> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final Club club = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView tvClubName = (TextView) view.findViewById(R.id.tv_club_name);
        tvClubName.setText(club.getName());
        TextView tvStar = (TextView) view.findViewById(R.id.tv_club_star);
        tvStar.setText(club.getStar());
        final Button btnFollow = (Button) view.findViewById(R.id.btn_club_follow);
        if (club.getIsFollow())
            btnFollow.setText("♥");
        else
            btnFollow.setText("♡");
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                club.setIsFollow(!club.getIsFollow());
                if (club.getIsFollow())
                    btnFollow.setText("♥");
                else
                    btnFollow.setText("♡");
            }
        });
        return view;
    }
}