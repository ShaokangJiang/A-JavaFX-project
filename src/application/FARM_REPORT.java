package application;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.SortType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

	public FARM_REPORT(HashMap<Integer, Farmer> farmers, Integer id,
			Integer year, Integer farmersTotalWeight) {
		super(farmers);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.year = year;
		this.farmersTotalWeight = farmersTotalWeight;
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
		if (id == null && year == null) {// use all farmer all year all month
			toRe = new DataFrame(new String[] {"ID","Year-Month","total_weight","percent"}, new Object[] {"2019-01",1,1,0.5});
			toRe.rows = a.getItems().stream().collect(Collectors.toList());
			
		} else if (id != null && year == null) {// use id and all year of this
			toRe = new DataFrame(new String[] {"Year-Month","total_weight","percent"}, new Object[] {"2019-01",1,0.5});
			toRe.rows = a.getItems().stream().collect(Collectors.toList());
		} else {// use id and year as specified
// month | total_weight | percent
		toRe = new DataFrame(new String[] {"Month","total_weight","percent"}, new Object[] {1,1,0.5});
		toRe.rows = a.getItems().stream().collect(Collectors.toList());
		}
		return toRe;
	}

	@Override
	public BorderPane Analize() {
		// TODO Auto-generated method stub
		BorderPane pane = new BorderPane();
		if (id == null && year == null) {// use all farmer all year all month

		} else if (id != null && year == null) {// use id and all year of this
												// id

		} else {// use id and year as specified
// month | total_weight | percent
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
					Object[] tmp = ChoiceWindow.displayRangeIDYear(tableviewOrig);
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
					if (ImportExportWindow.DisplayExportGeneral(Main.ss, toEx)) {
						alert1.display("Successfully export",
								"Successfully exported to directory: "
										+ ImportExportWindow.path1);
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
						(double) a.getValue()*100 / (double) totalWeight });
		}
		return toRe;
	}

	/**
	 * id | month | total_weight | percent
	 * 
	 * @return
	 */
	private List<Object[]> convertAll() {
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