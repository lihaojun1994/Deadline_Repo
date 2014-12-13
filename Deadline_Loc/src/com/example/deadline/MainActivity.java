package com.example.deadline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import com.example.deadline.data.ScheduleData;
import com.example.deadline.data.ScheduleDataOperator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private ScheduleDataOperator scheduleDataOperator;
	private ImageButton imageMenuButton;
	private ImageButton imageAddButton;
	private SwipeListView myListView;
	private Intent intent;
	private ArrayList<ScheduleData> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		scheduleDataOperator = new ScheduleDataOperator(this);
		scheduleDataOperator.open();

		initData();
		initView();
	}

	private void initData() {
		data = new ArrayList<ScheduleData>();

		ScheduleData[] scheduleDatas = scheduleDataOperator.findAll();

		if (scheduleDatas != null) {
			for (int i = 0; i < scheduleDatas.length; i++) {
				ScheduleData msg = scheduleDatas[i];
				data.add(msg);
			}
		}

//		Comparator<ScheduleData> comparator = new Comparator<ScheduleData>() {
//			@Override
//			public int compare(ScheduleData m1, ScheduleData m2) {
//				// TODO Auto-generated method stub
//				if (!m1.getDeadlineTime().equals(m2.getDeadlineTime())) {
//					return m1.getDeadlineTime().compareTo(m2.getDeadlineTime());
//				}
//				return 0;
//			}
//		};
//		Collections.sort(data, comparator);
		orderData1();
	}

	private void orderData1() {
		Comparator<ScheduleData> comparator = new Comparator<ScheduleData>() {
			@Override
			public int compare(ScheduleData m1, ScheduleData m2) {
				// TODO Auto-generated method stub
				if (!m1.getDeadlineTime().equals(m2.getDeadlineTime())) {
					return m2.getDeadlineTime().compareTo(m1.getDeadlineTime());
				}
				return 0;
			}
		};

		Collections.sort(data, comparator);
	}

	private void orderData2() {
		Comparator<ScheduleData> comparator = new Comparator<ScheduleData>() {
			@Override
			public int compare(ScheduleData m1, ScheduleData m2) {
				// TODO Auto-generated method stub
				if (m1.isImportant() && !m2.isImportant()) {
					return -1;
				} else if (!m1.isImportant() && m2.isImportant()) {
					return 1;
				}
				if (!m1.getDeadlineTime().equals(m2.getDeadlineTime())) {
					return m1.getDeadlineTime().compareTo(m2.getDeadlineTime());
				}
				return 0;
			}
		};

		Collections.sort(data, comparator);
	}

	private void initView() {
		final DrawerLayout view = (DrawerLayout) findViewById(R.id.drawer_layout);

		imageMenuButton = (ImageButton) findViewById(R.id.imageMenuButton);
		imageAddButton = (ImageButton) findViewById(R.id.imageAddButton);
		Button bt1 = (Button) findViewById(R.id.btn1);
		Button bt2 = (Button) findViewById(R.id.btn2);
		OnClickListener listener1 = new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				view.openDrawer(Gravity.LEFT);
			}

		};
		OnClickListener listener2 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent = new Intent();
				intent.setClass(MainActivity.this, AddActivity.class);
				startActivityForResult(intent, 1);
			}
		};
		OnClickListener listener3 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderData1();
				initView();
				view.closeDrawer(Gravity.LEFT);
			}
		};
		OnClickListener listener4 = new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderData2();
				initView();
				view.closeDrawer(Gravity.LEFT);
			}
		};
		imageMenuButton.setOnClickListener(listener1);
		imageAddButton.setOnClickListener(listener2);
		bt1.setOnClickListener(listener3);
		bt2.setOnClickListener(listener4);

		myListView = (SwipeListView) findViewById(R.id.listview);
		SwipeAdapter mAdapter = new SwipeAdapter(this, data,
				myListView.getRightViewWidth());
		mAdapter.setOnRightItemClickListener(new SwipeAdapter.onRightItemClickListener() {
			@Override
			public void onRightItemClick(View v, final int position) {
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("确认")
						.setMessage("确定删除吗？")
						.setPositiveButton("是",
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub
										ScheduleData scheduleData = new ScheduleData();
										scheduleData.setBuildUpTime(data.get(
												position).getBuildUpTime());
										long result = scheduleDataOperator
												.delete(scheduleData);

										Toast.makeText(
												MainActivity.this,
												"删除"
														+ (result > 0 ? "成功"
																: "失败"),
												Toast.LENGTH_SHORT).show();
										initData();
										initView();
									}
								}).setNegativeButton("否", null).show();

			}
		});

		//myListView.setAdapter(mAdapter);
		NewsAdapter tAdapter = new NewsAdapter(this,data); 
		myListView.setAdapter(tAdapter);
		
		myListView.setTextFilterEnabled(true);
		myListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view,
					int position, long arg3) {
				Bundle bundle = new Bundle();
				bundle.putString("id", data.get(position).getBuildUpTime());
				bundle.putString("str1", data.get(position).getTitle()
						.toString());
				bundle.putString("str2", data.get(position).getText()
						.toString());
				bundle.putString("str3", data.get(position).getDeadlineTime()
						.toString());
				bundle.putString("str4", data.get(position).getRemindTime()
						.toString());
				bundle.putString("str5", data.get(position).isImportant() + "");
				bundle.putString("str6", data.get(position).getType()
						.toString());
				bundle.putInt("position", position);
				intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(MainActivity.this, ScheduleActivity.class);
				startActivityForResult(intent, 0);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (requestCode == 1) {
			switch (resultCode) {
			case RESULT_OK:
				Bundle bundle = intent.getExtras();
				String buildUpTime = new Date().toString();
				String text1 = bundle.getString("str1");
				String text2 = bundle.getString("str2");
				String text3 = bundle.getString("str3");
				String text4 = bundle.getString("str4");
				String text5,
				text6;

				if (bundle.getBoolean("bool1") == true)
					text5 = "true";
				else
					text5 = "false";

				if (bundle.getBoolean("bool2") == true)
					text6 = "Personal";
				else
					text6 = "Work";

				ScheduleData ScheduleData = new ScheduleData(buildUpTime, text1, text2,
						text3, text4, text5, text6);
				
				System.out.println(ScheduleData.toString());
				
				long result = scheduleDataOperator.add(ScheduleData);
				if (result > 0) {
					data.add(ScheduleData);
				}
				Toast.makeText(MainActivity.this,
						"添加" + (result > 0 ? "成功" : "失败"), Toast.LENGTH_SHORT)
						.show();
				initView();
				break;
			default:
				break;
			}
		} else if (requestCode == 0) {
			switch (resultCode) {
			case RESULT_OK:
				Bundle bundle = intent.getExtras();
				String id = bundle.getString("id");
				String text1 = bundle.getString("str1");
				String text2 = bundle.getString("str2");
				String text3 = bundle.getString("str3");
				String text4 = bundle.getString("str4");
				String text5 = bundle.getString("str5");
				String text6 = bundle.getString("str6");
				ScheduleData ScheduleData = new ScheduleData(id, text1, text2,
						text3, text4, text5, text6);
				long result = scheduleDataOperator.update(ScheduleData);
				if (result > 0) {
					int position = bundle.getInt("position");
					data.get(position).setTitle(text1);
					data.get(position).setText(text2);
					data.get(position).setDeadlineTime(text3);
					data.get(position).setRemindTime(text4);
					data.get(position).setIsImportant(text5);
					data.get(position).setType(text6);
				}
				Toast.makeText(MainActivity.this,
						"修改" + (result > 0 ? "成功" : "失败"), Toast.LENGTH_SHORT)
						.show();
				

				initView();
				break;

			default:
				break;
			}
		}

	}

}
