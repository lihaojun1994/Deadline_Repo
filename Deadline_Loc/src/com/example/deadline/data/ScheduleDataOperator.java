package com.example.deadline.data;

import android.content.Context;

public class ScheduleDataOperator {
	private DBAdapter dbAdapter;

	public ScheduleDataOperator(Context context) {
		dbAdapter = new DBAdapter(context);
	}

	// �����ݿ�
	public void open() {
		dbAdapter.open();
	}

	// �ر����ݿ�
	public void close() {
		dbAdapter.close();
	}

	// ��
	public long add(ScheduleData scheduleData) {
		return dbAdapter.insert(scheduleData);
	}

	// ɾ
	public long delete(ScheduleData scheduleData) {
		return dbAdapter.deleteOneData(scheduleData.getBuildUpTime());
	}

	// ��
	public long update(ScheduleData scheduleData){
		//return dbAdapter.updateOneData(scheduleData.getBuildUpTime(), scheduleData);
		if((dbAdapter.deleteOneData(scheduleData.getBuildUpTime())>0)&&(
		dbAdapter.insert(scheduleData)>0)){
		return 1;	
		}else{
			return 0;
		}
	}

	// ��һ��
	public ScheduleData findOne(ScheduleData scheduleData) {
		return dbAdapter.queryOneData(scheduleData.getBuildUpTime())[0];
	}

	// ��ȫ��
	public ScheduleData[] findAll() {
		return dbAdapter.queryAllData();
	}

	// ��������
	public int getCount() {
		return dbAdapter.getCount();
	}
}
