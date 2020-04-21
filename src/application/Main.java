package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {

	protected FarmerManager Manager;
	private static String[] argument;

	/**
	 * This method should actually handle argument
	 */
	private void initialize() {
		// TODO Auto-generated method stub
		Manager = new FarmerManager();
	}

	@Override
	public void start(Stage s) throws Exception {
		// TODO Auto-generated method stub
		initialize();
		BorderPane pane = new BorderPane();
		
		// ObservableList<Object[]> data
		// = FXCollections.observableArrayList(Manager.ds.rows);

		ObservableList<Object[]> data = FXCollections.observableArrayList();

		TableColumn<Object[], String> time = new TableColumn<Object[], String>(
				"Time");
		time.setCellValueFactory(
				new Callback<CellDataFeatures<Object[], String>, ObservableValue<String>>() {
					public ObservableValue<String> call(
							CellDataFeatures<Object[], String> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return new SimpleStringProperty(
								(String) p.getValue()[0]);
					}
				});

		TableColumn<Object[], Integer> id = new TableColumn<Object[], Integer>(
				"ID");
		id.setCellValueFactory(
				new Callback<CellDataFeatures<Object[], Integer>, ObservableValue<Integer>>() {
					public ObservableValue<Integer> call(
							CellDataFeatures<Object[], Integer> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return new ReadOnlyObjectWrapper<>(
								(Integer) p.getValue()[1]);
					}
				});

		TableColumn<Object[], Integer> weight = new TableColumn<Object[], Integer>(
				"weight");
		weight.setCellValueFactory(
				new Callback<CellDataFeatures<Object[], Integer>, ObservableValue<Integer>>() {
					public ObservableValue<Integer> call(
							CellDataFeatures<Object[], Integer> p) {
						// p.getValue() returns the Person instance for a
						// particular TableView row
						return new ReadOnlyObjectWrapper<>(
								(Integer) p.getValue()[2]);
					}
				});

		TableView<Object[]> tableview = new TableView<Object[]>();

		tableview.setItems(data);

		tableview.getColumns().addAll(time, id, weight);

		pane.setLeft(tableview);

		Button importButton = new Button("Import data");
		importButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DataFrameIndex tmp = null;
				try {
					tmp = ImportExportWindow.Displayimport(s);
				} catch (Exception as) {
				}

				if (tmp != null) {
					try {
						if (!Manager.importData(tmp)) {
							alert1.display(Manager.getError());
						}
						ObservableList<Object[]> data = FXCollections
								.observableArrayList(
										exceptNullRow(Manager.ds.rows));
						tableview.setItems(data);
					} catch (Exception e) {
						alert1.display(
								"Unknow event happen, program will close",
								e.getMessage());
						System.exit(1);
					}
				}
			}
		});

		Button exportButton = new Button("Export data");
		exportButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (ImportExportWindow.DisplayExport(s, Manager.ds)) {
						alert1.display("Successfully output",
								"A result file representing all information is generated to "
										+ ImportExportWindow.path1);
					}
					;
				} catch (Exception as) {
				}
			}
		});

		Button generate_report = new Button("Generate report");
		generate_report.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final String[] tmp = new String[] { "Farm report",
						"Range Report", "Monthly Report", "Annual Report" };
				BorderPane show;
				Report report;
				switch (selectFun(tmp)) {
				case "Farm report":
					report = Manager.generateFarmReport();
					if (report == null) {
						alert1.display("Error hapen!", Manager.getError());
						show = null;
						return;
					} else
						show = ((FARM_REPORT) report).Analize();
					break;
				case "Range Report":
					Date[] pass = null;
					try {
						pass = ChoiceWindow.displayDateRange();
					} catch (Exception e) {
						alert1.display("You didn't choose any range!");
						return;
					}
					report = Manager.generateDateRangeReport(pass[0], pass[1]);
					if (report == null) {
						alert1.display("Error hapen!", Manager.getError());
						show = null;
						return;
					} else
						show = ((DATE_RANGE_REPORT) report).Analize();
					break;
				case "Monthly Report":
					report = Manager.generateMonthReport();
					if (report == null) {
						alert1.display("Error hapen!", Manager.getError());
						show = null;
						return;
					} else
						show = ((MONTHLY_REPORT) report).Analize();
					break;
				case "Annual Report":
					report = Manager.generateAnnualReport();
					if (report == null) {
						alert1.display("Error hapen!", Manager.getError());
						show = null;
						return;
					} else
						show = ((Annual_REPORT) report).Analize();
					break;
				default:
					alert1.display("Incorrecct choice");
					return;
				}

				Notification.display(show);

			}
		});

		Button addFarmerData = new Button("Add Data");
		addFarmerData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Object[] pass = null;
				try {
					pass = ChoiceWindow
							.displayAddFarmer(Manager.Farmers.keySet());
				} catch (Exception e) {
					alert1.display("You didn't choose any data!");
					return;
				}
				if (!Manager.addData((Integer) pass[1], (Integer) pass[2],
						(String) pass[0])) {
					alert1.display("Error happen", Manager.getError());
				} else {
					alert1.display("Data import successfully");
				}
				ObservableList<Object[]> data = FXCollections
						.observableArrayList(exceptNullRow(Manager.ds.rows));
				tableview.setItems(data);
			}
		});

		Button removeFarmerData = new Button("Remove Data");
		removeFarmerData.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				final String[] tmp = new String[] { "Remove Farmer",
						"Reduce weight on a day", "Remove weight on a day" };
				ObservableList<Object[]> data;
				Object[] pass;
				switch (selectFun(tmp)) {
				case "Remove Farmer":
					Integer id = null;
					try {
						id = ChoiceWindow.displayRemoveFarmer(Manager.Farmers
								.keySet().toArray(new Integer[0]), Manager);
					} catch (Exception e) {
						alert1.display("You didn't choose any data!");
						return;
					}
					if (!Manager.removeData(id)) {
						alert1.display("Error happen", Manager.getError());
					} else {
						alert1.display("Data reduced successfully");
					}
					data = FXCollections.observableArrayList(
							exceptNullRow(Manager.ds.rows));
					tableview.setItems(data);
					break;
				case "Reduce weight on a day":
					pass = null;
					try {
						pass = ChoiceWindow.displayReduceFarmer(Manager.Farmers
								.keySet().toArray(new Integer[0]), Manager);
					} catch (Exception e) {
						alert1.display("You didn't choose any data!");
						return;
					}
					if (!Manager.removeData((Integer) pass[1],
							(Integer) pass[2], (String) pass[0])) {
						alert1.display("Error happen", Manager.getError());
					} else {
						alert1.display("Data reduced successfully");
					}
					data = FXCollections.observableArrayList(
							exceptNullRow(Manager.ds.rows));
					tableview.setItems(data);
					break;
				case "Remove weight on a day":
					pass = null;
					try {
						pass = ChoiceWindow
								.displayRemoveFarmerDay(Manager.Farmers.keySet()
										.toArray(new Integer[0]), Manager);
					} catch (Exception e) {
						alert1.display("You didn't choose any data!");
						return;
					}
					if (!Manager.removeData((Integer) pass[1],
							(String) pass[0])) {
						alert1.display("Error happen", Manager.getError());
					} else {
						alert1.display("Data reduced successfully");
					}
					data = FXCollections.observableArrayList(
							exceptNullRow(Manager.ds.rows));
					tableview.setItems(data);

					break;
				default:
					return;
				}

			}
		});

		GridPane grid = new GridPane();
		grid.setHgap(20);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		grid.setAlignment(Pos.CENTER);

		grid.add(importButton, 0, 0);
		grid.add(exportButton, 1, 0);
		grid.add(generate_report, 0, 1);
		grid.add(addFarmerData, 0, 2);
		grid.add(removeFarmerData, 1, 2);
		
		pane.setRight(grid);
		
		Scene scene = new Scene(pane, 600, 400);
		s.setScene(scene);

		s.show();
		alert1.display("Do you know? ", interfaceHelp());
	}

	private static String selectFun(String[] tmp) {
		ChoiceDialog<String> dialog = new ChoiceDialog<String>(tmp[0], tmp);
		dialog.setTitle("Select Function");
		dialog.setHeaderText("Please choose the function you want");
		String result = dialog.showAndWait().get();
		// System.out.print(result);
		return result;

	}

	public static void main(String[] args) {
		argument = args;
		launch();
	}

	private static List<Object[]> exceptNullRow(List<Object[]> a) {
		List<Object[]> a1 = new ArrayList<Object[]>();
		for (Object[] h : a) {
			if (h != null) {
				a1.add(h);
			}
		}
		return a1;
	}
	
	//use when user start using GUI
	private static String interfaceHelp() {
		return "Left panel is the dataview in system currently Left panel is the dataview in system currently Left panel is the dataview in system currently \nddd";
	}
	
	//use when user start using command line
	private static String commandHelp() {
		return "";
	}
}
