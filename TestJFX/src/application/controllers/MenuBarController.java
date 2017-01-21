package application.controllers;

import java.io.IOException;

import application.logic.SceneGlobals;
import application.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class MenuBarController {

	public void Exit() {
		System.exit(0);
	}
	
	public void gotoPaneOne() {
		
		try {
			AnchorPane paneOne = FXMLLoader.load(SceneGlobals.paneOneUrl);
			Main.getRoot().setBottom(paneOne);
		}
		
		catch (IOException e) {
			System.out.println("Caught exception");
		}  
	}
	
	public void gotoPaneTwo() {
		try {
			AnchorPane paneTwo = FXMLLoader.load(SceneGlobals.paneTwoUrl);
			Main.getRoot().setBottom(paneTwo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
