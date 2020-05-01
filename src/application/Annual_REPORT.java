package application;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * Assignment requirement: Ask for year.
 * 
 * Then display list of total weight and percent of total weight of all farms by
 * farm for the year.
 * 
 * Sort by Farm ID, or you can allow the user to select display ascending or
 * descending by weight.
 * 
 * id | total weight | percent of total weight Integer | Integer | Double
 * 
 * @author shaokang
 *
 */
public class Annual_REPORT extends Report implements Calculate, Export {

	protected int year;
	protected int farmersTotalWeight;
	private static DecimalFormat df = new DecimalFormat("#.00");
	private TableColumn<Object[], Integer> id;
	private TableColumn<Object[], Integer> total;
	private TableColumn<Object[], String> percent;
	private TableView<Object[]> tableviewOrig;

	public Annual_REPORT(HashMap<Integer, Farmer> farmers, int year,
			int farmersTotalWeight) {
		super(farmers);
		// TODO Auto-generated constructor stub
		this.year = year;
		this.farmersTotalWeight = farmersTotalWeight;
	}

	@Override
	public DataFrame export(TableView<Object[]> a) {
		// TODO Auto-generated method stub
		DataFrame toRe = new DataFrame(
				new String[] { "Farm_id", "Weight", "Total_weight" },
				new Object[] { 1, 1, 1.1 });
		toRe.rows = a.getItems().stream().collect(Collectors.toList());
		return toRe;
	}

	@Override
	public BorderPane Analize() {
		// TODO Auto-generated method stub

		BorderPane pane = new BorderPane();

		ObservableList<Object[]> data = FXCollections
				.observableArrayList(convert());

		id = new TableColumn<Object[], Integer>("ID");
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

		total = new TableColumn<Object[], Integer>("Tot_Wei");
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

		percent = new TableColumn<Object[], String>("percent(%)");
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
					ImportExportWindow.DisplayExportReport(Main.ss, toEx);
					alert1.display("Successfully export", "Successfully exported to directory: "+ImportExportWindow.path1);
				} catch (Exception e) {
				}
			}
		});

		grid.add(Filter, 0, 0);
		grid.add(Export, 0, 1);

		pane.setRight(grid);

		alert1.display(
				"This effort will display the annual report for each farmer in year"
						+ year + "\nRepresentation of each field:"
						+ "\n  id -- Farmer_id in decending order"
						+ "\n  tot_weight -- the tital weight for "
						+ "\n  percent -- weight of this farm/weight of all farmers in this year");

		return pane;
	}

	/**
	 * id | total weight | percent of total weight Integer | Integer | Double
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private List<Object[]> convert() {
		// TODO Auto-generated method stub
		Date tmp = new Date(18000000);// set it to be 1970-01-01
		tmp.setYear(year - 1900);

		List<Object[]> toRe = new ArrayList<Object[]>();
		int tot = 0;
		for (Farmer a : Farmers) {// accumulate
			if (a.getWeightByYear().get(tmp) != null)
				tot += a.getWeightByYear().get(tmp);
		}

		for (Farmer a : Farmers) {// accumulate
			Integer weight = a.getWeightByYear().get(tmp);
			if (weight != null)
				toRe.add(new Object[] { a.getId(), weight,
						(double) weight * 100 / (double) tot });
		}

		return toRe;
	}

}
