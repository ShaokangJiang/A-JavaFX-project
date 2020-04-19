package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * An alert communicate interface
 * 
 * @author shaokang
 *
 */
public class alert1 {
	
	public static void display(String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setContentText(message);
 
        alert.showAndWait();
	}
	
	
	public static void display(String header, String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setHeaderText(header);
        alert.setContentText(message);
 
        alert.showAndWait();
	}
}
