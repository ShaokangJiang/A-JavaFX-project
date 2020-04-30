package application;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for storing a table format as a CSV file. You could think each line
 * in CSV as a row and each column in CSV as a cell
 * 
 * Format of dataType in this case: String, farmer_id, weight
 * 
 * @author Shaokang Jiang
 *
 */
public class DataFrame {

	// some sample private data field. You may change them.
	// All of them should be in private access
	protected String[] column;
	protected List<Object[]> rows;
	protected Object[] dataType;
	public int colNum;

	/**
	 * Create a new DataFrame with a given list of columns. column could be
	 * thought as the header of your table. For example, if csv file is:
	 * Time,rType,id,Speed,lane,u 0,MainRoad,200,58,0,660.09908
	 * 0,MainRoad,201,58,1,625.52486 0,MainRoad,202,58,2,590.95306
	 * 0,MainRoad,203,58,0,556.38140
	 * 
	 * Then, The pass in column could be [Time,rType,id,Speed,lane,u]
	 * 
	 * For the dataType field, It will be used to validate if the future passed
	 * in value is in the correct data type or not. The passed in dataType only
	 * needs to be in the same class as data needs. It doesn;t need to be
	 * exactly the same as the first row. But usually the first row is being
	 * passed in for representing this feature.
	 * 
	 * For the example above, the passed in datatype could be
	 * [0,MainRoad,200,58,0,660.09908]
	 * 
	 * You will need to use this to determine if the future passed in row is in
	 * good format or not in the append row, setcell method.
	 * 
	 * And before setup, you need to transform those value from String to their
	 * specific format. In this project case, first one should be transformed to
	 * java.util.Date second one should be transformed to int, third one should
	 * be transformed to int
	 * 
	 * @param column the names of the columns for the new DataFrame
	 */
	public DataFrame(String[] column, Object[] dataType) {
		this.column = column;
		this.dataType = dataType;
		rows = new ArrayList<Object[]>();
		colNum= column.length;
		
	}

	protected boolean checkData(Object[] row) {
		if (row.length != dataType.length)
			return false;
		boolean toRe = true;
		for (int i = 0; i < dataType.length; i++) {
			toRe &= row[i].getClass().equals(dataType[i].getClass());
		}
		return toRe;
	}

	/**
	 * Return the nth row.
	 * 
	 * @param pos the nth row that we want to return, first row is row 0
	 * @return the row String[]
	 */
	public Object[] getRow(int pos) {
		return rows.get(pos);
	}

	/**
	 * Get the value in the specific cell. To define a cell, it is using the col
	 * and row position.
	 * 
	 * @param row    position of the row first row is at pos 0
	 * @param column column's position, start from 0
	 * @return the value at the specific cell
	 */
	public Object getCell(int row, int column) {
		return rows.get(row)[column];
	}

	/**
	 * Change data of a specific cell to the value passed in
	 * 
	 * At the same time, you need to test if the passed in value is in correct
	 * dataType or not.
	 * 
	 * For datatype, refer to the dataType field defined when initialized
	 * 
	 * For testing if value is in correct dataType, you could use a reference at
	 * geeksforgeeks I forget the specific page // This method tells us whether
	 * the object is an // instance of class whose name is passed as a // string
	 * 'c'. public static boolean fun(Object obj, String c) throws
	 * ClassNotFoundException { return Class.forName(c).isInstance(obj); }
	 * 
	 * You need to handle the exception correcctly except
	 * IllegalArgumentException.
	 * 
	 * @param row    position of the row first row is at pos 0
	 * @param column column's position, start from 0
	 * @param value  the new value for this cell
	 * @exception IllegalArgumentException if passed in value doesn;t meet the
	 *                                     requirement of data at that position
	 */
	public void setCell(int row, int column, Object value)
			throws IllegalArgumentException {
		if (rows.get(row)[column].getClass().equals(value)) {
			rows.get(row)[column] = value;
		} else {
			throw new IllegalArgumentException("Data not valid!");
		}
	}

	/**
	 * Appends a new row to the DataFrame.
	 * 
	 * At the same time, you need to test if the passed in value is in correct
	 * dataType or not.No need to test if there is duplicate or not.
	 * 
	 * For datatype, refer to the dataType field defined when initialized
	 * 
	 * For testing if value is in correct dataType, you could use a reference at
	 * geeksforgeeks I forget the specific page // This method tells us whether
	 * the object is an // instance of class whose name is passed as a // string
	 * 'c'. public static boolean fun(Object obj, String c) throws
	 * ClassNotFoundException { return Class.forName(c).isInstance(obj); }
	 * 
	 * You need to handle the exception correcctly except
	 * IllegalArgumentException.
	 * 
	 * @param row the new row given as a String[]
	 * @throws IllegalArgumentException if the row does not have the same number
	 *                                  of columns as the DataFrame should have
	 *                                  or if passed in value doesn;t meet the
	 *                                  requirement of data at that position
	 */
	public void appendRow(Object[] row) throws IllegalArgumentException {
		if (checkData(row)) {
			rows.add(row);
		} else {
			throw new IllegalArgumentException("Data not valid!");
		}
	}

	/**
	 * Returns the column names of this DataFrame.
	 * 
	 * @return the column names
	 */
	public String[] getColumnNames() {
		return column;
	}

	/**
	 * Return the name of the column at the passed in position.
	 * 
	 * @param pos the position of the column
	 * @return the name of the column at that position
	 */
	public String getColumnName(int pos) {
		return column[pos];
	}

	/**
	 * Returns the position of the column with the given name.
	 * 
	 * @param name the name of the column
	 * @return the column's position -1 for not found
	 */
	public int getColumnPos(String name) {
		for (int i = 0; i < column.length; i++) {
			if (column[i].equals(name)) {
				return i;
			}
		}
		return -1;
	}

}