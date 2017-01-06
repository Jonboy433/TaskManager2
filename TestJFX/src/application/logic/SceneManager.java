package application.logic;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;

public class SceneManager {
	

	private static SceneManager instance = null;
	private FXMLLoader loader;
	
	protected SceneManager() {

	}

	public static SceneManager getInstance() {
		if (instance == null) {
			instance = new SceneManager();
		}
		
		return instance;
	}
	
	public FXMLLoader getLoader() {
		return loader;
	}
	
	public <T> T getController() {
		return loader.getController();
	}
	
	public <T> T load(URL sceneUrl) {
		loader = new FXMLLoader(sceneUrl);
		try {
			return loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

}
