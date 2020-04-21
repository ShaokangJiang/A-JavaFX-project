package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * Assignment requirement: Prompt user for a farm id and year (or use all
 * available data)
 * 
 * Then, display the total milk weight and percent of the total of all farm for
 * each month.
 * 
 * Sort, the list by month number 1-12, show total weight, then that farm's
 * percent of the total milk received for each month.
 * 
 * @author shaokang
 *
 */
public class FARM_REPORT extends Report
		implements Calculate, Export<FARM_REPORT.FARM> {

	protected Integer id;
	protected Integer year;
	protected int farmersTotalWeight;
	
	public FARM_REPORT(HashMap<Integer, Farmer> farmers, Integer id, Integer year, Integer farmersTotalWeight) {
		super(farmers);
		// TODO Auto-generated constructor stub
		this.id = id;
		this.year = year; 
		this.farmersTotalWeight = farmersTotalWeight;
	}

	@Override
	public DataFrame export(TableView<FARM> a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BorderPane Analize() {
		// TODO Auto-generated method stub
		
		return new BorderPane();
	}

	/**
	 * This class is used to store information that will be represented in table
	 * view in the
	 * 
	 * @author Shaokang Jiang
	 *
	 */
	protected class FARM {
		protected int id;
		protected Date day;
		protected int Num;
	}

}
