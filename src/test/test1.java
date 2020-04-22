package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Pair;

public class test1 {
	static String pattern = "yyyy-MM-dd";
	static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

	static SimpleDateFormat simpleMonDateFormat = new SimpleDateFormat("yyyy-MM");
	
	public static void main(String[] args) throws ParseException {
		Date tmp = new Date();
		tmp.setTime(18000000);
		System.out.println(tmp);
	}

}
