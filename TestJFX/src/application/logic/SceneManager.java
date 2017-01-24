package application.logic;

import java.io.IOException;
import java.net.URL;

import application.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class SceneManager {

	private static SceneManager instance = null;
	private FXMLLoader loader;
	private URL currentSceneURL;

	protected SceneManager() {
		//Set the current scene on startup to the main page
		currentSceneURL = SceneGlobals.paneOneUrl;
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
			System.out.println("Storing current scene: " + sceneUrl);
			currentSceneURL = sceneUrl;
			return loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void switchScene(URL sceneUrl) {

		//If you attempt to load the current scene: do nothing to avoid calling controller init()/ Otherwise load the new scene
		if (sceneUrl == currentSceneURL)
			System.out.println("Same scene - doing nothing");
		else {
			AnchorPane newPane = this.load(sceneUrl);
			Main.getRoot().setBottom(newPane);
		}
	}

	public void getCurrentScene() {
		System.out.println(loader.getLocation());
	}

}
