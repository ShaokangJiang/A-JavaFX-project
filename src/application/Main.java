package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
	protected static Stage ss;

	/**
	 * This method should actually handle argument
	 */
	private void initialize() {
		// TODO Auto-generated method stub
		
		Manager = new FarmerManager();
		String[] args = argument;
		if (args.length == 0) {
			return;
		}
		DataFrameIndex tmp = null;
		if (!initialAnalize(args)) {
			System.out.println("Wrong usage case detected, It seems you want to use the command mode, here it is.");
			System.out.println(
					"\nWelcome to command mode of this program. Because of the requirement of this assignment,"
							+ "\n majority effort should be done in GUI. This part is only for command line import function. \n"
							+ commandHelp());
			
			commandPrompt(tmp);
			return;
		} else {
			System.out.println("Data will be imported and GUI will start...");

			switch (args[0]) {
			case "-d":
				try {
					List<String> a = readFile(args[1]);
					StringBuilder fileList = new StringBuilder();
					for(String tmp1 : a) {
						fileList.append(tmp1+";");
					}
					tmp = ImportExportWindow.Import(fileList.toString());
				} catch (Exception as) {
					System.out.println("Error happen: " + as.getMessage()
							+ "\nYou will enter command line mode as it seems you want to use command line\n");
					tmp = null; 
					commandPrompt(tmp);
					return;
				}
				if (tmp != null) {
					try {
						if (!Manager.importData(tmp)) {
							alert1.display("Please import valid data:",
									Manager.getError());
						}else {
							System.out.println("Successfully imported");
						}
					} catch (Exception e) {
						System.out.println(
								"Unknow event happen, program will close: "
										+ e.getMessage());
						System.exit(1);
					}
				}
				break;
			default:
				try {
					tmp = ImportExportWindow.Import(args[0]);
				} catch (Exception as) {
					System.out.println("Error happen: " + as.getMessage()
							+ "\nPlease redo with valid path");
					break;
				}
				if (tmp != null) {
					try {
						if (!Manager.importData(tmp)) {
							alert1.display("Please import valid data:",
									Manager.getError());
						}else {
							System.out.println("Successfully imported");
						}
					} catch (Exception e) {
						System.out.println(
								"Unknow event happen, program will close: "
										+ e.getMessage());
						System.exit(1);
					}
				}
				break;
			}
		}
	}
	
	private void commandPrompt(DataFrameIndex tmp) {
		Scanner scnr = new Scanner(System.in);
		String[] command;
		
		while (true) {
			System.out.print("> ");
			command = scnr.nextLine().split(" ");
			if (command.length == 0)
				continue;
			switch (command[0]) {
			case "-h":
				System.out.print(commandHelp());
				break;
			case "-d":
				try {
					List<String> a = readFile(command[1]);
					StringBuilder fileList = new StringBuilder();
					for(String tmp1 : a) {
						fileList.append(tmp1+";");
					}
					tmp = ImportExportWindow.Import(fileList.toString());
				} catch (Exception as) {
					System.out.println("Error happen: " + as.getMessage()
							+ "\n Please redo");
					break;
				}
				if (tmp != null) {
					try {
						if (!Manager.importData(tmp)) {
							alert1.display("Please import valid data:",
									Manager.getError());
						}else {
							System.out.println("Successfully imported");
						}
					} catch (Exception e) {
						System.out.println(
								"Unknow event happen, program will close: "
										+ e.getMessage());
						System.exit(1);
					}
				}
				break;
			case "-f":
				try {
					tmp = ImportExportWindow.Import(command[1]);
				} catch (Exception as) {
					System.out.println("Error happen: " + as.getMessage()
							+ "\nPlease redo with valid path");
					break;
				}
				if (tmp != null) {
					try {
						if (!Manager.importData(tmp)) {
							alert1.display("Please import valid data:",
									Manager.getError());
						}else {
							System.out.println("Successfully imported");
						}
					} catch (Exception e) {
						System.out.println(
								"Unknow event happen, program will close: "
										+ e.getMessage());
						System.exit(1);
					}
				}
				break;
			case "-g":
				System.out.println("GUI will start...");
				scnr.close();
				return;
			default:
				System.out.print(commandHelp());
				break;
			}
		}
	}

	private static ArrayList<String> readFile(String path) {
		File dir = new File(path);
		ArrayList<String> files = new ArrayList<String>();
		File[] filesList = dir.listFiles();
		for (File file : filesList) {
			if (file.isFile()) {
				if (file.getName().trim().endsWith(".csv"))
					files.add(file.getAbsolutePath());
			}
		}
		return files;
	}

	private boolean initialAnalize(String[] args) {
		return (args.length == 2 && args[0].equals("-d"))
				|| (args.length == 1 && args[0].contains(".csv"));
	}

	@Override
	public void start(Stage s) throws Exception {
		// TODO Auto-generated method stub
		initialize();
		ss = s;
		BorderPane pane = new BorderPane();

		ObservableList<Object[]> data = FXCollections
				.observableArrayList(exceptNullRow(Manager.ds.rows));

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
							alert1.display("Please import valid data:",
									Manager.getError());
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
				try {
					final String[] tmp = new String[] { "Farm report",
							"Range Report", "Monthly Report", "Annual Report" };
					BorderPane show;
					Date[] pass;
					Report report;
					Date day = null;
					Integer[] farm;
					switch (selectFun(tmp)) {
					case "Farm report":
						// ask for id and year
						try {
							farm = ChoiceWindow.displayAskIdYear(Manager.Farmers
									.keySet().toArray(new Integer[0]), Manager);
						} catch (Exception e) {
							alert1.display("You didn't choose any option!");
							return;
						}
						report = Manager.generateFarmReport(farm[0],farm[1]);
						farm = null;

						if (report == null) {
							alert1.display("Error happen!", Manager.getError());
							show = null;
							return;
						} else
							show = ((FARM_REPORT) report).Analize();

						break;
					case "Range Report":
						pass = null;
						try {
							pass = ChoiceWindow.displayDateRange();
						} catch (Exception e) {
							alert1.display("You didn't choose any range!");
							return;
						}
						report = Manager.generateDateRangeReport(pass[0],
								pass[1]);
						if (report == null) {
							alert1.display("Error hapen!", Manager.getError());
							show = null;
							return;
						} else
							show = ((DATE_RANGE_REPORT) report).Analize();
						break;
					case "Monthly Report":
						// ask for month
						try {
							farm = ChoiceWindow.displayAskYearMonth(Manager);
						} catch (Exception e) {
							alert1.display("You didn't choose any range!");
							return;
						}
						report = Manager.generateMonthReport(farm[0], farm[1]);
						farm = null;
						if (report == null) {
							alert1.display("Error hapen!", Manager.getError());
							show = null;
							return;
						} else
							show = ((MONTHLY_REPORT) report).Analize();
						break;
					case "Annual Report":
						// ask for year
						Integer year = 0;
						try {
							year = ChoiceWindow.displayAskYear(Manager);
						} catch (Exception e) {
							alert1.display("You didn't choose any data!");
							return;
						}
						if (year == null)
							return;
						// System.out.println(year);
						report = Manager.generateAnnualReport(year);
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
				} catch (Exception e) {
					alert1.display("You didn't choose any data!");
					// e.printStackTrace();
					return;
				}
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
				try {
					switch (selectFun(tmp)) {
					case "Remove Farmer":
						Integer id = null;
						try {
							id = ChoiceWindow.displayRemoveFarmer(
									Manager.Farmers.keySet()
											.toArray(new Integer[0]),
									Manager);
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
							pass = ChoiceWindow.displayReduceFarmer(
									Manager.Farmers.keySet()
											.toArray(new Integer[0]),
									Manager);
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
							pass = ChoiceWindow.displayRemoveFarmerDay(
									Manager.Farmers.keySet()
											.toArray(new Integer[0]),
									Manager);
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
				} catch (Exception e) {
					alert1.display("You didn't choose any data!");
					return;
				}

			}
		});

		Button Exit = new Button("Exit");
		Exit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
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
		grid.add(Exit, 1, 3);

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

	// use when user start using GUI
	private static String interfaceHelp() {
		return "Left panel is the dataview currently in system. "
				+ "\nBotton Usage:"
				+ "\n  Import data -- Import data from csv, you could select multiple files\n"
				+ "	Export data -- Export current data into csv file\n"
				+ "	Generate report -- Generate and show report based on your request and current data in system\n"
				+ "	Add Data -- Add data for a Farmer, use a id that doesn't exist in the system will create a new Farmer\n"
				+ "	Remove Data -- Remove data in different situation";
	}

	// use when user start using command line
	private static String commandHelp() {
		return "Usage: "
				+ "\t -f \"path/to/file\" -- This file will be imported. Should be csv files. Seperate using ; if there are multiple files\n"
				+ "\t -d \"path/to/file\" -- This will import any csv file in the directory, if any of them contain incorrect formatted data, no data will be imported \n"
				+ "\t -h -- print help message \n" + "\t -g -- start GUI \n";
	}
}
