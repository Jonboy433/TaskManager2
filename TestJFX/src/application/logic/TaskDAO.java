package application.logic;

import java.sql.SQLException;
import java.util.List;

import application.dao.Task;

public interface TaskDAO {

	void createTask(Task task) throws SQLException;
	
	List<Task> getAllTasks();
	
	Task getTask(int id);

	// id = id value of the Task in DB
	void deleteTask(int id);

	// id = id value of the Task in DB
	void updateTask(int id);

}