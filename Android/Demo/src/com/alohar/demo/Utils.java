/*
 * Copyright (C) 2011 Alohar Mobile
 *
 */
package com.alohar.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	public static String formatPeriod(long periodInSeconds, boolean enableSeconds) {
		StringBuilder builder = new StringBuilder();
		long seconds = periodInSeconds % 60;  //seconds
		long minutes = (periodInSeconds / 60) % 60; //minutes
		long hours = periodInSeconds/3600;	//hours

		if (hours > 0) {
			builder.append(hours).append(" hr").append(hours > 1?"s ":" ");
		}
		if (minutes > 0) {
			builder.append(minutes).append(" min").append(minutes > 1?"s ":" ");
		}
		if (seconds >= 0 && enableSeconds) {
			builder.append(seconds).append(" sec").append(seconds > 1?"s ":" ");
		}
		return builder.toString();
	}

	private static Date date = new Date();
	public static String formatDateTime(long timeInMilliseconds){
		SimpleDateFormat format = new SimpleDateFormat("EEEE, MMM dd yyyy");
		date.setTime(timeInMilliseconds);
		return format.format(date);
	}

	public static String formatTime(long timeInMilliseconds) {
		SimpleDateFormat format = new SimpleDateFormat("E, dd MMMM, yyyy h:mma");
		date.setTime(timeInMilliseconds);
		return format.format(date);
	}

	public static String formatTimeMs(long timeInMilliseconds) {
		SimpleDateFormat format = new SimpleDateFormat("h:mm:ss:SSS");
		date.setTime(timeInMilliseconds);
		return format.format(date);
	}

	public static String formatStayTime(long startTimeInSeconds, long endTimeInSeconds) {
		SimpleDateFormat format = new SimpleDateFormat("h:mma");
		date.setTime(startTimeInSeconds * 1000);
		return String.format("%s %s", format.format(date), formatPeriod(endTimeInSeconds - startTimeInSeconds, false));

	}

}
