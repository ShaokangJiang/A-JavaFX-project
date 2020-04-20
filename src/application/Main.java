package application;

import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {

  protected ImportExportWindow importExportWindow;
  protected Notification noti;
  protected FarmerManager Magnager;
  
  @Override
  public void start(Stage s) throws Exception {
    // TODO Auto-generated method stub
	  Button buttonM = new Button("Select Files");

		TextField path = new TextField();
		path.setPromptText("The path...");

		buttonM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
			}
		});

		BorderPane pane = new BorderPane();
		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		grid.setAlignment(Pos.CENTER);

		grid.add(new Label("Path:"), 0, 0);
		grid.add(path, 1, 0);
		grid.add(buttonM, 2, 0);
		
		pane.setRight(grid);
		
		Scene scene = new Scene(pane, 600, 400);
        s.setScene(scene); 
  
        s.show(); 
  }

  public static void main(String[] args) {
    launch();
  }
}
