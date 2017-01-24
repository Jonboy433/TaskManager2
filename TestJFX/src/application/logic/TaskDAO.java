package application.logic;

import java.sql.SQLException;
import java.util.List;

import application.dao.Task;

public interface TaskDAO {

	void createTask(Task task) throws SQLException;
	
	List<Task> getAllTasks();
	
	List<Task> getAllTasksHistory();
	
	Task getTask(int id);

	// id = id value of the Task in DB
	void deleteTask(int id) throws SQLException;

	// id = id value of the Task in DB
	void updateTask(int id, String summary, String desc, String dueDate) throws SQLException;

}