package com.naukri;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Tester {
	
	public static void main(String[] args) {
		String fileTimeStamp = new SimpleDateFormat("yyyy_MMM_dd_hh:mm:ss'.txt'").format(new Date());
		System.out.println(fileTimeStamp);
	}

}
