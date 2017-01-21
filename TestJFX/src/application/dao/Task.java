package application.dao;

import javafx.beans.property.SimpleStringProperty;

public class Task {

	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	private SimpleStringProperty summary;
	private SimpleStringProperty description;
	private SimpleStringProperty dueDate;
	
	public Task(int id, String summary, String description, String dueDate) {
		this.id = id;
		this.summary = new SimpleStringProperty(summary);
		this.description = new SimpleStringProperty(description);
		this.dueDate = new SimpleStringProperty(dueDate);
	}
	
	public Task(String summary, String description, String dueDate) {
		this.summary = new SimpleStringProperty(summary);
		this.description = new SimpleStringProperty(description);
		this.dueDate = new SimpleStringProperty(dueDate);
	}
	
	public String getSummary() {
		return summary.get();
	} 
	
	public void setSummary(String summary) {
		this.summary.set(summary);
	}
	public String getDescription() {
		return description.get();
	}
	public void setDescription(String description) {
		this.description.set(description);
	}
	public String getDueDate() {
		return dueDate.get();
	}
	public void setDueDate(String dueDate) {
		this.dueDate.set(dueDate);
	}
}
