package application.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.logic.SceneGlobals;
import application.logic.SceneManager;
import javafx.fxml.Initializable;

public class MenuBarController implements Initializable {
	
	SceneManager sceneManager;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sceneManager = SceneManager.getInstance();
	}

	public void gotoMain() {
		sceneManager.switchScene(SceneGlobals.paneOneUrl);
	}
	
	public void gotoHistory() {
		sceneManager.switchScene(SceneGlobals.paneTwoUrl);
	}
	
	public void Exit() {
		System.exit(0);
	}
}
