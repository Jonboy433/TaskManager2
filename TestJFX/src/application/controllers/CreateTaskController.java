package application.controllers;

import java.lang.Thread.UncaughtExceptionHandler;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import application.dao.Task;
import application.helpers.DisplayHelpers;
import application.logic.SceneManager;
import application.logic.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

public class CreateTaskController implements Initializable {

	TaskManager taskManager;
	SceneManager sceneManager;
	private ObservableList<Task> observableTasks;
	private ExecutorService exec;

	@FXML
	Button BtnCreateTask;
	@FXML
	TextField taskSummary;
	@FXML
	TextArea taskText;
	@FXML
	DatePicker dueDatePicker;
	@FXML
	TableView<Task> taskTable;
	@FXML
	TableColumn<Task, String> sumCol;
	@FXML
	TableColumn<Task, String> descCol;
	@FXML
	TableColumn<Task, String> dateCol;
	@FXML
	TextField summaryField;
	@FXML
	TextArea descField;
	@FXML
	DatePicker dateField;
	@FXML
	Button updateTask;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taskManager = TaskManager.getInstance();
		sceneManager = SceneManager.getInstance();
		setupTableView();

		// create executor that uses daemon threads:
		exec = Executors.newCachedThreadPool(runnable -> {
			Thread t = new Thread(runnable);
			t.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.out.println(t + " throws: " + e);

				}
			});
			t.setDaemon(true);
			return t;
		});

		// set descField to wrap
		descField.setWrapText(true);
	}

	public void createTask() {

		if (checkEmptyFields()) {
			Task task = new Task(taskSummary.getText(), taskText.getText(), dueDatePicker.getValue().toString());
			try {
				taskManager.createTask(task);
			} catch (SQLException e) {
				DisplayHelpers.displayAlert((String.valueOf(e.getErrorCode())), e.getMessage());
				e.printStackTrace();
			} finally {
				clearTaskEntryFields();
			}
			refreshTableView();
		}

		else {
			DisplayHelpers.displayAlert("Required fields missing", null);
		}

	}

	public void deleteTask() {
		System.out.println(Thread.currentThread().getName() + " Delete task called");

		javafx.concurrent.Task<Void> taskThread = new javafx.concurrent.Task<Void>() {
			@Override
			public Void call() throws Exception {
				taskManager.deleteTask(taskTable.getSelectionModel().getSelectedItem().getId());
				refreshTableView();
				return null;
			}
		};

		// Print to standard out if succeeded
		taskThread.setOnSucceeded(e -> {
			System.out.println("Task deleted");
			clearTaskEditFields();
		});

		// if NullPointerException: Prompt to select a task
		// if SQLException: Display generic error message
		taskThread.setOnFailed(e -> {
			if (taskThread.getException() instanceof NullPointerException)
				DisplayHelpers.displayAlert("Please select a Task to delete", taskThread.getException().getMessage());
			if (taskThread.getException() instanceof SQLException)
				DisplayHelpers.displayAlert("Error executing deletion", null);
		});
		exec.execute(taskThread);

	}

	public void updateTask() {
		System.out.println("Update Task called");

		javafx.concurrent.Task<Void> taskThread = new javafx.concurrent.Task<Void>() {
			@Override
			public Void call() throws Exception {
				taskManager.updateTask(taskTable.getSelectionModel().getSelectedItem().getId(), summaryField.getText(),
						descField.getText(), dateField.getValue().toString());
				refreshTableView();
				return null;
			}
		};

		taskThread.setOnSucceeded(e -> {
			System.out.println("Task updated");
		});

		taskThread.setOnFailed(e -> {
			if (taskThread.getException() instanceof NullPointerException)
				System.out.println("Null pointer exception");
			if (taskThread.getException() instanceof SQLException)
				System.out.println("SQL exception: " + taskThread.getException().getMessage());
		});

		exec.execute(taskThread);

	}

	private void refreshTableView() {

		javafx.concurrent.Task<List<Task>> taskThread = new javafx.concurrent.Task<List<Task>>() {
			@Override
			public List<Task> call() throws Exception {
				return taskManager.getAllTasks();
			}
		};
		taskThread.setOnSucceeded(e -> {
			System.out.println("TableView refreshed");
			try {
				observableTasks = FXCollections.observableArrayList(taskThread.get());
				taskTable.setItems(observableTasks);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ExecutionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		exec.execute(taskThread);

	}
	
	private void setupTableView() {
		observableTasks = FXCollections.observableArrayList(taskManager.getAllTasks());
		taskTable.setItems(observableTasks);
		sumCol.setCellValueFactory(new PropertyValueFactory<>("summary"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		sumCol.setText("Summary");
		descCol.setText("Description");
		dateCol.setText("Due Date");
		
		taskTable.setRowFactory(tv -> {
			TableRow<Task> row = new TableRow();

			// Set the fields to include the data from the selected row
			row.setOnMouseClicked(event -> {
				if (event.getButton() == MouseButton.PRIMARY) {
					Task t = row.getItem();
					summaryField.setText(t.getSummary());
					descField.setText(t.getDescription());
					dateField.setValue(LocalDate.parse(t.getDueDate()));

					// Every time a row is selected mark the fields as
					// non-editable
					summaryField.setEditable(false);
					descField.setEditable(false);
					dateField.setDisable(true);
				}

			});

			return row;
		});
	}

	private boolean checkEmptyFields() {
		return (!taskSummary.getText().isEmpty() && !taskText.getText().isEmpty()
				&& !dueDatePicker.getValue().toString().isEmpty());
	}

	public void setEditable() {
		
		if (!taskTable.getSelectionModel().isEmpty()) {
			System.out.println("Task now editable...");
			summaryField.setEditable(true);
			descField.setEditable(true);
			dateField.setDisable(false); 
		}
	}

	private void clearTaskEntryFields() {
		taskSummary.clear();
		taskText.clear();
		dueDatePicker.setValue(null);

	}

	private void clearTaskEditFields() {
		summaryField.clear();
		descField.clear();
		dateField.setValue(null);
	}

} // end Class
