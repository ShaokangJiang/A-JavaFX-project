package application;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import javafx.scene.layout.BorderPane;
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

	private static final String dayPattern = "yyyy-MM-dd";
	private static final SimpleDateFormat dateDayFormat = new SimpleDateFormat(
			dayPattern);
	private static Date startDate;
	private static Date endDate;

	public static Date[] displayDateRange() {
		// TODO Auto-generated method stub
		Dialog<Pair<Date, Date>> dialog = new Dialog<>();
		dialog.setTitle("Choice window");
		dialog.setHeaderText("Please choose the date range: ");

		ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		BorderPane pane = new BorderPane();
		Label tmp = new Label(
				"Please use the formate as YYYY-MM-DD. Otherwise, program is not able to recognize");
		pane.setTop(tmp);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField start = new TextField();
		start.setPromptText("Start date");
		TextField end = new TextField();
		end.setPromptText("End date");

		grid.add(new Label("Start date:"), 0, 0);
		grid.add(start, 0, 1);
		grid.add(new Label("End date:"), 1, 0);
		grid.add(end, 1, 1);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		start.textProperty().addListener((observable, oldValue, newValue) -> {
			if(validateDate(newValue,end.getText())) {
				loginButton.setDisable(false);
				tmp.setText("");
			}else {
				tmp.setText("Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
				loginButton.setDisable(true);
			}
		});

		end.textProperty().addListener((observable, oldValue, newValue) -> {
			if(validateDate(start.getText(),newValue)) {
				loginButton.setDisable(false);
				tmp.setText("");
			}else {
				tmp.setText("Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
				loginButton.setDisable(true);
			}
		});

		pane.setBottom(grid);
		dialog.getDialogPane().setContent(pane);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				return new Pair<>(startDate, endDate);
			}
			return null;
		});
		Pair<Date, Date> result = dialog.showAndWait().get();

		System.out.print(result.getKey() + "   " + result.getValue());

		return new Date[] { result.getKey(), result.getValue() };

	}

	private static Boolean validateDate(String start, String end) {
		try {
			startDate = dateDayFormat.parse(start);
			endDate = dateDayFormat.parse(end);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	//FIXME
	public static Object[] displayAddFarmer() {
		// TODO Auto-generated method stub
		Dialog<Pair<Date, Date>> dialog = new Dialog<>();
		dialog.setTitle("Choice window");
		dialog.setHeaderText("Please choose the date range: ");

		ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		BorderPane pane = new BorderPane();
		Label tmp = new Label(
				"Please use the formate as YYYY-MM-DD. Otherwise, program is not able to recognize");
		pane.setTop(tmp);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField start = new TextField();
		start.setPromptText("Start date");
		TextField end = new TextField();
		end.setPromptText("End date");

		grid.add(new Label("Start date:"), 0, 0);
		grid.add(start, 0, 1);
		grid.add(new Label("End date:"), 1, 0);
		grid.add(end, 1, 1);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		start.textProperty().addListener((observable, oldValue, newValue) -> {
			if(validateDate(newValue,end.getText())) {
				loginButton.setDisable(false);
				tmp.setText("");
			}else {
				tmp.setText("Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
				loginButton.setDisable(true);
			}
		});

		end.textProperty().addListener((observable, oldValue, newValue) -> {
			if(validateDate(start.getText(),newValue)) {
				loginButton.setDisable(false);
				tmp.setText("");
			}else {
				tmp.setText("Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
				loginButton.setDisable(true);
			}
		});

		pane.setBottom(grid);
		dialog.getDialogPane().setContent(pane);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				return new Pair<>(startDate, endDate);
			}
			return null;
		});
		Pair<Date, Date> result = dialog.showAndWait().get();

		System.out.print(result.getKey() + "   " + result.getValue());

		return new Date[] { result.getKey(), result.getValue() };

	}
	
	//FIXME
		public static Object[] displayRemoveFarmer(Integer[] integers) {
			// TODO Auto-generated method stub
			Dialog<Pair<Date, Date>> dialog = new Dialog<>();
			dialog.setTitle("Choice window");
			dialog.setHeaderText("Please choose the date range: ");

			ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().addAll(OKButton,
					ButtonType.CANCEL);

			BorderPane pane = new BorderPane();
			Label tmp = new Label(
					"Please use the formate as YYYY-MM-DD. Otherwise, program is not able to recognize");
			pane.setTop(tmp);

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			TextField start = new TextField();
			start.setPromptText("Start date");
			TextField end = new TextField();
			end.setPromptText("End date");

			grid.add(new Label("Start date:"), 0, 0);
			grid.add(start, 0, 1);
			grid.add(new Label("End date:"), 1, 0);
			grid.add(end, 1, 1);

			Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
			loginButton.setDisable(true);

			start.textProperty().addListener((observable, oldValue, newValue) -> {
				if(validateDate(newValue,end.getText())) {
					loginButton.setDisable(false);
					tmp.setText("");
				}else {
					tmp.setText("Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
					loginButton.setDisable(true);
				}
			});

			end.textProperty().addListener((observable, oldValue, newValue) -> {
				if(validateDate(start.getText(),newValue)) {
					loginButton.setDisable(false);
					tmp.setText("");
				}else {
					tmp.setText("Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
					loginButton.setDisable(true);
				}
			});

			pane.setBottom(grid);
			dialog.getDialogPane().setContent(pane);

			dialog.setResultConverter(dialogButton -> {
				if (dialogButton == OKButton) {
					return new Pair<>(startDate, endDate);
				}
				return null;
			});
			Pair<Date, Date> result = dialog.showAndWait().get();

			System.out.print(result.getKey() + "   " + result.getValue());

			return new Date[] { result.getKey(), result.getValue() };

		}

}
