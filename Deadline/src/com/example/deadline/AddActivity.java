package com.example.deadline;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddActivity extends Activity{
	private EditText et1;
	private EditText et2;
	private String title;
	private String messg;
	private String date_chosen;
	private String time_chosen;
	private boolean isImportant,isPersonal;
	private Button bt,date,time;
	private ImageButton imbt;
	private ListView lv;
	private TextView textview_boolean,textview_date,textview_time;
	private Intent intent;
	private Bundle bundle;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add);
		bundle = new Bundle();
		intent = this.getIntent();
		
//		String[] data=new String[]{"重要","日常"};
//		ArrayAdapter<String> checkAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_checked,data);
//		lv=(ListView)findViewById(R.id.lv);
//		lv.setAdapter(checkAdapter);
//		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//		textview_boolean=(TextView)findViewById(R.id.textview_boolean);
//		lv.setOnItemClickListener(new OnItemClickListener(){
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//					long arg3) {
//				// TODO Auto-generated method stub
//				if(position==0){
//					isImportant=true;
//					textview_boolean.setText("重要");
//				}
//				else{
//					isImportant=false;
//					textview_boolean.setText("日常");
//				}
//			}
//			
//		});
		
		final String[] data1=new String[]{"重要","日常"};
		final TextView stext1=(TextView)findViewById(R.id.spinnerText1);
		Spinner sp1=(Spinner)findViewById(R.id.Spinner1);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data1);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp1.setAdapter(adapter1);
		sp1.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				stext1.setText("任务级别是："+data1[arg2]);
				if(arg2==0)
					isImportant=true;
				else
					isImportant=false;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		final String[] data2=new String[]{"工作","个人"};
		final TextView stext2=(TextView)findViewById(R.id.spinnerText2);
		Spinner sp2=(Spinner)findViewById(R.id.Spinner2);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,data2);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp2.setAdapter(adapter2);
		sp2.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				stext2.setText("任务类别是："+data2[arg2]);
				if(arg2==0)
					isPersonal=false;
				else
					isPersonal=true;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		date=(Button)findViewById(R.id.date);
		textview_date=(TextView)findViewById(R.id.textview_date);
		date.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				View view=getLayoutInflater().inflate(R.layout.alarm_date,null);
				final DatePicker datepicker=(DatePicker)view.findViewById(R.id.datePicker);
				new AlertDialog.Builder(AddActivity.this).setTitle("选择完成日期").setView(view)
					.setPositiveButton("确定",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int year=datepicker.getYear();
						int month=datepicker.getMonth();
						int day=datepicker.getDayOfMonth();
						date_chosen=year+"-"+month+"-"+day;
						textview_date.setText("所选择的日期是"+date_chosen);
					}
				}).setNegativeButton("取消",null).show();
			}
		});
						
		time=(Button)findViewById(R.id.time);
		textview_time=(TextView)findViewById(R.id.textview_time);
		time.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				View view=getLayoutInflater().inflate(R.layout.alarm_time,null);
				final TimePicker timepicker=(TimePicker)view.findViewById(R.id.timePicker);
				timepicker.setIs24HourView(true);
				new AlertDialog.Builder(AddActivity.this).setTitle("设置提醒时间").setView(view)
					.setPositiveButton("确定",new DialogInterface.OnClickListener(){
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						int hour=timepicker.getCurrentHour();
						int min=timepicker.getCurrentMinute();
						time_chosen=hour+":"+min;
						textview_time.setText("所选择的时间是"+time_chosen);
					}
				}).setNegativeButton("取消",null).show();
			}
		});
		
		bt = (Button)findViewById(R.id.button);
		et1 = (EditText)findViewById(R.id.editText1);
		et2 = (EditText)findViewById(R.id.editText2);
	    bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				title = et1.getText().toString();
				messg = et2.getText().toString();
				if(title==""||messg==null||date_chosen==null||time_chosen==null){
					 Toast.makeText(AddActivity.this, "信息不完整，请完善后提交", Toast.LENGTH_LONG).show();
				}
				else{					
//					int hour=timepicker.getCurrentHour();
//					int min=timepicker.getCurrentMinute();
//					text3=hour+":"+min;
//					
					bundle.putString("str1",title); 
					bundle.putString("str2",messg);
					bundle.putString("str3", date_chosen);
					bundle.putString("str4", time_chosen);
					bundle.putBoolean("bool1", isImportant);
					bundle.putBoolean("bool2", isPersonal);
			        intent.putExtras(bundle);
			        
					AddActivity.this.setResult(RESULT_OK,intent);
					AddActivity.this.finish();
				}

			}
		});
	    
	    imbt = (ImageButton)findViewById(R.id.imageButton1);
	    imbt.setOnClickListener(new OnClickListener(){
	    	public void onClick(View v){
	    		AddActivity.this.setResult(RESULT_CANCELED);
	    		AddActivity.this.finish();
	    	}
	    });
	}
}
