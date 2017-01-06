package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import application.logic.SceneGlobals;
import application.logic.SceneManager;
import application.logic.TaskManager;
import application.main.Main;
import application.models.Task;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class CreateTaskController implements Initializable {
	
	TaskManager taskManager;
	SceneManager sceneManager;
	
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
	
	private ListProperty<String> listProperty = new SimpleListProperty<>();

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		taskManager = TaskManager.getInstance();
		sceneManager = SceneManager.getInstance();
		taskList.itemsProperty().bind(listProperty);
		taskList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		refreshListView();
	}
	

	public void createTask() {
		Task task = new Task(taskSummary.getText(), taskText.getText(),Date.valueOf(dueDatePicker.getValue()));
		taskManager.addTask(task);
		refreshListView();

	}
	
	//Every time an update is made the to the list of tasks refresh the list view
	private void refreshListView() {
		listProperty.set(taskManager.getAllTaskSummaries());
	}

	//Display Task description in TextArea
	public void getTaskDescription() {
		taskDescription.setText(taskManager.getTaskDescriptionByIndex(taskList.getSelectionModel().getSelectedIndex()));
	}
	
	//Open the Task detail view whenever object is selected in the listView
	public void gotoTaskDetail() {

			int tempIndex = taskList.getSelectionModel().getSelectedIndex();
			AnchorPane paneTwo = sceneManager.load(SceneGlobals.paneTwoUrl);
			TaskDetailController tdc = sceneManager.getController();
			tdc.injectTaskDetails(	tempIndex, 
									taskManager.getTaskSummaryByIndex(tempIndex), 
									taskManager.getTaskDescriptionByIndex(tempIndex), 
									taskManager.getTaskDueDateByIndex(tempIndex));
			Main.getRoot().setBottom(paneTwo);
		
	}
	
}
