package test;

import application.alert1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FirstGUI extends Application {

	static boolean result;

	@Override
	public void start(Stage primaryStage) {

		primaryStage.setTitle("My First JavaFX GUI");

		Label labelmain = new Label();

		alert1.display("aaa");
		
		
		
		Button buttonmain= new Button("Show");
		buttonmain.setOnAction(e -> {
			alert1.show();
		});

		Button buttonmain1= new Button("Hide");
		buttonmain1.setOnAction(e -> {
			alert1.hide();
		});
		
		
		alert1.display("aaaa", "aaaaa");
		
		
		VBox layout = new VBox(20);
		layout.getChildren().addAll(labelmain, buttonmain, buttonmain1);

		Scene scene1 = new Scene(layout, 300, 250);
		primaryStage.setScene(scene1);

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
