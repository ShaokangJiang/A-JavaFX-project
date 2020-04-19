package test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class test {

	static String pattern = "yyyy-MM-dd";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	static SimpleDateFormat simpleMonDateFormat = new SimpleDateFormat("yyyy-MM");
	
	public static void main(String[] args) throws ParseException {
		Date a = new Date();
		a = simpleDateFormat.parse("2019-05-03");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		String strDate = dateFormat.format(a);
		System.out.print(strDate);
		String abc = "abc,";
		System.out.print(abc.substring(0,abc.length()-1));
		System.out.println(Integer.parseInt("Farm 1".split("Farm ")[1]));
		System.out.print(System.lineSeparator());
		
		
	}
}
