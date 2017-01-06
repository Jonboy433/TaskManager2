package application.logic;

import java.util.ArrayList;
import java.util.List;

import application.models.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskManager {
	
	private static TaskManager instance = null;
	private List<Task> tasks;
	private ObservableList<String> taskSummaries;
	
	protected TaskManager() {
		tasks = new ArrayList<Task>();
		taskSummaries = FXCollections.observableArrayList();
	}

	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}
		
		return instance;
	}

	public void addTask(Task task) {
		tasks.add(task);
		updateTaskSummaries(task);
	}
	
	public void removeTask(Task task) {
		tasks.remove(task);
	}
	
	
	public int getTotalTaskCount() { 
		return tasks.size();
	}
	
	public List<Task> getAllTasks() {
		return tasks;
	}
	
	public String getTaskSummaryByIndex(int taskIndex) {
		return tasks.get(taskIndex).getTaskSummary();
	}
	
	public String getTaskDescriptionByIndex(int taskIndex) {
		return tasks.get(taskIndex).getTaskDescription();
	}
	
	public String getTaskDueDateByIndex(int taskIndex) {
		return tasks.get(taskIndex).getDueDate();
	}
	
	public ObservableList<String> getAllTaskSummaries() {
		return taskSummaries;
	}
	
	public void updateTaskSummaries(Task task) {
		taskSummaries.add(task.getTaskSummary());
	}
}
