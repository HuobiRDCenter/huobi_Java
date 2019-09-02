package com.huobi.client.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
	/**
	 * 时间戳转字符串
	 * 
	 * @param time
	 * @param formatter
	 *            yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String convertTimeStamp(Long time, String formatter) {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(formatter);
		return dateTimeFormatter.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault()));
	}

	/**
	 * 默认格式的时间戳转字符串
	 * 
	 * @param time
	 * @param formatter
	 * @return
	 */
	public static String defaultConvertTimeStamp(Long time) {
		return convertTimeStamp(time, "yyyy-MM-dd HH:mm:ss");
	}
}
