package com.java.DAO;

import java.util.Date;

public class BaseDAO {

	public static int logId = 1000;

	public static int getLogId() {

		logId = logId + 1;

		return logId;

	}

	public static Date getCurrentTime() {

		return new Date();
	}

}

