package application;

import java.util.Date;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

public class Notification {
   
	
	public static void display(BorderPane toAdd) {
		
		if(toAdd == null) {
			alert1.display("Uninitialize happen", "Field might not be initialized. <Br> Please import data first");
			return;
		}
		// TODO Auto-generated method stub
		Stage s = new Stage();
		Scene scene = new Scene(toAdd, 600, 400);
		s.setScene(scene);

		s.show();
	}
	


}
