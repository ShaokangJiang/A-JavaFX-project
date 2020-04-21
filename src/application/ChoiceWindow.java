package application;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
			if (validateDate(newValue, end.getText())) {
				loginButton.setDisable(false);
				tmp.setText("");
			} else {
				tmp.setText(
						"Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
				loginButton.setDisable(true);
			}
		});

		end.textProperty().addListener((observable, oldValue, newValue) -> {
			if (validateDate(start.getText(), newValue)) {
				loginButton.setDisable(false);
				tmp.setText("");
			} else {
				tmp.setText(
						"Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
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

		//System.out.print(result.getKey() + "   " + result.getValue());

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

	// FIXME
	public static Object[] displayAddFarmer(Set<Integer> integers) {
		Dialog<Object[]> dialog = new Dialog<>();
		dialog.setTitle("Choice window");
		dialog.setHeaderText("Please choose Farmer, day, weight to delete: ");

		ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		BorderPane pane = new BorderPane();
		Label tmp = new Label("Select property:");
		pane.setTop(tmp);
		pane.setMinHeight(Region.USE_PREF_SIZE);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField id = new TextField();
		id.setPromptText("Farm ID");
		TextField Day = new TextField();
		Day.setPromptText("Day(2019-01-01)...");
		TextField weight = new TextField();
		weight.setPromptText("Weight...");

		id.textProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if (!integers.contains(Integer.parseInt(newValue))) {
					tmp.setText("Notice: " + newValue
							+ " is a new Farm id. It will be added to the table. ");
				}
			} catch (Exception e) {
				tmp.setText("Please input a number as id");
			}
		});

		grid.add(new Label("Farmer_id"), 0, 0);
		grid.add(id, 1, 0);
		grid.add(new Label("Date"), 0, 1);
		grid.add(Day, 1, 1);
		grid.add(new Label("Weight"), 0, 2);
		grid.add(weight, 1, 2);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		Day.textProperty().addListener((observable, oldValue, newValue) -> {
			if (validateDate(newValue)) {

				tmp.setText("");
				if (weight.getText().matches("\\d*")
						&& weight.getText().compareTo("0") != 0
						&& !weight.getText().isEmpty()) {
					loginButton.setDisable(false);
				}
			} else {
				tmp.setText(
						"Date should be in format: yyyy-MM-dd, e.g. 2019-01-01");
				loginButton.setDisable(true);
			}
		});

		weight.textProperty().addListener((observable, oldValue, newValue) ->

		{
			if (newValue.matches("\\d*") && validateDate(Day.getText())) {
				if (weight.getText().compareTo("0") != 0
						&& !weight.getText().isEmpty())
					loginButton.setDisable(false);
			} else if (validateDate(Day.getText())) {
				weight.setText(newValue.replaceAll("[^\\d]", ""));
				if (!weight.getText().isEmpty()
						&& weight.getText().compareTo("0") != 0)
					loginButton.setDisable(false);
			} else {
				loginButton.setDisable(true);
			}
		});

		pane.setBottom(grid);
		dialog.getDialogPane().setContent(pane);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				return new Object[] { Day.getText(),
						Integer.parseInt(id.getText()),
						Integer.parseInt(weight.getText()) };
			}
			return null;
		});
		Object[] result = dialog.showAndWait().get();

		return result;
	}

	private static boolean validateDate(String newValue) {
		// TODO Auto-generated method stub
		return validateDate(newValue, newValue);
	}

	private static ComboBox<Integer> ID;
	private static ComboBox<String> day;
	private static int maxWeight;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object[] displayReduceFarmer(Integer[] integers,
			FarmerManager Manager) {
		if (integers.length == 0)
			alert1.display("No data to choose");
		// TODO Auto-generated method stub
		Dialog<Object[]> dialog = new Dialog<>();
		dialog.setTitle("Choice window");
		dialog.setHeaderText("Please choose Farmer, day, weight to delete: ");

		ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		BorderPane pane = new BorderPane();
		Label tmp = new Label("Select property:");
		pane.setTop(tmp);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ID = new ComboBox<Integer>(FXCollections.observableArrayList(integers));
		day = new ComboBox<String>(
				FXCollections.observableArrayList(new String[] { "" }));
		TextField weight = new TextField("0");

		maxWeight = 0;

		ID.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue ov, Object t,
							Object t1) {
						List<String> toUse = new ArrayList<String>();
						DateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						for (Date tmp : Manager.findFarmer((Integer) t1)
								.getWeightByDay().keySet()) {
							toUse.add(dateFormat.format(tmp));
						}
						day.setItems(FXCollections.observableList(toUse));
					}
				});

		day.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue ov, Object t,
							Object t1) {
						String dayPattern = "yyyy-MM-dd";
						SimpleDateFormat dateDayFormat = new SimpleDateFormat(
								dayPattern);
						try {
							maxWeight = Manager.findFarmer(ID.getValue())
									.getWeightByDay()
									.get(dateDayFormat.parse((String) t1));
						} catch (ParseException e) {
						}
					}
				});
		grid.add(new Label("Farmer_id"), 0, 0);
		grid.add(ID, 1, 0);
		grid.add(new Label("Date"), 0, 1);
		grid.add(day, 1, 1);
		grid.add(new Label("Weight"), 0, 2);
		grid.add(weight, 1, 2);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		weight.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				weight.setText(newValue.replaceAll("[^\\d]", ""));
			}
			if (!weight.getText().isEmpty()) {
				if (Integer.parseInt(weight.getText()) > maxWeight) {
					weight.setText("" + maxWeight);
					tmp.setText(ID.getValue() + " only have " + maxWeight
							+ " gals of milk");
				}
				loginButton.setDisable(false);

			}
			if (maxWeight == 0)
				loginButton.setDisable(true);

		});

		pane.setBottom(grid);
		dialog.getDialogPane().setContent(pane);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				return new Object[] { day.getValue(), ID.getValue(),
						Integer.parseInt(weight.getText()) };
			}
			return null;
		});
		Object[] result = dialog.showAndWait().get();

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object[] displayRemoveFarmerDay(Integer[] integers,
			FarmerManager Manager) {
		if (integers.length == 0)
			alert1.display("No data to choose");
		// TODO Auto-generated method stub
		Dialog<Object[]> dialog = new Dialog<>();
		dialog.setTitle("Choice window");
		dialog.setHeaderText("Please choose Farmer, day, weight to delete: ");

		ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		BorderPane pane = new BorderPane();
		Label tmp = new Label("Select property:");
		pane.setTop(tmp);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ID = new ComboBox<Integer>(FXCollections.observableArrayList(integers));
		day = new ComboBox<String>(
				FXCollections.observableArrayList(new String[] { "" }));

		ID.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue ov, Object t,
							Object t1) {
						List<String> toUse = new ArrayList<String>();
						DateFormat dateFormat = new SimpleDateFormat(
								"yyyy-MM-dd");
						for (Date tmp : Manager.findFarmer((Integer) t1)
								.getWeightByDay().keySet()) {
							toUse.add(dateFormat.format(tmp));
						}
						day.setItems(FXCollections.observableList(toUse));
					}
				});

		grid.add(new Label("Farmer_id"), 0, 0);
		grid.add(ID, 1, 0);
		grid.add(new Label("Date"), 0, 1);
		grid.add(day, 1, 1);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		day.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue ov, Object t,
							Object t1) {
						loginButton.setDisable(false);
					}
				});

		pane.setBottom(grid);
		dialog.getDialogPane().setContent(pane);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				return new Object[] { day.getValue(), ID.getValue() };
			}
			return null;
		});
		Object[] result = dialog.showAndWait().get();

		return result;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Integer displayRemoveFarmer(Integer[] integers,
			FarmerManager Manager) {
		if (integers.length == 0)
			alert1.display("No data to choose");
		// TODO Auto-generated method stub
		Dialog<Integer> dialog = new Dialog<>();
		dialog.setTitle("Choice window");
		dialog.setHeaderText("Please choose Farmer, day, weight to delete: ");

		ButtonType OKButton = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(OKButton,
				ButtonType.CANCEL);

		BorderPane pane = new BorderPane();
		Label tmp = new Label("Select property:");
		pane.setTop(tmp);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		ID = new ComboBox<Integer>(FXCollections.observableArrayList(integers));

		grid.add(new Label("Farmer_id"), 0, 0);
		grid.add(ID, 1, 0);

		Node loginButton = dialog.getDialogPane().lookupButton(OKButton);
		loginButton.setDisable(true);

		ID.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener() {
					@Override
					public void changed(ObservableValue ov, Object t,
							Object t1) {
						loginButton.setDisable(false);
					}
				});

		pane.setBottom(grid);
		dialog.getDialogPane().setContent(pane);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == OKButton) {
				return ID.getValue();
			}
			return null;
		});
		Integer result = dialog.showAndWait().get();

		return result;
	}

}
