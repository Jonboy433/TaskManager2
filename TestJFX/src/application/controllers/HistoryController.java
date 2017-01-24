package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.dao.Task;
import application.logic.SceneGlobals;
import application.logic.SceneManager;
import application.logic.TaskManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class HistoryController implements Initializable {
	
	private TaskManager taskManager;
	private SceneManager sceneManager;
	private ObservableList<Task> observableTasks;
	
	@FXML
	TableView<Task> historyTable;
	@SuppressWarnings("rawtypes")
	@FXML
	TableColumn sumCol;
	@SuppressWarnings("rawtypes")
	@FXML
	TableColumn descCol;
	@SuppressWarnings("rawtypes")
	@FXML
	TableColumn dateCol;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		taskManager = TaskManager.getInstance();
		sceneManager = SceneManager.getInstance();
		setupTableView();
		
	}
	
	public void gotoMain() {
		System.out.println("gotoMain clicked");
		sceneManager.switchScene(SceneGlobals.paneOneUrl);
	}
	
	private void setupTableView() {
		observableTasks = FXCollections.observableArrayList(taskManager.getAllTasksHistory());
		historyTable.setItems(observableTasks);
		sumCol.setCellValueFactory(new PropertyValueFactory<>("summary"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
		sumCol.setText("Summary");
		descCol.setText("Description");
		dateCol.setText("Due Date");
		
		historyTable.setRowFactory(tv -> {
			TableRow<Task> row = new TableRow();

			// Set the fields to include the data from the selected row
			row.setOnMouseClicked(event -> {
				System.out.println("Inside history table event");
				/* if (event.getButton() == MouseButton.PRIMARY) {
					Task t = row.getItem();
					summaryField.setText(t.getSummary());
					descField.setText(t.getDescription());
					dateField.setValue(LocalDate.parse(t.getDueDate()));

					// Every time a row is selected mark the fields as
					// non-editable
					summaryField.setEditable(false);
					descField.setEditable(false);
					dateField.setDisable(true);
				} */

			});

			return row;
		});
	}

}
