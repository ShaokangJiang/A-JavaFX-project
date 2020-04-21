package application;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

/**
 * Requirement: Ask for year and month.
 * 
 * Then, display a list of totals and percent of total by farm.
 * 
 * The list must be sorted by Farm ID, or you can prompt for ascending or
 * descending by weight.
 * 
 * @author shaokang
 *
 */
public class MONTHLY_REPORT extends Report
		implements Calculate, Export<MONTHLY_REPORT.MONTH> {

	protected Date askFor;
	protected int farmersTotalWeight;
	public MONTHLY_REPORT(HashMap<Integer, Farmer> farmers, Date askFor, int farmersTotalWeight) {
		super(farmers);
		// TODO Auto-generated constructor stub
		this.askFor = askFor;
		this.farmersTotalWeight = farmersTotalWeight;
	}

	@Override
	public DataFrame export(TableView<MONTH> a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BorderPane Analize() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * This class is used to store information that will be represented in table
	 * view in the
	 * 
	 * @author Shaokang Jiang
	 *
	 */
	protected class MONTH {
		protected int id;
		protected Date day;
		protected int Num;
	}

}
