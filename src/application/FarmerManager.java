package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javafx.scene.control.TableView;

/**
 * The core management class
 * 
 * @author shaokang
 *
 */
public class FarmerManager implements Export<Object[]> {

	// Main class will use them, so it is needed to be in protected
	protected List<Farmer> Farmers;
	protected DataFrameIndex ds;
	private String information = "";
	protected int totalWeight;
	protected HashSet<Integer> allID;
	private static final String dayPattern = "yyyy-MM-dd";
	private static final SimpleDateFormat dateDayFormat = new SimpleDateFormat(
			dayPattern);
	/**
	 * Constructor of this class, will be called once the entire program
	 * starting. Need initialize the local private fields to represent them as
	 * empty. The ds is null to indicate this is a start
	 */
	public FarmerManager() {
		Farmers = new ArrayList<Farmer>();
		ds = new DataFrameIndex(new String[] { "date", "farm_id", "weight" },
				new Object[] { "2019-01-01", 1, 1 });
		allID = new HashSet<Integer>();
	}

	/**
	 * Add data to the current Farmers and ds based on imformation in the passed
	 * in fm. You need to make use of the methods in Farmers and ds.
	 * 
	 * This would be the easy case, you have a Farmer object now. you need to
	 * merge data in this object with any object that has the same index
	 * information. If there is no object that has the same indexing information
	 * as this one. You should add a new one to the current Farmers and ds.
	 * 
	 * For example, if the pass in fm has fm.id=1 fm.day=2019-01-01 fm.gallon =
	 * 111 if there is a farm has the same id information. then call the
	 * newValue method in farmer with those information.
	 * 
	 * If not, then add this one to the farmers and also append it to the ds.
	 * Also remember, you need to put this one in the correct place, which means
	 * the farmers are always in sorted order.
	 * 
	 * I know a Red-black/AVL tree might be better at here, but in order to make
	 * this simplier, we just find the correct position and put it at here. Same
	 * procedure as before. Same things need to be done as before
	 * 
	 * @param id     the id of the farmer
	 * @param weight the weight of farmer
	 * @param day    the day that those gallon of milk is produced.
	 * @return true if added successfully, otherwise(e.g. data is null, etc.)
	 *         false when weight<0
	 */
	public Boolean addData(Integer id, Integer weight, String day) {
		Boolean find = true;
		Boolean success = false;
		for (Farmer abc : Farmers) {
			if (abc.getId() == id) {
				success = abc.newValue(day, weight);
				find = false;
				break;
			}
		}
		if (find) {
			Farmer tmp = new Farmer(id);
			success = tmp.newValue(day, weight);
			for (int i = 0; i < Farmers.size(); i++) {
				if (Farmers.get(i).compareTo(tmp) > 0) {
					Farmers.add(i, tmp);
				}
			}
		}
		if (success) {
			ds.appendRow(new Object[] { day, id, weight });
			allID.add(id);
		}
		totalWeight += weight;
		return success;
	}

	/**
	 * This one just return a Farm_report class, main method will handle the
	 * rest stuff.
	 * 
	 * @return a FARM_REPORT class contain current farmers information
	 */
	public FARM_REPORT generateFarmReport() {
		if (ds == null) {
			information = "Should import first.";
			return null;
		}
		return new FARM_REPORT(Farmers);
	}

	/**
	 * This one just return a Annual_REPORT class, main method will handle the
	 * rest stuff.
	 * 
	 * @return a Annual_REPORT class contain current farmers information
	 */
	public Annual_REPORT generateAnnualReport() {
		if (ds == null) {
			information = "Should import first.";
			return null;
		}
		return new Annual_REPORT(Farmers);
	}

	/**
	 * This one just return a DATE_RANGE_REPORT class, main method will handle
	 * the rest stuff.
	 * 
	 * @return a DATE_RANGE_REPORT class contain current farmers information
	 */
	public DATE_RANGE_REPORT generateDateRangeReport(Date start, Date end) {
		if (ds == null) {
			information = "Should import first.";
			return null;
		}

		return new DATE_RANGE_REPORT(Farmers, start, end);
	}

	/**
	 * This one just return a MONTHLY_REPORT class, main method will handle the
	 * rest stuff.
	 * 
	 * @return a MONTHLY_REPORT class contain current farmers information
	 */
	public MONTHLY_REPORT generateMonthReport() {
		if (ds == null) {
			information = "Should import first.";
			return null;
		}
		return new MONTHLY_REPORT(Farmers);
	}

