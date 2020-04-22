package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * An import/export window With an example, but most of
 * 
 * @author shaokang
 *
 */
public class ImportExportWindow extends Application {

	static protected FileChooser fileChooser;
	//static protected String path1 = System.getProperty("user.home");
	static protected String path1 = System.getProperty("user.home");
	/**
	 * Show this import window
	 * 
	 * 
	 * @param i int to indicate this is in import model or export model
	 */
	public static DataFrameIndex Displayimport(Stage s) {
		// TODO Auto-generated method stub
		Dialog<DataFrameIndex> dialog = new Dialog<>();

		fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Comma-Separated Values",
						"*.csv"));
		fileChooser.setTitle("Choose files path...");
		fileChooser.setInitialDirectory(new File(path1));

		Button buttonM = new Button("Select Files");

		TextField path = new TextField();
		path.setPromptText("The path...");

		buttonM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				List<File> files = fileChooser.showOpenMultipleDialog(s);
				if (files == null || files.isEmpty()) {
					return;
				}
				path1 = files.get(0).getParent();
				fileChooser.setInitialDirectory(new File(path1));
				for (File file : files) {
					path.appendText(file.getAbsolutePath() + ";");
				}
				path.setText(path.getText().substring(0,
						path.getText().length() - 1));
			}
		});

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		grid.setAlignment(Pos.CENTER);

		grid.add(new Label("Path:"), 0, 0);
		grid.add(path, 1, 0);
		grid.add(buttonM, 2, 0);

		ButtonType OKButton = new ButtonType("Import", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		path.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				try {
					return Import(path.getText());
				} catch (FileNotFoundException | RuntimeException e) {
					// TODO Auto-generated catch block
					alert1.display("Please import valid data:" + e.getMessage());
					return null;
				}
			}
			return null;
		});

		dialog.setTitle("Import Files chosen Dialog");
		dialog.setHeaderText("Choose path: ");

		dialog.getDialogPane().setContent(grid);
		return dialog.showAndWait().get();
	}

	/**
	 * Show this import window
	 * 
	 * 
	 * @param i int to indicate this is in import model or export model
	 */
	public static Boolean DisplayExport(Stage s, DataFrame k) {
		// TODO Auto-generated method stub
		Dialog<Boolean> dialog = new Dialog<>();

		fileChooser = new FileChooser();

		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Comma-Separated Values",
						"*.csv"));
		fileChooser.setTitle("Save as...");
		fileChooser.setInitialDirectory(new File(path1));
		String toWrite = "Result.csv";
		if (new File(path1 + File.separator +toWrite).exists()) {
			toWrite = "Result-" + new Date().getTime() + ".csv";
		}
		fileChooser.setInitialFileName(toWrite);

		Button buttonM = new Button("Create File");

		TextField path = new TextField();
		path.setPromptText("The path...");

		buttonM.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				File file = fileChooser.showSaveDialog(s);
				if (file == null) {
					return;
				}
				path.setText(file.getAbsolutePath());
				path1 = file.getParent();
				fileChooser.setInitialDirectory(new File(path1));
			}
		});

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		grid.setAlignment(Pos.CENTER);

		grid.add(new Label("Path:"), 0, 0);
		grid.add(path, 1, 0);
		grid.add(buttonM, 2, 0);

		ButtonType OKButton = new ButtonType("Export", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		path.textProperty().addListener((observable, oldValue, newValue) -> {
			loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				try {
					return export(k, path.getText());
				} catch (RuntimeException | IOException e) {
					// TODO Auto-generated catch block
					alert1.display("Error happen: \n" + e.getMessage());
					return false;
				}
			}
			return false;
		});

		dialog.setTitle("Export Path chosen Dialog");
		dialog.setHeaderText(
				"The import panel, please choose a file to be written");

		dialog.getDialogPane().setContent(grid);
		return dialog.showAndWait().get();
	}

	/**
	 * put the passed in ds to the path, if path doesn;t contain filename, use
	 * export.csv if file already exist, use path+Date.currentTime.
	 * 
	 * @param ds the dataframe being exported
	 * @throws IOException
	 * @throws RuntimeException
	 */
	public static boolean export(DataFrame ds, String path)
			throws RuntimeException, IOException {
		CSVFileReader.writeToCSV(ds, new File(path));
		return true;
	}

	/**
	 * Should detect of the path is multiple paths("C:/a.a;C:/b.a") or not If it
	 * is, read each csv in each position and merge them into one dataframe then
	 * set protected path to the first path in the list , if any of file doesn;t
	 * meet the requirement(error file) prompt the user then return null
	 * 
	 * 
	 * @param path path or path list to files
	 * @return the DataFrame contains all information
	 * @throws RuntimeException
	 * @throws FileNotFoundException
	 */
	public static DataFrameIndex Import(String path)
			throws FileNotFoundException, RuntimeException {
		String[] toRead = path.split(";");
		File[] tmp = new File[toRead.length];
		for (int i = 0; i < toRead.length; i++) {
			tmp[i] = new File(toRead[i]);
		}
		return CSVFileReader.readCSV(tmp);
	}

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub

		primaryStage.setTitle("JavaFX GUI");

		Label labelmain = new Label();

		Button buttonmain = new Button("Displayimport");
		buttonmain.setOnAction(e -> {
			try {
				ImportExportWindow.Displayimport(primaryStage);
			} catch (Exception as) {

			}
		});

		Button buttonmain1 = new Button("Displayexport");
		buttonmain1.setOnAction(e -> {
			try {
				ImportExportWindow.DisplayExport(primaryStage, null);
			} catch (Exception as) {

			}
		});

		VBox layout = new VBox(20);
		layout.getChildren().addAll(labelmain, buttonmain, buttonmain1);

		Scene scene1 = new Scene(layout, 300, 250);
		primaryStage.setScene(scene1);

		primaryStage.show();

	}

}
