package com.makethings.infoplatform.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.makethings.infoplatform.R;

import java.util.ArrayList;
import java.util.List;

public class CheckEventActivity extends AppCompatActivity {

    private RecyclerView checkEventRecyclerView;
    private List<Student> studentList = new ArrayList();
    private CheckEventRecycleAdapter adapter;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_events);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("我的报名");
        initStudentData();
        checkEventRecyclerView = (RecyclerView) findViewById(R.id.check_event_recyclerView);
        checkEventRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CheckEventRecycleAdapter();
        adapter.setItemListener(new CheckEventItemOnClick() {
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
        checkEventRecyclerView.setAdapter(adapter);
        Button btnChangeEvent= (Button) findViewById(R.id.btn_modify_event);
        btnChangeEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(CheckEventActivity.this,IssueEventsActivity.class);
                startActivity(i);
            }
        });
    }

    private void initStudentData(){
        for (int i = 0; i < 10; i++) {
            Student student = new Student("14122756 "+i,"颜鑫"+i);
            studentList.add(student);
        }
    }

    class Student{
        public String studentNum;
        public String studentName;

        Student(String studentNum, String studentName){
            this.studentNum = studentNum;
            this.studentName = studentName;
        }
    }
    // 这个接口不是去给下边的适配器去实现的，而是让适配器增加一个点击事件接口数据成员，
    // 这样在new一个适配器，当你去set这个接口数据成员就需要去具体实现这个接口的方法，
    // 而适配器在bindView 的时候 给每个View增加一个点击事件，这个点击事件就是去调用接口的方法，
    // 将单个ViewHolder 的position 和View 传入这个接口中
    interface CheckEventItemOnClick{
        public void itemOnClick(View view, int pos);
    }

    class CheckEventRecycleAdapter extends RecyclerView.Adapter<CheckEventRecycleAdapter.CheckEventHolder> {

        CheckEventItemOnClick ItemListener;

        public void setItemListener(CheckEventItemOnClick itemListener) {
            ItemListener = itemListener;
        }

        @Override
        public CheckEventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CheckEventHolder holder = new CheckEventHolder(LayoutInflater.
                    from(CheckEventActivity.this)
                    .inflate(R.layout.check_event_item,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final CheckEventHolder holder, int position) {
            holder.studentNum.setText(studentList.get(position).studentNum);
            holder.studentName.setText(studentList.get(position).studentName);
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

        @Override
        public int getItemCount() {
            return studentList.size();
        }


        class CheckEventHolder extends RecyclerView.ViewHolder{
            private TextView studentNum;
            private TextView studentName;
            private View view;

            CheckEventHolder(View itemView) {
                super(itemView);
                view = itemView;
                studentNum = (TextView) itemView.findViewById(R.id.check_event_item_studentNum);
                studentName = (TextView) itemView.findViewById(R.id.check_event_item_studentName);
            }
        }
    }
}
