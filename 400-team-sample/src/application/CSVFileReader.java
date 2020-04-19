package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Handle read/write with CSV file
 * 
 * @author shaokang
 * 
 */
public class CSVFileReader {

	/**
	 * Create a DataFrame based on the passed in CSV file.
	 * 
	 * You can treat the first row after header in csv file as the default
	 * dataType
	 * 
	 * You need to prompt a window if dataType if is incorrect or fail to be
	 * converted
	 * 
	 * For example, correct format: date,farm_id,weight 2019-1-1,Farm 0,6760
	 * Wromg data: 2019-1-1,ZU4XV2,11924
	 * 
	 * You won't be able to convert or interpreted it to the format of xx(in
	 * Integer) At this time, you should prompt a dialog to tell user data file
	 * has error and return null.
	 * 
	 * @param csvFile the input CSV file
	 * @return the DataFrameIndex generated, we want as this method will only
	 *         used in the import and setup field in FarmerManager class.
	 * @throws FileNotFoundException
	 * @throws Exception             if CSV file contains error: e.g. DataType
	 *                               in this row is not correct as it should be.
	 * 
	 * 
	 */
	public DataFrameIndex readCSV(File[] csvFile)
			throws RuntimeException, FileNotFoundException {
		Scanner csv = new Scanner(csvFile[0]);
		String first;
		String second;
		first = csv.nextLine();
		second = csv.nextLine();

		DataFrameIndex toRe = new DataFrameIndex(first.split(","),
				convert(second));

		while (csv.hasNextLine()) {
			toRe.appendRow(convert(csv.nextLine()));
		}

		csv.close();

		for (int i = 1; i < csvFile.length; i++) {
			csv = new Scanner(csvFile[i]);
			csv.nextLine();
			toRe.appendRow(convert(csv.nextLine()));
			while (csv.hasNextLine()) {
				toRe.appendRow(convert(csv.nextLine()));
			}
		}

		return toRe;
	}

	/**
	 * To convert string to the required type Specified by the assignment In the
	 * form: date,farm_id,weight 2019-1-1,Farm 0,6760
	 *
	 * To the form: [2019-1-1, 0, 6760] [String, int, int]
	 * 
	 * Need remove farm in this case
	 * 
	 * @param a
	 * @return
	 */
	private Object[] convert(String a) throws RuntimeException {
		String[] handle = a.split(",");
		return new Object[] { handle[0],
				Integer.parseInt(handle[1].split("Farm ")[1]),
				Integer.parseInt(handle[2]) };
	}

	private String merge(Object[] a) {
		String toRe = "";
		for (Object b : a)
			toRe += b.toString() + ",";
		return toRe.substring(0, toRe.length() - 1);
	}

	/**
	 * Write a DataFrame to a CSV file.
	 * 
	 * Remember the agreement that: Though the farm\_id field in sample is in
	 * String format as farm 0. Since it is in the format of "farm xx" in any
	 * example, so we remove the farm part and remain the xx part. Reason to do
	 * this is for optimizing speed when sort and easier to index.
	 * 
	 * So, for the farmer\_id col, you need to turn the number "xx" into "farm
	 * xx" and then write to CSV file
	 * 
	 * @param f       the DataFrame
	 * @param csvFile the file to write
	 * @throws IOException
	 * @throws Exception   if file I/O errors happens
	 */
	public void writeToCSV(DataFrame f, File csvFile)
			throws RuntimeException, IOException {
		FileWriter out = new FileWriter(csvFile);
		out.write(merge(f.getColumnNames()) + System.lineSeparator());
		for (Object[] a : f.rows) {
			a[1] = "Farm "+a[1];
			out.write(merge(a) + System.lineSeparator());
		}
		out.flush();
		out.close();
	}

}
