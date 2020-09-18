package com.cnu.lwm2m.server.exception;

public class ExceptionConst {
	public static final int SUCCESS								= 0;
	public static final int FAIL								= 9999;

	// SYSTEM (0~199)
	public static final int SESSION_EXPIRED						= 20;
	public static final int ACCESS_DENIED						= 31;
	public static final int AUTHENTICATION_NEEDED				= 32;
	public static final int INVALID_LOGINID						= 101;
	public static final int INVALID_PASSWORD					= 102;
	public static final int UNREGISTERED_USER					= 103;
	public static final int UNREGISTERED_IP						= 104;
	public static final int LOCKED_USER							= 105;
	public static final int SERVICE_EXPIRED						= 106;
	public static final int DUPLICATE_USER						= 107;
	public static final int MISSING_REQUIRED_PARAMETER			= 131;
	public static final int NOT_VALID_PARAMETER					= 132;

	// AUTH (2xx)
	public static final int CANNOT_FOUND_PRIVATE_KEY			= 200;

	// FILE (3xx)
	public static final int FILE_MOVE_FAILURE					= 300;

	// SXSSF (Excel)
	public static final int EXCEL_FILE_NOT_FOUND				= 351;

	// NETWORK (5xx)
	public static final int UNKNOWN_HOST						= 501;

	// COMMON (6xx)
	public static final int COMMON_JSON_PARSING_FAIL			= 601;

	// LOGIC
	public static final int RSA_NO_SUCH_ALGORITHM				= 1401;

	// VALIDATION (15xx)
	public static final int VALIDATION_COMMON					= 1500;

	// DB (20xx)
	public static final int DB_COMMIT_FAILURE					= 2000;
}