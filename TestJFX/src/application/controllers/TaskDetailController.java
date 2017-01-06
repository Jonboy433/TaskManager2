package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.logic.SceneGlobals;
import application.logic.SceneManager;
import application.logic.TaskManager;
import application.main.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class TaskDetailController implements Initializable {

	TaskManager taskManager; //Reference to the task manager
	private int taskIndex; //Store the index from the listView in case you want to update the task
	private SceneManager sceneManager;
	
	@FXML
	TextField taskDetailSummary;
	
	@FXML
	TextArea taskDetailDesc;
	
	@FXML
	DatePicker taskDetailDueDate;
	
	@FXML
	Button backBtn;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		taskManager = TaskManager.getInstance();
		sceneManager = SceneManager.getInstance();
		taskDetailDueDate.setDisable(true);
		
	}

	public void injectTaskDetails(int taskIndex, String summary, String description, String dueDate) {
		this.taskIndex = taskIndex;
		taskDetailSummary.setText(summary);
		taskDetailDesc.setText(description);
		taskDetailDueDate.setValue(LocalDate.parse(dueDate));
	}
	
	public void loadTaskListView() {
		
		/* try {
			//AnchorPane paneOne = FXMLLoader.load(SceneGlobals.paneOneUrl);
			
			Main.getRoot().setBottom(paneOne);
		}
		
		catch (IOException e) {
			System.out.println("Caught exception");
		}  */
		
		AnchorPane paneOne = sceneManager.load(SceneGlobals.paneOneUrl);
		Main.getRoot().setBottom(paneOne);
	}
}
