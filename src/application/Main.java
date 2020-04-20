package application;

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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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

		ObservableList<Object[]> data = FXCollections.observableArrayList(
				new Object[] { "1990-01-01", 2, 25 },
				new Object[] { "1990-01-02", 2, 25 },
				new Object[] { "1990-01-03", 3, 25 },
				new Object[] { "1990-01-04", 4, 25 },
				new Object[] { "1990-01-05", 5, 25 },
				new Object[] { "1990-01-01", 6, 25 },
				new Object[] { "1990-01-01", 7, 25 });

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

				if(tmp !=null) {
				try {
				if (!Manager.importData(tmp)) {
					alert1.display(Manager.getError());
				}
				ObservableList<Object[]> data = FXCollections
						.observableArrayList(Manager.ds.rows);
				tableview.setItems(data);
				}catch(Exception e) {
					alert1.display("Unknow event happen, program will close", e.getMessage());
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
					if(ImportExportWindow.DisplayExport(s, Manager.ds)) {
						alert1.display("Successfully output", "A result file representing all information is generated to "+ImportExportWindow.path1);
					};
				} catch (Exception as) {
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

		pane.setRight(grid);

		Scene scene = new Scene(pane, 600, 400);
		s.setScene(scene);

		s.show();
	}

	public static void main(String[] args) {
		argument = args;
		launch();
	}
}
