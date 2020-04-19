package application;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Basic component of entire structure, also has stastic function
 * 
 * @author shaokang
 *
 */
public class Farmer implements Comparable<Farmer> {

	private int id;
	private int totalWeight;
	private HashMap<Date, Integer> weightByDay;
	private HashMap<Date, Integer> weightByMonth;
	private HashMap<Date, Integer> weightByYear;
	private static final String monthPattern = "yyyy-MM";
	private static final String dayPattern = "yyyy-MM-dd";
	private static final String yearPattern = "yyyy";
	private static final SimpleDateFormat dateMonthFormat = new SimpleDateFormat(
			monthPattern);
	private static final SimpleDateFormat dateDayFormat = new SimpleDateFormat(
			dayPattern);
	private static final SimpleDateFormat dateYearFormat = new SimpleDateFormat(
			yearPattern);

	/**
	 * Constructor, setup each field by the passed in id
	 */
	public Farmer(int id) {
		this.id = id;
		totalWeight = 0;
		weightByDay = new HashMap<Date, Integer>();
		weightByMonth = new HashMap<Date, Integer>();
		weightByYear = new HashMap<Date, Integer>();
	}

	/**
	 * Should add this new value to each field. If the entry in the hashmap
	 * already exist, then increment it by the {@value weight} amount.
	 * 
	 * For e.g.
	 * 
	 * If there is a line of data: day = 2019-1-2, weight = 10.
	 * 
	 * The you need to do, weightByDay.get(Date(2019-1-2)) != null do:
	 * weightByDay.get(Date(2019-1-2)) += weight else create this entry and set
	 * the value to weight. Then find the weightByMonth:
	 * weightByMonth.get(Date(2019-1-1)) != null do things like before
	 * 
	 * same thing should be done for year
	 * 
	 * and totalweight should also be incremented.
	 * 
	 * Those are just pseudocode, not javacode
	 * 
	 * @param day
	 * @param weight
	 * @return false when weight < 0
	 */
	public Boolean newValue(String day1, int weight)
			throws IllegalArgumentException {
		if (weight < 0)
			return false;
		Date day;
		Date month;
		Date year;
		try {
			day = dateDayFormat.parse(day1);
			month = dateMonthFormat.parse(day1);
			year = dateYearFormat.parse(day1);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		if (weightByDay.containsKey(day)) {// exist
			weightByDay.put(day, weightByDay.get(day) + weight);
		} else {// not exist
			weightByDay.put(day, weight);
		}

		if (weightByMonth.containsKey(month)) {// exist
			weightByMonth.put(month, weightByMonth.get(month) + weight);
		} else {// not exist
			weightByMonth.put(month, weight);
		}

		if (weightByYear.containsKey(year)) {// exist
			weightByYear.put(year, weightByYear.get(year) + weight);
		} else {// not exist
			weightByYear.put(year, weight);
		}
		totalWeight += weight;
		return true;
	}

	/**
	 * Should remove this new value to each field. If the entry in the hashmap
	 * already exist, then decrease it by the {@value weight} amount.
	 * 
	 * the finding procedure is the same as before One thing dfferent is that
	 * when not found method should return false
	 * 
	 * and totalweight should also be decrement.
	 * 
	 * 
	 * @param day
	 * @param weight
	 * @return false when day not exist/weight<0
	 */
	public Boolean removeValue(String day1, int weight)
			throws IllegalArgumentException {
		if (weight < 0)
			return false;
		Date day;
		Date month;
		Date year;
		try {
			day = dateDayFormat.parse(day1);
			month = dateMonthFormat.parse(day1);
			year = dateYearFormat.parse(day1);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}

		if (weightByDay.containsKey(day)) {// exist
			if (weightByDay.get(day) <= weight)
				weightByDay.remove(day);
			else
				weightByDay.put(day, weightByDay.get(day) - weight);
		} else {// not exist
			return false;
		}

		if (weightByMonth.containsKey(month)) {// exist
			if (weightByMonth.get(month) <= weight)
				weightByMonth.remove(month);
			else
				weightByMonth.put(month, weightByMonth.get(month) - weight);
		}

		if (weightByYear.containsKey(year)) {// exist
			if (weightByYear.get(year) <= weight)
				weightByYear.remove(year);
			else
				weightByYear.put(year, weightByYear.get(year) - weight);
		}

		totalWeight -= weight;
		return true;
	}

	/**
	 * remove all weight in a day
	 * 
	 * @param day1
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Boolean removeValue(String day1) throws IllegalArgumentException {
		Date day;
		Date month;
		Date year;
		try {
			day = dateDayFormat.parse(day1);
			month = dateMonthFormat.parse(day1);
			year = dateYearFormat.parse(day1);
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		int weight;
		if (weightByDay.containsKey(day)) {// exist
			weight = weightByDay.remove(day);
		} else {// not exist
			return false;
		}

		if (weightByMonth.containsKey(month)) {// exist
			if (weightByMonth.get(month) <= weight)
				weightByMonth.remove(month);
			else
				weightByMonth.put(month, weightByMonth.get(month) - weight);
		}

		if (weightByYear.containsKey(year)) {// exist
			if (weightByYear.get(year) <= weight)
				weightByYear.remove(year);
			else
				weightByYear.put(year, weightByYear.get(year) - weight);
		}

		totalWeight -= weight;
		return true;
	}

	/**
	 * 
	 * @return the id of this farmer
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return the totalweight sent by this farmer
	 */
	public int getTotalWeight() {
		return totalWeight;
	}

	/**
	 * 
	 * @return weightByDay
	 */
	public HashMap<Date, Integer> getWeightByDay() {
		return weightByDay;
	}

	/**
	 * 
	 * @return weightByMonth
	 */
	public HashMap<Date, Integer> getWeightByMonth() {
		return weightByMonth;
	}

	/**
	 * 
	 * @return weightByYear
	 */
	public HashMap<Date, Integer> getWeightByYear() {
		return weightByYear;
	}

	@Override
	/**
	 * Compare with another object based on their id.
	 * 
	 * @return this.getId() - o.getId()
	 */
	public int compareTo(Farmer o) {
		return this.getId() - o.getId();
	}

}
