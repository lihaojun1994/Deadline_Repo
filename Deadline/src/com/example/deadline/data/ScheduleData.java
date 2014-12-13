package com.example.deadline.data;

public class ScheduleData {
	private String deadlineTime;
	private String title;
	private String text;
	private String buildUpTime;
	private String remindTime;
	private String isImportant;
	private String type;

	public ScheduleData(){}
	public ScheduleData(String buildUpTime, String title, String text,String deadlineTime, 
			String remindTime,String isImportant,String type
			) {
		this.buildUpTime = buildUpTime;
		this.title = title;
		this.text = text;
		this.type = type;
		this.isImportant = isImportant;
		this.remindTime = remindTime;
		this.deadlineTime = deadlineTime;
	}

	public String getBuildUpTime() {
		return buildUpTime;
	}

	public void setBuildUpTime(String buildUpTime) {
		this.buildUpTime = buildUpTime;
	}

	public String getDeadlineTime() {
		return deadlineTime;
	}

	public void setDeadlineTime(String deadlineTime) {
		this.deadlineTime = deadlineTime;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public String getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(String isImportant) {
		this.isImportant = isImportant;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return "title:" + title + "\n text:" + text + "\n type:" + type
				+ "\n isImportant:" + isImportant + "\n buildUpTime"
				+ buildUpTime + "\n remindTime:" + remindTime
				+ "\n deadlineTime:" + deadlineTime;
	}
}
