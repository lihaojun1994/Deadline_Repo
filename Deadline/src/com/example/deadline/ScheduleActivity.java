package com.example.deadline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.AdapterView.OnItemSelectedListener;

public class ScheduleActivity extends Activity {
	private EditText et1,et2;
	private Spinner sp1,sp2;
	private Button date,time;
	private Button bt;
	private Intent intent;
	private Bundle bundle;
	private String text1,text2,date_chosen,time_chosen,text5,text6;
	private Integer position;
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		
		
	    bundle = this.getIntent().getExtras();
	    position = bundle.getInt("position");
	    text1 = bundle.getString("str1");
	    text2 = bundle.getString("str2");
	    date_chosen = bundle.getString("str3");
	    time_chosen = bundle.getString("str4");
	    text5 = bundle.getString("str5");
	    text6 = bundle.getString("str6");
	    System.out.println(text5+" "+text6);
	    et1 = (EditText)findViewById(R.id.editText1);
	    et1.setText(text1);
	    et2 = (EditText)findViewById(R.id.editText2);
	    et2.setText(text2);
	    
	    final String[] data1=new String[]{"重要","日常"};
		final TextView stext1=(TextView)findViewById(R.id.spinnerText1);
		sp1=(Spinner)findViewById(R.id.Spinner1);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data1);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter1);
		if(text5.equals("Normal")){sp1.setSelection(1);}
		sp1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				stext1.setText("任务级别是："+data1[arg2]);
				if(arg2==0)
					text5="Important";
				else
					text5="Normal";
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		final String[] data2=new String[]{"工作","个人"};
		final TextView stext2=(TextView)findViewById(R.id.spinnerText2);
		sp2=(Spinner)findViewById(R.id.Spinner2);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data2);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(adapter2);
		if(text6.equals("Personal")){
			sp2.setSelection(1);
		}
		sp2.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				stext2.setText("任务类别是："+data2[arg2]);
				if(arg2==0)
					text6="Work";
				else
					text6="Personal";
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
	    
		
		date=(Button)findViewById(R.id.date);
		date.setText("Deadline->"+date_chosen);
//		final TextView textview_date=(TextView)findViewById(R.id.textview_date);
		date.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				View view=getLayoutInflater().inflate(R.layout.alarm_date,null);
				final DatePicker datepicker=(DatePicker)view.findViewById(R.id.datePicker);
				new AlertDialog.Builder(ScheduleActivity.this).setTitle("选择完成日期").setView(view)
					.setPositiveButton("确定",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int year=datepicker.getYear();
						int month=datepicker.getMonth();
						int day=datepicker.getDayOfMonth();
						date_chosen=year+"-"+month+"-"+day;
//						textview_date.setText("所选择的日期是"+date_chosen);
						date.setText("Deadline->"+date_chosen);
					}
				}).setNegativeButton("取消",null).show();
			}
		});
						
		time=(Button)findViewById(R.id.time);
		time.setText("RemindAt->"+time_chosen);
//		final TextView textview_time=(TextView)findViewById(R.id.textview_time);
		time.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				View view=getLayoutInflater().inflate(R.layout.alarm_time,null);
				final TimePicker timepicker=(TimePicker)view.findViewById(R.id.timePicker);
				timepicker.setIs24HourView(true);
				new AlertDialog.Builder(ScheduleActivity.this).setTitle("设置提醒时间").setView(view)
					.setPositiveButton("确定",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int hour=timepicker.getCurrentHour();
						int min=timepicker.getCurrentMinute();
						time_chosen=hour+":"+min;
//						textview_time.setText("所选择的时间是"+time_chosen);
						time.setText("RemindAt->"+time_chosen);
					}
				}).setNegativeButton("取消",null).show();
			}
		});
	    
	    intent = this.getIntent();
	    bt = (Button)findViewById(R.id.button);
	    bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				text1 = et1.getText().toString();
				text2 = et2.getText().toString();
				bundle.putString("str1",text1); 
				bundle.putString("str2",text2);
				bundle.putString("str3", date_chosen);
				bundle.putString("str4",time_chosen);
				bundle.putString("str5", text5);
				bundle.putString("str6", text6);
				bundle.putInt("position",position);
		        intent.putExtras(bundle);
				ScheduleActivity.this.setResult(RESULT_OK,intent);
				ScheduleActivity.this.finish();
			}
		});
	    
	}
}