package application;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Assignment requirement:
 * 
 * Prompt user for a farm id and year : <id, year> = <Integer, Integer>
 * 
 * Choice 2 (or use all available data) <id, year> = <null, null>
 * 
 * choice 3 (or use all available data for a farmer) <id, year> = <Integer,
 * null>
 * 
 * Then, display the total milk weight and percent of the total of all farm for
 * each month.
 * 
 * Sort, the list by month number 1-12, show total weight, then that farm's
 * percent of the total milk received for each month.
 * 
 * month | total_weight | percent int | int | double String | int | double
 * 
 * 
 * 
 * @author shaokang
 *
 */
public class FARM_REPORT extends Report implements Calculate, Export {

	protected Integer id;
	protected Integer year;
	protected int farmersTotalWeight;
	private static DecimalFormat df = new DecimalFormat("#.00");
	protected TableView<Object[]> tableviewOrig;
	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

	private String[] agg; // aggregation result 0-minWeight 1-MaxWeight
	// 2-AvgWeight 3-minWeightPer 4-MaxWeightPer
	// 5-AvgWeightPer
	private int totalWeight;

	public FARM_REPORT(HashMap<Integer, Farmer> farmers, Integer id,
			Integer year, Integer farmersTotalWeight) {
		super(farmers);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.year = year;
		this.farmersTotalWeight = farmersTotalWeight;
		this.agg = new String[6];
	}

	/**
	 * Because there are too many cases in this program, direct export will be
	 * more efficient
	 * 
	 * @param a
	 * @return
	 */
	public DataFrame export(TableView<Object[]> a) {
		// TODO Auto-generated method stub
		DataFrame toRe = null;
		if (id != null && year == null) {// use id and all year of this
			toRe = new DataFrame(
					new String[] { "Year-Month", "total_weight", "percent" },
					new Object[] { "2019-01", 1, 0.5 });
			toRe.rows = a.getItems().stream().collect(Collectors.toList());
		} else {// use id and year as specified
// month | total_weight | percent
			toRe = new DataFrame(
					new String[] { "Month", "total_weight", "percent" },
					new Object[] { 1, 1, 0.5 });
			toRe.rows = a.getItems().stream().collect(Collectors.toList());
		}
		return toRe;
	}

