package application.main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public final class SceneManager {

	public SceneManager() {
		
	}
	
	public void openScene(Scene scene){
		Parent root;
		
		try {
			root = FXMLLoader.load(getClass().getResource("Main.fxml"));
			scene = new Scene(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
