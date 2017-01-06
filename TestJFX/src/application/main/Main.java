package application.main;
	
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;




public class Main extends Application {
	
	
	private static BorderPane root = new BorderPane();
	
	public static BorderPane getRoot() {
		return root;
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			URL menuBarUrl = getClass().getResource("/application/fxml/Menu.fxml");
			MenuBar menuBar = FXMLLoader.load(menuBarUrl);
			
			URL paneOneUrl = getClass().getResource("/application/fxml/PaneOne.fxml");
			AnchorPane anchorPane = FXMLLoader.load(paneOneUrl);
			
			root.setTop(menuBar);
			root.setBottom(anchorPane);
			Scene scene = new Scene(root, 640, 480);
			
			
		
		
			primaryStage.setScene(scene);
			primaryStage.setTitle("Task Manager Pro");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