	@Override
	public BorderPane Analize() {
		// TODO Auto-generated method stub
		BorderPane pane = null;
		if (id != null && year == null) {// use id and all year of this
			pane = new BorderPane(); // id
			ObservableList<Object[]> data = FXCollections
					.observableArrayList(convertID());

			TableColumn<Object[], String> month = new TableColumn<Object[], String>(
					"Month");
			month.setCellValueFactory(
					new Callback<CellDataFeatures<Object[], String>, ObservableValue<String>>() {
						public ObservableValue<String> call(
								CellDataFeatures<Object[], String> p) {
							// p.getValue() returns the Person instance for a
							// particular TableView row
							return new ReadOnlyStringWrapper(
									(String) p.getValue()[0]);
						}
					});

			TableColumn<Object[], Integer> total = new TableColumn<Object[], Integer>(
					"Tot_Wei");
			total.setCellValueFactory(
					new Callback<CellDataFeatures<Object[], Integer>, ObservableValue<Integer>>() {
						public ObservableValue<Integer> call(
								CellDataFeatures<Object[], Integer> p) {
							// p.getValue() returns the Person instance for a
							// particular TableView row
							return new ReadOnlyObjectWrapper<>(
									(Integer) p.getValue()[1]);
						}
					});

			TableColumn<Object[], String> percent = new TableColumn<Object[], String>(
					"percent(%)");
			percent.setCellValueFactory(
					new Callback<CellDataFeatures<Object[], String>, ObservableValue<String>>() {
						public ObservableValue<String> call(
								CellDataFeatures<Object[], String> p) {
							// p.getValue() returns the Person instance for a
							// particular TableView row
							return new ReadOnlyObjectWrapper<>(
									df.format(((Double) p.getValue()[2])));
						}
					});

			TableView<Object[]> tableview = new TableView<Object[]>();

			tableview.setItems(data);
			tableviewOrig = tableview;
			month.setSortable(true);
			month.setSortType(SortType.DESCENDING);
			tableview.getColumns().addAll(month, total, percent);
			tableview.getSortOrder().add(month);

			int minWeight = (int) tableview.getItems().get(0)[1];
			int maxWeight = (int) tableview.getItems().get(0)[1];
			double minPer = (double) tableview.getItems().get(0)[2];
			double maxPer = (double) tableview.getItems().get(0)[2];
			totalWeight = 0;

			for (Object[] tmp : tableview.getItems()) {
				if ((int) tmp[1] < minWeight)
					minWeight = (int) tmp[1];
				else if ((int) tmp[1] > maxWeight)
					maxWeight = (int) tmp[1];
				totalWeight += (int) tmp[1];
				double tmpPer = (double) Double
						.parseDouble(String.valueOf(tmp[2]));
				if (tmpPer < minPer)
					minPer = tmpPer;
				else if (tmpPer > maxPer)
					maxPer = tmpPer;
			}

			agg[0] = "" + minWeight;
			agg[1] = "" + maxWeight;
			agg[2] = df.format((double) totalWeight
					/ (double) tableview.getItems().size());
			agg[3] = df.format(minPer);
			agg[4] = df.format(maxPer);
			agg[5] = df.format(100 / (double) tableview.getItems().size());

			month.setSortType(SortType.DESCENDING);

			pane.setLeft(tableview);

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			Button Filter = new Button("Filter");
			Filter.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Object[] tmp = ChoiceWindow.displayRangeID(tableviewOrig);
					List<Object[]> toShow = tableviewOrig.getItems().stream()
							.filter(o -> {
								try {
									return ChoiceWindow.in(
											dateFormat.parse((String) o[0]),
											(Date) tmp[0], (Date) tmp[1])
											&& ChoiceWindow.in((Integer) o[1],
													(Integer) tmp[2],
													(Integer) tmp[3])
											&& ChoiceWindow.in((double) o[2],
													(double) tmp[4],
													(double) tmp[5]);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									return false;
								}
							}).collect(Collectors.toList());
					ObservableList<Object[]> data = FXCollections
							.observableArrayList(toShow);
					tableview.setItems(data);
					alert1.display("Filter result",
							"This will show up id " + tmp[0] + "~" + tmp[1]
									+ " weight " + tmp[2] + "~" + tmp[3]
									+ " Weight percaentage" + tmp[4] + "%~"
									+ tmp[5] + "%");
				}
			});

			Button Export = new Button("Export");
			Export.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					DataFrame toEx = export(tableview);
					if (ImportExportWindow.DisplayExportGeneral(Main.ss,
							toEx)) {
						alert1.display("Successfully export",
								"Successfully exported to directory: "
										+ ImportExportWindow.path1);
					}
				}
			});

			Button ExportAgg = new Button("Export Aggregation report");
			ExportAgg.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						String report = "Report for id " + id + " and all Month"
								+ ":" + System.lineSeparator() + "Total Farms: "
								+ tableview.getItems().size()
								+ System.lineSeparator() + "Total Weight: "
								+ totalWeight + System.lineSeparator()
								+ "Min Weight: " + agg[0]
								+ System.lineSeparator() + "Max Weight: "
								+ agg[1] + System.lineSeparator()
								+ "Average Weight: " + agg[2]
								+ System.lineSeparator()
								+ "Min Weight Percentage: " + agg[3] + "%"
								+ System.lineSeparator()
								+ "Max Weight percentage: " + agg[4] + "%"
								+ System.lineSeparator()
								+ "Average Weight Percentage: " + agg[5] + "%";
						if (ImportExportWindow.DisplayExport(Main.ss,
								"Aggregation report:\n" + report)) {
							alert1.display("Successfully export",
									"Successfully exported to directory: "
											+ ImportExportWindow.path1);
						}
					} catch (Exception e) {
					}
				}
			});

			grid.add(Filter, 0, 0);
			grid.add(Export, 0, 1);
			grid.add(ExportAgg, 0, 2);

			VBox vb = new VBox(5);
			vb.setStyle("-fx-padding: 16;");
			vb.getChildren().addAll(
					new Label("Report for id " + id + " and all Month" + ":"),
					new Label("Total Months: " + tableview.getItems().size()),
					new Label("Total Weight: " + totalWeight),
					new Label("Min Weight: " + agg[0]),
					new Label("Max Weight: " + agg[1]),
					new Label("Average Weight: " + agg[2]),
					new Label("Min Weight Percentage: " + agg[3] + "%"),
					new Label("Max Weight percentage: " + agg[4] + "%"),
					new Label("Average Weight Percentage: " + agg[5] + "%"));
			BorderPane right = new BorderPane();

			right.setCenter(vb);
			right.setBottom(grid);
			pane.setRight(right);
			alert1.display("This effort will display the Farm report for id "
					+ id + "in all year" + "\nRepresentation of each field:"
					+ "\n  Month -- Month in decending order"
					+ "\n  tot_weight -- the tital weight for "
					+ "\n  percent -- weight of this farm/weight of all farmers in this year");

		} else {// use id and year as specified
// month | total_weight | percent
			pane = new BorderPane();
			ObservableList<Object[]> data = FXCollections
					.observableArrayList(convertIDYear());

			TableColumn<Object[], Integer> month = new TableColumn<Object[], Integer>(
					"Month");
			month.setCellValueFactory(
					new Callback<CellDataFeatures<Object[], Integer>, ObservableValue<Integer>>() {
						public ObservableValue<Integer> call(
								CellDataFeatures<Object[], Integer> p) {
							// p.getValue() returns the Person instance for a
							// particular TableView row
							return new ReadOnlyObjectWrapper<>(
									(Integer) p.getValue()[0]);
						}
					});

			TableColumn<Object[], Integer> total = new TableColumn<Object[], Integer>(
					"Tot_Wei");
			total.setCellValueFactory(
					new Callback<CellDataFeatures<Object[], Integer>, ObservableValue<Integer>>() {
						public ObservableValue<Integer> call(
								CellDataFeatures<Object[], Integer> p) {
							// p.getValue() returns the Person instance for a
							// particular TableView row
							return new ReadOnlyObjectWrapper<>(
									(Integer) p.getValue()[1]);
						}
					});

			TableColumn<Object[], String> percent = new TableColumn<Object[], String>(
					"percent(%)");
			percent.setCellValueFactory(
					new Callback<CellDataFeatures<Object[], String>, ObservableValue<String>>() {
						public ObservableValue<String> call(
								CellDataFeatures<Object[], String> p) {
							// p.getValue() returns the Person instance for a
							// particular TableView row
							return new ReadOnlyObjectWrapper<>(
									df.format(((Double) p.getValue()[2])));
						}
					});

			TableView<Object[]> tableview = new TableView<Object[]>();

			tableview.setItems(data);
			tableviewOrig = tableview;
			month.setSortable(true);
			month.setSortType(SortType.DESCENDING);
			tableview.getColumns().addAll(month, total, percent);
			tableview.getSortOrder().add(month);

			int minWeight = (int) tableview.getItems().get(0)[1];
			int maxWeight = (int) tableview.getItems().get(0)[1];
			double minPer = (double) tableview.getItems().get(0)[2];
			double maxPer = (double) tableview.getItems().get(0)[2];
			totalWeight = 0;

			for (Object[] tmp : tableview.getItems()) {
				if ((int) tmp[1] < minWeight)
					minWeight = (int) tmp[1];
				else if ((int) tmp[1] > maxWeight)
					maxWeight = (int) tmp[1];
				totalWeight += (int) tmp[1];
				double tmpPer = (double) Double
						.parseDouble(String.valueOf(tmp[2]));
				if (tmpPer < minPer)
					minPer = tmpPer;
				else if (tmpPer > maxPer)
					maxPer = tmpPer;
			}

			agg[0] = "" + minWeight;
			agg[1] = "" + maxWeight;
			agg[2] = df.format((double) totalWeight
					/ (double) tableview.getItems().size());
			agg[3] = df.format(minPer);
			agg[4] = df.format(maxPer);
			agg[5] = df.format(100 / (double) tableview.getItems().size());

			month.setSortType(SortType.DESCENDING);

			pane.setLeft(tableview);

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(20, 150, 10, 10));

			Button Filter = new Button("Filter");
			Filter.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Object[] tmp = ChoiceWindow
							.displayRangeIDYear(tableviewOrig);
					List<Object[]> toShow = tableviewOrig.getItems().stream()
							.filter(o -> ChoiceWindow.in((Integer) o[0],
									(Integer) tmp[0], (Integer) tmp[1])
									&& ChoiceWindow.in((Integer) o[1],
											(Integer) tmp[2], (Integer) tmp[3])
									&& ChoiceWindow.in((double) o[2],
											(double) tmp[4], (double) tmp[5]))
							.collect(Collectors.toList());
					ObservableList<Object[]> data = FXCollections
							.observableArrayList(toShow);
					tableview.setItems(data);
					alert1.display("Filter result",
							"This will show up id " + tmp[0] + "~" + tmp[1]
									+ " weight " + tmp[2] + "~" + tmp[3]
									+ " Weight percaentage" + tmp[4] + "%~"
									+ tmp[5] + "%");
				}
			});

			Button Export = new Button("Export");
			Export.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					DataFrame toEx = export(tableview);
					if (ImportExportWindow.DisplayExportGeneral(Main.ss,
							toEx)) {
						alert1.display("Successfully export",
								"Successfully exported to directory: "
										+ ImportExportWindow.path1);
					}
				}
			});
			Button ExportAgg = new Button("Export Aggregation report");
			ExportAgg.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					try {
						String report = "Report for id " + id + " and year "
								+ year + ":" + System.lineSeparator()
								+ "Total Farms: " + tableview.getItems().size()
								+ System.lineSeparator() + "Total Weight: "
								+ totalWeight + System.lineSeparator()
								+ "Min Weight: " + agg[0]
								+ System.lineSeparator() + "Max Weight: "
								+ agg[1] + System.lineSeparator()
								+ "Average Weight: " + agg[2]
								+ System.lineSeparator()
								+ "Min Weight Percentage: " + agg[3] + "%"
								+ System.lineSeparator()
								+ "Max Weight percentage: " + agg[4] + "%"
								+ System.lineSeparator()
								+ "Average Weight Percentage: " + agg[5] + "%";
						if (ImportExportWindow.DisplayExport(Main.ss,
								"Aggregation report:\n" + report)) {
							alert1.display("Successfully export",
									"Successfully exported to directory: "
											+ ImportExportWindow.path1);
						}
					} catch (Exception e) {
					}
				}
			});

			grid.add(Filter, 0, 0);
			grid.add(Export, 0, 1);
			grid.add(ExportAgg, 0, 2);

			VBox vb = new VBox(5);
			vb.setStyle("-fx-padding: 16;");
			vb.getChildren().addAll(
					new Label(
							"Report for id " + id + " and year " + year + ":"),
					new Label("Total Months: " + tableview.getItems().size()),
					new Label("Total Weight: " + totalWeight),
					new Label("Min Weight: " + agg[0]),
					new Label("Max Weight: " + agg[1]),
					new Label("Average Weight: " + agg[2]),
					new Label("Min Weight Percentage: " + agg[3] + "%"),
					new Label("Max Weight percentage: " + agg[4] + "%"),
					new Label("Average Weight Percentage: " + agg[5] + "%"));
			BorderPane right = new BorderPane();

			right.setCenter(vb);
			right.setBottom(grid);
			pane.setRight(right);
			alert1.display("This effort will display the Farm report for id "
					+ id + "in year" + year + "\nRepresentation of each field:"
					+ "\n  Month -- Month in decending order"
					+ "\n  tot_weight -- the tital weight for "
					+ "\n  percent -- weight of this farm/weight of all farmers in this year");

		}

		return pane;
	}

	/**
	 * id | total weight | percent of total weight Integer | Integer | Double
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private List<Object[]> convertID() {
		// TODO Auto-generated method stub
		Farmer toUse = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		for (Farmer a : Farmers) {// accumulate
			if (a.getId() == id) {
				toUse = a;
				break;
			}
		}
		if (toUse == null)
			return null;
		List<Object[]> toRe = new ArrayList<Object[]>();
		int totalWeight = toUse.getTotalWeight();
		for (Map.Entry<Date, Integer> a : toUse.getWeightByMonth().entrySet()) {
			toRe.add(new Object[] { dateFormat.format(a.getKey()), a.getValue(),
					(double) a.getValue() * 100 / (double) totalWeight });
		}

		return toRe;
	}

	/**
	 * month | total_weight | percent
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private List<Object[]> convertIDYear() {
		// TODO Auto-generated method stub
		Farmer toUse = null;
		for (Farmer a : Farmers) {// accumulate
			if (a.getId() == id) {
				toUse = a;
				break;
			}
		}
		if (toUse == null)
			return null;
		List<Object[]> toRe = new ArrayList<Object[]>();
		Date tmp = new Date(18000000);// set it to be 1970-01-01
		tmp.setYear(year - 1900);
		int totalWeight = toUse.getWeightByYear().get(tmp);
		for (Map.Entry<Date, Integer> a : toUse.getWeightByMonth().entrySet()) {
			if (a.getKey().getYear() == year - 1900)
				toRe.add(new Object[] { (a.getKey().getMonth() + 1),
						a.getValue(),
						(double) a.getValue() * 100 / (double) totalWeight });
		}
		return toRe;
	}
}