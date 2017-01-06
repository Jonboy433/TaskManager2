package application.models;

import java.util.Calendar;
import java.util.Date;

public class Task {
	
	private String summary;
	private String description;
	private Date dueDate;

	public Task() {
		description = "";
		
		//Set default due date to 1/1/16
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, 1);
		cal.set(Calendar.DATE, 1);
		
		dueDate = cal.getTime();
	}
	
	public Task(String summary, String contents, Date dueDate) {
		this.summary = summary;
		this.description = contents;
		this.dueDate = dueDate;
	}
	
	public String getTaskSummary() {
		return this.summary;
	}
	
	public String getTaskDescription() {
		return this.description;
	}
	
	public String getDueDate() {
		return this.dueDate.toString();
	}
}
