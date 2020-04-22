package application;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * An abstract class for each report
 * @author shaokang
 *
 */
public abstract class Report {

	protected Farmer[] Farmers;

	/**
	 * In this method, you need to convert each element of passed in st to an
	 * element in Farmers.
	 * 
	 * @param farmers2
	 */
	public Report(HashMap<Integer, Farmer> farmers2) {
		Farmers = farmers2.values().toArray(new Farmer[0]);
	}

}
