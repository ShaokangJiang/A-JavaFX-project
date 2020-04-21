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
		Farmers = new Farmer[farmers2.size()];
		for (int i = 0; i < Farmers.length; i++) {
			Farmers[i] = farmers2.get(i);
		}
	}

}
