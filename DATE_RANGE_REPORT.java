package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
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
	}

	@Override
	public DataFrame export(TableView<Object[]> a) {
		// TODO Auto-generated method stub
		return null;
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

		tableview.setItems(data);

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
				alert1.display("Still in construction");
			}
		});

		Button Export = new Button("Export");
		Export.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				alert1.display("Still in construction");
			}
		});

		grid.add(Filter, 0, 0);
		grid.add(Export, 0, 1);

		pane.setRight(grid);

		alert1.display(
				"This effort will display the range report for each farmer between "
						+ start.toString() + " and " + end.toString()
						+ "\nRepresentation of each field:"
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
			if(subtotal!=0) {
				total+=subtotal;
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
