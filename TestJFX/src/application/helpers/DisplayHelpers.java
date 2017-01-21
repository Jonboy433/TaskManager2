package application.helpers;

import javafx.scene.control.Alert;

public class DisplayHelpers {
	
	public static void displayAlert(String header, String content) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("ERROR");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
