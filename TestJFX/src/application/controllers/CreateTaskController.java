package application.controllers;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import application.dao.Task;
import application.helpers.DisplayHelpers;
import application.logic.SceneManager;
import application.logic.TaskManager;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CreateTaskController implements Initializable {

	TaskManager taskManager;
	SceneManager sceneManager;
	private ListProperty<String> listProperty = new SimpleListProperty<>();
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
	ListView<String> taskList;
	@FXML
	TextArea taskDescription;
	@FXML
	TableView<Task> taskTable;
	@FXML
	TableColumn<Task, String> sumCol;
	@FXML
	TableColumn<Task, String> descCol;
	@FXML
	TableColumn<Task, String> dateCol;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taskManager = TaskManager.getInstance();
		sceneManager = SceneManager.getInstance();

		// create executor that uses daemon threads:
		exec = Executors.newCachedThreadPool(runnable -> {
			Thread t = new Thread(runnable);
			t.setDaemon(true);
			return t;
		});

		taskList.itemsProperty().bind(listProperty);
		taskList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		observableTasks = FXCollections.observableArrayList(taskManager.getAllTasks());
		taskTable.setItems(observableTasks);
		// taskTable.setItems(FXCollections.observableArrayList(taskManager.getAllTasks()));
		sumCol.setCellValueFactory(new PropertyValueFactory<>("summary"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		sumCol.setText("Summary");
		descCol.setText("Description");
		dateCol.setText("Due Date");
		refreshListView();
		
	}

	public void createTask() {

		if (checkEmptyFields()) {
			Task task = new Task(taskSummary.getText(), taskText.getText(), dueDatePicker.getValue().toString());
			try {
				taskManager.createTask(task);
			} catch (SQLException e) {
				DisplayHelpers.displayAlert((String.valueOf(e.getErrorCode())), e.getMessage());
				e.printStackTrace();
			}
			refreshListView();
			refreshTableView();
		}

		else {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Required fields missing");
			alert.showAndWait();
		}

	}

	private void refreshTableView() {
		//observableTasks = FXCollections.observableArrayList(taskManager.getAllTasks());
		//taskTable.setItems(observableTasks);
		
		
		javafx.concurrent.Task<List<Task>> taskThread = new javafx.concurrent.Task<List<Task>>() {
			@Override
			public List<Task> call() throws Exception {
				return taskManager.getAllTasks();
			}
		};
		taskThread.setOnSucceeded(e -> {
			System.out.println("Inside new task method");
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

	// Every time an update is made the to the list of tasks refresh the list
	// view
	private void refreshListView() {
		listProperty.set(FXCollections.observableArrayList(taskManager.getAllTaskSummaries()));

	}

	// Display Task description in TextArea
	public void getTaskDescription() {
		// taskDescription.setText(taskManager.getTaskDescriptionByIndex(taskList.getSelectionModel().getSelectedIndex()));

	}

	// Open the Task detail view whenever object is selected in the listView
	public void gotoTaskDetail() {
		/*
		 * int tempIndex = taskList.getSelectionModel().getSelectedIndex();
		 * AnchorPane paneTwo = sceneManager.load(SceneGlobals.paneTwoUrl);
		 * TaskDetailController tdc = sceneManager.getController();
		 * tdc.injectTaskDetails( tempIndex,
		 * taskManager.getTaskSummaryByIndex(tempIndex),
		 * taskManager.getTaskDescriptionByIndex(tempIndex),
		 * taskManager.getTaskDueDateByIndex(tempIndex));
		 * Main.getRoot().setBottom(paneTwo);
		 * 
		 */

	}

	private boolean checkEmptyFields() {
		return (!taskSummary.getText().isEmpty() && !taskText.getText().isEmpty()
				&& !dueDatePicker.getValue().toString().isEmpty());
	}

} // end Class