	/**
	 * remove those weight on a specific day of a farm. Same finding procedure
	 * as {@code addData}
	 * 
	 * @param id     the id of the farmer
	 * @param weight the weight of farmer to be removed
	 * @param day    the day that those gallon of milk is produced.
	 * @return true if added successfully, otherwise(e.g. data is null, etc.)
	 *         false, id not found, day not exist/weight<0
	 * @exception IllegalArgumentException some arument(date) not illegal
	 */
	public Boolean removeData(Integer id, Integer weight, String day)
			throws IllegalArgumentException {
		int idx = -1;
		for (int i = 0; i < Farmers.size(); i++) {
			if (Farmers.get(i).getId() == id) {
				idx = i;
				break;
			}
		}
		if (idx < 0) {
			information = "id not found";
			return false;
		}

		try {
		if (Farmers.get(idx).removeValue(day, weight)) {
			ds.reduceAmount(id, day, weight);
			totalWeight -= weight;
			if(Farmers.get(idx).getTotalWeight()==0) removeData(id);
			return true;
		} else {
			information = "day no exist or weight < 0";
			return false;
		}}catch(Exception e) {
			information = "day not valid";
			return false;
		}
	}

	public String getError() {
		String a = information;
		information = "";
		return a;
	}
	
	public Farmer findFarmer(int id) {
		int idx = -1;
		for (int i = 0; i < Farmers.size(); i++) {
			if (Farmers.get(i).getId() == id) {
				return Farmers.get(i);
			}
		}
		return null;

	}

	/**
	 * Remove the data of a farm at a specific day. You should also remove data
	 * from farmers and ds.
	 * 
	 * Hint: Use method {@code removeRow} to remove in ds
	 * 
	 * @param id  the id of the farmer
	 * @param day day information to be removed
	 * @return true if success, false if this data doesn't exist and when day
	 *         not exist/weight<0
	 * @exception IllegalArgumentException some arument(date) not illegal
	 */
	public Boolean removeData(Integer id, String day)
			throws IllegalArgumentException {
		int idx = -1;
		for (int i = 0; i < Farmers.size(); i++) {
			if (Farmers.get(i).getId() == id) {
				idx = i;
				break;
			}
		}
		if (idx < 0) {
			information = "id not found";
			return false;
		}
		Date day1 = null;
		try {
			day1 = dateDayFormat.parse(day);
		}catch(Exception e) {
			information = "day not valid";
			return false;
		}
		totalWeight -= Farmers.get(idx).getWeightByDay().get(day1);
		if (Farmers.get(idx).removeValue(day)) {
			ds.removeRow(id, day);
			if(Farmers.get(idx).getTotalWeight()==0) removeData(id);
			return true;
		}
		information = "day no exist or weight < 0";
		return false;
	}

	/**
	 * remove all information of this id. It means the ds will no longer contain
	 * information about this id And this id will also be removed from farmers
	 * list
	 * 
	 * @param id the id of the farmer
	 * @return true if success, false if this data doesn't exist
	 */
	public Boolean removeData(Integer id) {
		int idx = -1;
		for (int i = 0; i < Farmers.size(); i++) {
			if (Farmers.get(i).getId() == id) {
				idx = i;
				break;
			}
		}
		if (idx < 0) {
			information = "id not found";
			return false;
		}
		totalWeight -= Farmers.get(idx).getTotalWeight();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Farmer tmp = Farmers.remove(idx);
		allID.remove(tmp.getId());
		for (Date a : tmp.getWeightByDay().keySet()) {
			ds.removeRow(id, dateFormat.format(a));
		}
		return true;
	}

	/**
	 * 
	 * Load data from a ds, loop through each row in ds and add information to
	 * the ds in this class and farmers. The way to find has been discussed in
	 * addData, and you could make use of addData
	 * 
	 * 
	 * @param ds1
	 * @return true if success, false otherwise(ds doesn;t have required format,
	 *         etc. )
	 */
	public Boolean importData(DataFrame ds1) {
		Boolean toRe = true;
		for (Object[] a : ds1.rows) {
			try {
				boolean tmp = addData((Integer) a[1], (Integer) a[2],
						(String) a[0]);
				toRe &= tmp;
				if (!tmp)
					information += "Imported data doesn't meet format requirement, this data is not imported"
							+ System.lineSeparator()
							+ "  require: Date(2019-01-01),id(Farm 1), weight(25)"
							+ System.lineSeparator() + "  received: " + a[0]
							+ ",Farm " + a[1] + "," + a[2]
							+ System.lineSeparator();
			} catch (Exception e) {
				information += e.getMessage() + "\n";
			}
		}
		return toRe;
	}

	/**
	 * Add a new farmer with automatic index. the index is determined by the idx
	 * of the last element in {@code farmers}+1. Then same procedure as previous
	 * addData.
	 * 
	 * @param weight the weight of it
	 * @param day    the day of the data.
	 * @return true if success otherwise false(e.g. try catch with error.)
	 */
	public Boolean addData(Integer weight, String day) {
		return addData(Farmers.size(), weight, day);
	}

	@Override
	/**
	 * This is a special export, basically, directly return ds of this class
	 * would be enough
	 */
	public DataFrame export(TableView<Object[]> a) {
		// TODO Auto-generated method stub
		return ds;
	}

}
