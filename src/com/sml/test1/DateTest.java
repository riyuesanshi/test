package com.sml.test1;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		
		String datePattern = "yyyy-MM-dd-HH'.'";
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		;

		System.out.println(sdf.format(now));
	}

}
