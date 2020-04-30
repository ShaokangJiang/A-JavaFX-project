package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * An alert communicate interface
 * 
 * @author shaokang
 *
 */
public class alert1 {
	
	public static void display(String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}
	
	
	public static void display(String header, String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION, message, ButtonType.OK);
		alert.setHeaderText(header);
		alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
		alert.showAndWait();
	}
}
