package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * A class for storing a table format as a CSV file with quick indexing feature.
 * Still useful as: 1. remove duplicates in imported csv file 2. Easy for
 * FarmerManager to export data. 3. save runtime for manager class. Because user
 * will only add/remove some data. This is not the same as filter. User are not
 * able to filter data in the main GUI as the main GUI is supposed to represent
 * the overall data
 * 
 * 
 * @author Shaokang Jiang
 *
 */
public class DataFrameIndex extends DataFrame {

	// I think we need to use those variables
	// feel free to add more private data field

	protected HashMap<Date, HashMap<Integer, Integer>> Index;
	private static final String dayPattern = "yyyy-MM-dd";
	private static final SimpleDateFormat dateDayFormat = new SimpleDateFormat(
			dayPattern);
	/**
	 * Do whatever the DateFarme is done You need to initialize the Hashmap as
	 * well.
	 * 
	 * @param column the names of the columns for the new DataFrame
	 */
	public DataFrameIndex(String[] column, Object[] dataType) {
		super(column, dataType);
		Index = new HashMap<Date, HashMap<Integer, Integer>>();
	}

	/**
	 * 
	 * Those step should be done after the step of checking correctness is done.
	 * If the information of the given row is already existed in the form, e.g.
	 * if you try to access the date and farmid in the hashmap and the return
	 * value is not null. Then it means it is existed in the list. By using the
	 * int you get, you can access the list and add the gallons of milk to the
	 * form at the specific position.
	 * 
	 * If it is not existed in the hashmap, you should add it to the hashmap and
	 * store the position in the list to the hashmap.
	 * 
	 * Same as before, If invalid data, need give user a prompt
	 * 
	 * Append could only append positive weight
	 * 
	 * @param row the new row given as a String[]
	 * @throws IllegalArgumentException if the row does not have the same number
	 *                                  of columns as the DataFrame should have
	 *                                  or if passed in value doesn;t meet the
	 *                                  requirement of data at that position
	 */
	public void appendRow(Object[] row) throws IllegalArgumentException {
		try {
			Date day = dateDayFormat.parse((String) row[0]);
			if (((Integer) row[2]) < 0)
				throw new IllegalArgumentException("Invalid weight");
			if (checkData(row)) {
				
				if (Index.get(day) != null) {
					if (Index.get(day).get(row[1]) != null) {
						int idx = Index.get(day).get(row[1]);
						rows.get(idx)[2] = (Integer) rows.get(idx)[2]
								+ (Integer) row[2];
					} else {
						rows.add(row);
						Index.get(day).put((Integer) row[1],
								rows.size() - 1);
					}
				} else {
					rows.add(row);
					
					Index.put(day, new HashMap<Integer, Integer>());
					Index.get(day).put((Integer) row[1], rows.size() - 1);
				}
			} else
				throw new IllegalArgumentException("Failed to compute the row data: "+System.lineSeparator()
						+ "  require: Date(2019-01-01),id(Farm 1), weight(25)"
						+ System.lineSeparator() + "  received: " + row[0]+","+row[1]+","+row[2]);
		} catch (Exception e) {
			throw new IllegalArgumentException("Indexing error");
		}

	}

	/**
	 * Same procedure as before. Difference is that: when I find this element I
	 * will set its value directly to this value passed in
	 * 
	 *  If invalid data, throw {@link IllegalArgumentException}
	 * 
	 * @param id  the id of the farm
	 * @param day the day of the farm
	 * @param val
	 * @throws ParseException 
	 */
	public void setVal(int id, String day, int val) throws IllegalArgumentException, ParseException{
		if (checkValid(id, day)) {
			Date day1 = dateDayFormat.parse(day);
			int idx = Index.get(day1).get(id);
			rows.get(idx)[2] = val;
		} else {
			throw new IllegalArgumentException(
					"Invalid: id: " + id + " day: " + day);
		}
	}

	/**
	 * Same finding procedure as before. This time, you need to remove the entry
	 * for this farmer in this day. And if this farmer doesn;t have any day
	 * value that still contains gallon data. You do also need to remove the
	 * farmer entry.
	 * 
	 * And don't forget to remove the row in rows as well.
	 * 
	 * If invalid data, throw {@link IllegalArgumentException}
	 * 
	 * Structure: Day->id->weight
	 * 
	 * @param id
	 * @param day
	 * @throws ParseException 
	 * 
	 */
	public void removeRow(int id, String day) throws IllegalArgumentException, ParseException {
		if (checkValid(id, day)) {
			Date day1 = dateDayFormat.parse(day);
			int idx = Index.get(day1).get(id);
			rows.set(idx, null);
			Index.get(day1).remove(id);
			if (Index.get(day1).size() == 0) {
				Index.remove(day1);
			}
		} else {
			throw new IllegalArgumentException(
					"Invalid: id: " + id + " day: " + day);
		}
	}

	private boolean checkValid(int id, String day) throws ParseException {
		Date day1 = dateDayFormat.parse(day);
		if (Index.get(day1) != null) {
			if (Index.get(day1).get(id) != null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Same finding procedure as before. This time, you need to remove the entry
	 * for this farmer in this day. And if this farmer doesn;t have any day
	 * value that still contains gallon data. You do also need to remove the
	 * farmer entry.
	 * 
	 * Difference: reduce instead of removing
	 * 
	 * And don't forget to remove the row in rows as well.
	 * 
	 * If invalid data, need give user a prompt
	 * 
	 * @param id
	 * @param day
	 * @throws ParseException 
	 */
	public void reduceAmount(int id, String day, int weight) throws ParseException {
		if (weight < 0)
			throw new IllegalArgumentException("Invalid weight");
		if (checkValid(id, day)) {
			Date day1 = dateDayFormat.parse(day);
			int idx = Index.get(day1).get(id);
			rows.get(idx)[2] = (Integer) rows.get(idx)[2] - weight;
		} else {
			throw new IllegalArgumentException(
					"Invalid: id: " + id + " day: " + day);
		}
	}

}
