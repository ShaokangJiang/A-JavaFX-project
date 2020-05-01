package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Set;

import javafx.beans.property.ReadOnlyObjectWrapper;
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
 * 
 * Requirement
 * 
 * Prompt user for start date (year-month-day) and end month-day,
 * 
 * Then display the total milk weight per farm and the percentage of the total
 * for each farm over that date range.
 * 
 * The list must be sorted by Farm ID, or you can prompt for ascending or
 * descending order by weight or percentage.
 * 
 * 
 * ID | total milk weight per farm in the day range | percentage of the total
 * Integer | Integer | Double
 * 
 * @author shaokang
 *
 */
public class DATE_RANGE_REPORT extends Report implements Calculate, Export {

	protected Date start;
	protected Date end;
	protected int farmersTotalWeight;
	private static DecimalFormat df = new DecimalFormat("#.00");
	private TableView<Object[]> tableviewOrig;
	private String[] agg; // aggregation result 0-minWeight 1-MaxWeight
	// 2-AvgWeight 3-minWeightPer 4-MaxWeightPer
	// 5-AvgWeightPer
	private int totalWeight;

	/**
	 * Instead of passing in the daate range, let user click a button to choose
	 * at first
	 * 
	 * @param st
	 */
	public DATE_RANGE_REPORT(HashMap<Integer, Farmer> farmers, Date start,
			Date end, Integer farmersTotalWeight) {
		super(farmers);
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end = end;
		this.farmersTotalWeight = farmersTotalWeight;
		this.agg = new String[6];
	}

	@Override
	public DataFrame export(TableView<Object[]> a) {
		DataFrame toRe = new DataFrame(
				new String[] { "Farm_id", "Weight", "Total_weight" },
				new Object[] { 1, 1, 1.1 });
		toRe.rows = a.getItems().stream().collect(Collectors.toList());
		return toRe;
	}

	@Override
	/**
	 * A little bit different at here. You need to generate the table and the
	 * graph based on data range. And when you are registering a filter, you
	 * need to make sure the date range are not able to exceed the start and end
	 * date range.
	 */
	public BorderPane Analize() {
		// TODO Auto-generated method stub
		BorderPane pane = new BorderPane();

		ObservableList<Object[]> data = FXCollections
				.observableArrayList(convert());

		TableColumn<Object[], Integer> id = new TableColumn<Object[], Integer>(
				"ID");
		id.setCellValueFactory(
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
		tableviewOrig = tableview;
		tableview.setItems(data);

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
			double tmpPer = (double) Double.parseDouble(String.valueOf(tmp[2]));
			if (tmpPer < minPer)
				minPer = tmpPer;
			else if (tmpPer > maxPer)
				maxPer = tmpPer;
		}

		agg[0] = ""+minWeight;
		agg[1] = ""+maxWeight;
		agg[2] = df.format((double) totalWeight / (double) tableview.getItems().size());
		agg[3] = df.format(minPer);
		agg[4] = df.format(maxPer);
		agg[5] = df.format(100 / (double) tableview.getItems().size());
		
		id.setSortable(true);
		id.setSortType(SortType.DESCENDING);
		tableview.getColumns().addAll(id, total, percent);

		pane.setLeft(tableview);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		Button Filter = new Button("Filter");
		Filter.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Object[] tmp = ChoiceWindow.displayRange(tableviewOrig);
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
				} catch (Exception as) {
					alert1.display("You select nothing...");
				}
			}
		});

		Button Export = new Button("Export");
		Export.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					DataFrame toEx = export(tableview);
					if(ImportExportWindow.DisplayExportReport(Main.ss, toEx)) {
					alert1.display("Successfully export", "Successfully exported to directory: "+ImportExportWindow.path1);
					}
					} catch (Exception e) {
				}
			}
		});
		
		Button ExportAgg = new Button("Export Aggregation report");
		ExportAgg.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					String report = "range report for each farmer between "
							+ start.toString() + " and " + end.toString()
							+ System.lineSeparator() + "Total Farms: "
							+ tableview.getItems().size()
							+ System.lineSeparator() + "Total Weight: "
							+ totalWeight + System.lineSeparator()
							+ "Min Weight: " + agg[0] + System.lineSeparator()
							+ "Max Weight: " + agg[1] + System.lineSeparator()
							+ "Average Weight: " + agg[2]
							+ System.lineSeparator() + "Min Weight Percentage: "
							+ agg[3] + "%" + System.lineSeparator()
							+ "Max Weight percentage: " + agg[4] + "%"
							+ System.lineSeparator()
							+ "Average Weight Percentage: " + agg[5] + "%";
					if (ImportExportWindow.DisplayExport(Main.ss,
							"Aggregation report:\n"+report)) {
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
		vb.getChildren().addAll(new Label("Range report for each farmer between \n"
				+ start.toString() + " and \n" + end.toString()),
				new Label("Total Farms: " + tableview.getItems().size()),
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

		alert1.display(
				"This effort will display the range report for each farmer between "
						+ start.toString() + " and " + end.toString()
						+ "\n\n Representation of each field:"
						+ "\n  id -- Farmer_id in decending order"
						+ "\n  tot_weight -- the total weight for a farmer on a day in range"
						+ "\n  percent -- weight of this farm/weight of all farmers in this day in range");

		return pane;
	}

	private List<Object[]> convert() {
		// TODO Auto-generated method stub
		List<Object[]> toRe = new ArrayList<Object[]>();
		HashMap<Integer, Integer> totweightByID = new HashMap<Integer, Integer>();
		Integer total = 0;
		for (Farmer a : Farmers) {// accumulate
			int subtotal = 0;
			for (Entry<Date, Integer> key : a.getWeightByDay().entrySet()) {

				if (in(key.getKey())) {
					subtotal += key.getValue();
				}
			}
			if (subtotal != 0) {
				total += subtotal;
				totweightByID.put(a.getId(), subtotal);
			}
		}

		for (Entry<Integer, Integer> key : totweightByID.entrySet()) {
			toRe.add(new Object[] { key.getKey(), key.getValue(),
					(double) key.getValue() * 100 / (double) total });
		}

		return toRe;
	}

	private Boolean in(Date current) {
		if (current.compareTo(start) > 0 && current.compareTo(end) < 0)
			return true;
		return false;
	}

}
