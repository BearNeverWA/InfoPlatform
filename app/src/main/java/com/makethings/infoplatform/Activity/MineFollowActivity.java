
package com.makethings.infoplatform.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.makethings.infoplatform.R;

import java.util.ArrayList;
import java.util.List;

public class MineFollowActivity extends AppCompatActivity {
    private RecyclerView mineFollowRecyclerView;
    private List<Club> clubList = new ArrayList();
    private mineFollowRecycleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_follow);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的报名");
        initClubData();
        mineFollowRecyclerView = (RecyclerView) findViewById(R.id.mine_follow_recycler_view);
        mineFollowRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new mineFollowRecycleAdapter();
        adapter.setItemListener(new mineFollowItemOnClick() {
            @Override
            public void itemOnClick(final View view, int pos) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(view.getContext(),"Click Event ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        mineFollowRecyclerView.setAdapter(adapter);
    }

    private void initClubData(){
        for (int i = 0; i < 10; i++) {
            Club club = new Club("title "+i,"☆x"+i,"details details details details details  "+i);
            clubList.add(club);
        }
    }

    class Club{
        public String title;
        public String stars;
        public String details;

        Club(String title, String stars, String details){
            this.title = title;
            this.stars = stars;
            this.details = details;
        }
    }
    // 这个接口不是去给下边的适配器去实现的，而是让适配器增加一个点击事件接口数据成员，
    // 这样在new一个适配器，当你去set这个接口数据成员就需要去具体实现这个接口的方法，
    // 而适配器在bindView 的时候 给每个View增加一个点击事件，这个点击事件就是去调用接口的方法，
    // 将单个ViewHolder 的position 和View 传入这个接口中
    interface mineFollowItemOnClick{
        public void itemOnClick(View view, int pos);
    }

    class mineFollowRecycleAdapter extends RecyclerView.Adapter<mineFollowRecycleAdapter.mineFollowHolder> {

        mineFollowItemOnClick ItemListener;

        public void setItemListener(mineFollowItemOnClick itemListener) {
            ItemListener = itemListener;
        }

        @Override
        public mineFollowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            mineFollowHolder holder = new mineFollowHolder(LayoutInflater.
                    from(MineFollowActivity.this)
                    .inflate(R.layout.mine_follow_item,parent,false));
            return holder;
        }

        @Override
        public int getItemCount() {
            return clubList.size();
        }

        @Override
        public void onBindViewHolder(final mineFollowHolder holder, int position) {
            holder.title.setText(clubList.get(position).title);
            holder.stars.setText(clubList.get(position).stars);
            holder.details.setText(clubList.get(position).details);
            if(ItemListener != null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        ItemListener.itemOnClick(holder.itemView,pos);
                    }
                });
            }
        }


        class mineFollowHolder extends RecyclerView.ViewHolder{
            private TextView title;
            private TextView stars;
            private TextView details;
            private View view;

            public mineFollowHolder(View itemView) {
                super(itemView);
                view = itemView;
                title = (TextView) itemView.findViewById(R.id.mine_follow_item_title);
                stars = (TextView) itemView.findViewById(R.id.mine_follow_item_stars);
                details = (TextView) itemView.findViewById(R.id.mine_follow_item_details);

            }
        }
    }
}
