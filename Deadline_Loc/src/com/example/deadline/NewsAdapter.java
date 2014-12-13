package com.example.deadline;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.deadline.data.ScheduleData;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter {


    Context context;                          
    private ArrayList<ScheduleData> data;
    LayoutInflater listContainer;
    TextView time,title,mess;
    LinearLayout ll;

    public NewsAdapter(Context context,ArrayList<ScheduleData> data){
        this.context = context;
        listContainer = LayoutInflater.from(context);
        this.data = data;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = listContainer.inflate(R.layout.timeline, null);
            time = (TextView)convertView.findViewById(R.id.time);
            title = (TextView)convertView.findViewById(R.id.title);
            mess = (TextView)convertView.findViewById(R.id.mess);
        }else {
            return convertView;
        }


        if(!data.isEmpty()){
        	ScheduleData msg = data.get(position);
            time.setText(msg.getDeadlineTime());
            title.setText(msg.getTitle());
            mess.setText(msg.getText());
        }
        
        title.setTextColor(Color.WHITE);
        mess.setTextColor(Color.WHITE);
        
        return convertView;
        
    }



}