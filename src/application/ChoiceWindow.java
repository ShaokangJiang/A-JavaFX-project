package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * A simple window to let user choose data range
 * 
 * @author shaokang
 *
 */
public class ChoiceWindow {


	public static String[] display() {
		// TODO Auto-generated method stub
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Please choose the date range: Please use the formate as YYYY-MM-DD. Otherwise, program is not able to recognize");


		ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField start = new TextField();
		start.setPromptText("Start date");
		TextField end = new TextField();
		end.setPromptText("End date");

		grid.add(new Label("Start date:"), 0, 0);
		grid.add(start, 1, 0);
		grid.add(new Label("End date:"), 0, 1);
		grid.add(end, 1, 1);


		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		start.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		
		
		dialog.getDialogPane().setContent(grid);
		
		

		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == OKButton) {
		        return new Pair<>(start.getText(), end.getText());
		    }
		    return null;
		});
		Pair<String, String> result = dialog.showAndWait().get();

		System.out.print(result.getKey()+"   "+result.getValue());
		
		return new String[] {result.getKey(), result.getValue()};
		
	}
	
	

}
