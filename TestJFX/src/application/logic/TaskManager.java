package application.logic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.dao.Task;

public class TaskManager implements TaskDAO {

	private static TaskManager instance = null;
	private Connection conn;

	private TaskManager() {

	}

	public static TaskManager getInstance() {
		if (instance == null) {
			instance = new TaskManager();
		}

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see application.logic.TaskDAO#createTask(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void createTask(Task task) throws SQLException {

		conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(
				String.format("INSERT into tasks('summary','description','due_date') values('%s','%s','%s')",
						task.getSummary(), task.getDescription(), task.getDueDate()));
		ps.executeUpdate();

		conn.close();

	}

	// id = id value of the Task in DB
	/*
	 * (non-Javadoc)
	 * 
	 * @see application.logic.TaskDAO#delete(int)
	 */
	@Override
	public void deleteTask(int id) throws SQLException {
		conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(
				String.format("DELETE from tasks where id='%s'",id));
		ps.executeUpdate();

		conn.close();
	}

	public void updateTask(int id, String summary, String desc, String dueDate) throws SQLException {

		conn = getConnection();
		PreparedStatement ps = conn.prepareStatement(
				String.format("UPDATE tasks " + 
								"SET summary='%s', description='%s',due_date='%s' " +
								"WHERE id='%d'", summary, desc, dueDate, id));
		ps.executeUpdate();
	}
		

	public int getAllTasksCount() {
		return 0;
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

	@Override
	public List<Task> getAllTasks() {

		System.out.println("Task Manager - Getting all tasks");

		ResultSet rs;
		List<Task> tasks = new ArrayList<Task>();

		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * from tasks");
			rs = ps.executeQuery();

			while (rs.next()) {
				Task task = new Task(rs.getInt("id"), rs.getString("summary"), rs.getString("description"),
						rs.getString("due_date"));
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
	
	//Adding for new feature
	@Override
	public List<Task> getAllTasksHistory() {

		System.out.println("Task Manager - Getting all historical tasks");

		ResultSet rs;
		List<Task> tasks = new ArrayList<Task>();

		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("SELECT * from tasks_history");
			rs = ps.executeQuery();

			while (rs.next()) {
				Task task = new Task(rs.getInt("id"), rs.getString("summary"), rs.getString("description"),
						rs.getString("due_date"));
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
