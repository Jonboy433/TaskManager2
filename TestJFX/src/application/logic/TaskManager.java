package application.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.dao.Task;

public class TaskManager implements TaskDAO {

	private static TaskManager instance = null;
	private Connection conn;
	private List<String> taskSummaries;
	

	private TaskManager() {
		taskSummaries = new ArrayList<String>();
	}

	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}

		return instance;
	}

	/* (non-Javadoc)
	 * @see application.logic.TaskDAO#createTask(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void createTask(Task task) throws SQLException {
	
		
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(
					String.format("INSERT into tasks('summary','description','due_date') values('%s','%s','%s')",
							task.getSummary(), task.getDescription(), task.getDueDate()));
			ps.executeUpdate();
		
				conn.close();
			
		
		
		/** WORKING BEFORE EXCEPTION TESTING
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement(
					String.format("INSERT into tasks('summary','description','due_date') values('%s','%s','%s')",
							task.getSummary(), task.getDescription(), task.getDueDate()));
			ps.executeUpdate();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println("Inside the SQLException block");
			e1.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} */
	}

	// id = id value of the Task in DB
	/* (non-Javadoc)
	 * @see application.logic.TaskDAO#delete(int)
	 */
	@Override
	public void deleteTask(int id) {

	}

	// id = id value of the Task in DB
	/* (non-Javadoc)
	 * @see application.logic.TaskDAO#update(int)
	 */
	@Override
	public void updateTask(int id) {

	}

	public int getAllTasksCount() {
		return 0;
	}

	public List<String> getAllTaskSummaries() {
		ResultSet rs = null;
		
		//clear out the ObservableList on every method call
		taskSummaries.clear();
		
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT summary from tasks");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				taskSummaries.add(rs.getString("summary"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		return taskSummaries;
	}
	
	private Connection getConnection() {
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:MySQLiteDB.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	@Override
	public Task getTask(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	
	//Probably wont need to keep this around
	public Map<Integer, Task> getTaskMap() {
		Map<Integer, Task> map = new HashMap<Integer, Task>();
		ResultSet rs;
		
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * from tasks");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Task task = new Task(rs.getInt("id"),rs.getString("summary"),rs.getString("description"),rs.getString("due_date"));
				map.put(task.getId(), task);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return map;
	}

	@Override
	public List<Task> getAllTasks() {
		
		System.out.println("Getting all tasks");
		
		ResultSet rs;
		List<Task> tasks = new ArrayList<Task>();
		
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * from tasks");
			rs = ps.executeQuery();
			
			while (rs.next()) {
				Task task = new Task(rs.getInt("id"),rs.getString("summary"),rs.getString("description"),rs.getString("due_date"));
				tasks.add(task);
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return tasks;
	}
}
