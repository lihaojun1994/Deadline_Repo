package com.example.deadline.data;

import android.content.Context;

public class ScheduleDataOperator {
	private DBAdapter dbAdapter;

	public ScheduleDataOperator(Context context) {
		dbAdapter = new DBAdapter(context);
	}

	// 打开数据库
	public void open() {
		dbAdapter.open();
	}

	// 关闭数据库
	public void close() {
		dbAdapter.close();
	}

	// 增
	public long add(ScheduleData scheduleData) {
		return dbAdapter.insert(scheduleData);
	}

	// 删
	public long delete(ScheduleData scheduleData) {
		return dbAdapter.deleteOneData(scheduleData.getBuildUpTime());
	}

	// 改
	public long update(ScheduleData scheduleData){
		//return dbAdapter.updateOneData(scheduleData.getBuildUpTime(), scheduleData);
		if((dbAdapter.deleteOneData(scheduleData.getBuildUpTime())>0)&&(
		dbAdapter.insert(scheduleData)>0)){
		return 1;	
		}else{
			return 0;
		}
	}

	// 查一个
	public ScheduleData findOne(ScheduleData scheduleData) {
		return dbAdapter.queryOneData(scheduleData.getBuildUpTime())[0];
	}

	// 查全部
	public ScheduleData[] findAll() {
		return dbAdapter.queryAllData();
	}

	// 返回数量
	public int getCount() {
		return dbAdapter.getCount();
	}
}
