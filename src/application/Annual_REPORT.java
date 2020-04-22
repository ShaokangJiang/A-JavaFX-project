package application;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * Assignment requirement: Ask for year.
 * 
 * Then display list of total weight and percent of total weight of all farms by
 * farm for the year.
 * 
 * Sort by Farm ID, or you can allow the user to select display ascending or
 * descending by weight.
 * 
 * @author shaokang
 *
 */
public class Annual_REPORT extends Report
		implements Calculate, Export{

	protected int year;
	protected int farmersTotalWeight;
	
	public Annual_REPORT(HashMap<Integer, Farmer> farmers, int year, int farmersTotalWeight) {
		super(farmers);
		// TODO Auto-generated constructor stub
		this.year = year; 
		this.farmersTotalWeight = farmersTotalWeight;
	}

	@Override
	public DataFrame export(TableView<Object[]> a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BorderPane Analize() {
		// TODO Auto-generated method stub
		return new BorderPane();
	}
}
