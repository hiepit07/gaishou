package vn.bananavietnam.ict.selenium;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

public class SeleniumCommon {

	// define test mode here
	final String IE_VN_TEST = "1";
	final String IE_JP_TEST = "2";
	final String IE_EN_TEST = "3";
	final String FF_VN_TEST = "4";
	final String FF_JP_TEST = "5";
	final String FF_EN_TEST = "6";
	final String CHR_VN_TEST = "7";
	final String CHR_JP_TEST = "8";
	final String CHR_EN_TEST = "9";
	final String EDGE_VN_TEST = "10";
	final String EDGE_JP_TEST = "11";
	final String EDGE_EN_TEST = "12";
	String[] testModeArray = {
		IE_VN_TEST,IE_JP_TEST, IE_EN_TEST,
		FF_VN_TEST, FF_JP_TEST, FF_EN_TEST,
		CHR_VN_TEST, CHR_JP_TEST,CHR_EN_TEST,
		EDGE_VN_TEST, EDGE_JP_TEST, EDGE_EN_TEST
	};

	
	
	private static final String BANANA_DB_IP = "172.16.0.175";
	private static final String BANANA_DB_ROOT = "root";
	private static final String BANANA_DB_ROOT_PASSWORD = "B@nana2016";
	private static final String DUMP_FILE_EXTENSION = ".csv";
	private static final String MY_SQL_SHORTCUT_FILE = "mysql.exe.lnk";
	// define log file path here
	private static final String LOG_FOLDER_PATH = "E:/Data/Spring Tool Suite - Vietnam Banana/logs/";

	/**
	 * print database table's data to CSV file
	 * 
	 * @param databaseName : name of database
	 * @param fileName : full path to file that needs to print
	 * @param tableName : name of table in database to print
	 * @return true/false : operation successful/failed
	 */
	public static boolean dumpDataToFile(String databaseName, String fileName, String tableName) {
		// get folder from fileName
		File file = new File(fileName + DUMP_FILE_EXTENSION);
		String folder = FilenameUtils.getFullPathNoEndSeparator(file.getAbsolutePath());
		File theDir = new File(folder);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;
			try {
				theDir.mkdirs();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}

		// start dumping data
	    try {
	    	String commandString = MY_SQL_SHORTCUT_FILE + " -u " + BANANA_DB_ROOT + " -p"
	    			+ BANANA_DB_ROOT_PASSWORD + " -h " + BANANA_DB_IP + " " + databaseName + " -e "
	    			+ "\"select * from " + tableName + "\" > \"" + fileName + DUMP_FILE_EXTENSION + "\"";
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.exe", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("Data dumping started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Data dumping failed");
			return false;
		}
	    return true;
	}

	/**
	 * execute SQL statements in SQL file
	 * 
	 * @param databaseName : name of database
	 * @param fileName : full path to file that needs to print
	 * @return true/false : operation successful/failed
	 */
	public static boolean executeSqlFile(String databaseName, String fileName) {
		// start executing
	    try {
	    	String commandString = MY_SQL_SHORTCUT_FILE + " -u " + BANANA_DB_ROOT + " -p"
	    			+ BANANA_DB_ROOT_PASSWORD + " -h " + BANANA_DB_IP + " " + databaseName + " < "
	    			+ fileName;
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.exe", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("SQL executing started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("SQL executing failed");
			return false;
		}
	    return true;
	}

	/**
	 * simulate client's session timeout by fast forwarding time
	 * 
	 * @return true/false : operation successful/failed
	 */
	public static boolean sessionTimeOut() {
		// start
	    try {
	    	long ONE_MINUTE_IN_MILLIS = 60000; // milliseconds

	    	Calendar date = Calendar.getInstance();
	    	long t = date.getTimeInMillis();
	    	Date afterAddingTenMins = new Date(t + (10 * ONE_MINUTE_IN_MILLIS));
	    	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.lnk", "/c", "time " + dateFormat.format(afterAddingTenMins));
	    	Process p = pb.start();
	    	System.out.println("Session timeout started: " + dateFormat.format(afterAddingTenMins));
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Session timeout failed");
			return false;
		}
	    return true;
	}

	/**
	 * simulate client's connection failure by disabling local area connection
	 * 
	 * @return true/false : operation successful/failed
	 */
	public static boolean disableClientConnection() {
		// start
	    try {
	    	String commandString = "netsh interface set interface \"Local Area Connection\" DISABLE";
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.lnk", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("Connection disabling started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection disabling failed");
			return false;
		}
	    return true;
	}

	/**
	 * re-enable client's connection by enabling local area connection
	 * 
	 * @return true/false : operation successful/failed
	 */
	public static boolean enableClientConnection() {
		// start
	    try {
	    	String commandString = "netsh interface set interface \"Local Area Connection\" ENABLE";
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.lnk", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("Connection enabling started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection enabling failed");
			return false;
		}
	    return true;
	}

	/**
	 * simulate server's connection failure by disabling user's permission in database
	 * 
	 * @param databaseName : name of database
	 * @param username : name of user that needs to disable permission
	 * @return true/false : operation successful/failed
	 */
	public static boolean disableDBServerConnection(String databaseName, String username) {
		// start
	    try {
	    	String commandString = MY_SQL_SHORTCUT_FILE +  " -u " + BANANA_DB_ROOT + " -p"
	    			+ BANANA_DB_ROOT_PASSWORD + " -h " + BANANA_DB_IP + " -e "
	    			+ "\"revoke all on " + databaseName + ".* from '" + username + "'@'%'\"";
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.exe", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("Connection disabling started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection disabling failed");
			return false;
		}
	    return true;
	}

	/**
	 * re-enable server's connection by enabling user's permission in database
	 * 
	 * @param databaseName : name of database
	 * @param username : name of user that needs to disable permission
	 * @return true/false : operation successful/failed
	 */
	public static boolean enableDBServerConnection(String databaseName, String username) {
		// start
	    try {
	    	String commandString = MY_SQL_SHORTCUT_FILE +  " -u " + BANANA_DB_ROOT + " -p"
	    			+ BANANA_DB_ROOT_PASSWORD + " -h " + BANANA_DB_IP + " -e "
	    			+ "\"grant all on " + databaseName + ".* to '" + username + "'@'%'\"";
	    	ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "start", "/wait", "cmd.exe", "/c", commandString);
	    	Process p = pb.start();
	    	System.out.println("Connection enabling started: " + commandString);
	    	System.out.println("Result code: " + p.waitFor());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection enabling failed");
			return false;
		}
	    return true;
	}

	/**
	 * print log files based on checkpoints array and log file paths array 
	 * 
	 * @param checkPointArray : checkpoints array
	 * @param logFileNameArray : log file paths array
	 */
	public static void startPrintingLog(ArrayList<Long> checkPointArray, ArrayList<String> logFileNameArray) {
		// get banana log file in folder
		File inputFileBanana = getFile(LOG_FOLDER_PATH, "banana");
		// get spring log file in folder
		File inputFileSpring = getFile(LOG_FOLDER_PATH, "spring_framework");
		// start writing log evidence
		for (int i = 0; i < checkPointArray.size(); i++) {
			// get current check point
			long currentCheckPoint = checkPointArray.get(i);
			// check if array is at last element or not
			if (i < checkPointArray.size() - 1) {
				// get next check point
				long nextCheckPoint = checkPointArray.get(i + 1);
				File outputFileBanana = new File(logFileNameArray.get(i));
				File outputFileSpring = new File(logFileNameArray.get(i).replace(".log", "_spring.log"));
				// write banana file
				if (writeEvidenceLog(inputFileBanana, outputFileBanana, currentCheckPoint, nextCheckPoint)) {
					System.out.println("Banana file is written successfully!");
				} else {
					System.out.println("Banana file is failed to write!");
				}
				// write spring file
				if (writeEvidenceLog(inputFileSpring, outputFileSpring, currentCheckPoint, nextCheckPoint)) {
					System.out.println("Spring file is written successfully!");
				} else {
					System.out.println("Spring file is failed to write!");
				}
			} else {
				// last element
				File outputFileBanana = new File(logFileNameArray.get(i));
				File outputFileSpring = new File(logFileNameArray.get(i).replace(".log", "_spring.log"));
				// write banana file
				if (writeEvidenceLog(inputFileBanana, outputFileBanana, currentCheckPoint, 0)) {
					System.out.println("Banana file is written successfully!");
				} else {
					System.out.println("Banana file is failed to write!");
				}
				// write spring file
				if (writeEvidenceLog(inputFileSpring, outputFileSpring, currentCheckPoint, 0)) {
					System.out.println("Spring file is written successfully!");
				} else {
					System.out.println("Spring file is failed to write!");
				}
			}
		}
	}

	/**
	 * create checkpoint of current date time
	 * 
	 * @return Long type of current date time (removed characters other than digits)
	 */
	public static long createCurrentCheckPoint() {
		// create current time string
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss.SSS");
		String currentTime = sdf1.format(cal.getTime());
		currentTime = currentTime.replaceAll("[^\\d]", "");
		return Long.valueOf(currentTime);
	}

	/**
	 * print a specified part of tomcat's log file based on time checkpoints
	 * 
	 * @param inputFile : full path to tomcat's log file
	 * @param outputFile : path to output file, same folder with evidence images
	 * @param currentCheckPoint : checkpoint's start
	 * @param nextCheckPoint : checkpoint's end
	 * @return true/false : operation successful/failed
	 */
	private static boolean writeEvidenceLog(File inputFile, File outputFile, long currentCheckPoint, long nextCheckPoint) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(inputFile));
			BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
	
			// create current date string
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
			String currentDate = sdf.format(cal.getTime());

			// file reading starts
			String currentLine;
			while ((currentLine = reader.readLine()) != null) {
			    // trim newline when comparing with lineToRemove
			    String trimmedLine = currentLine.trim();
			    if (trimmedLine.indexOf(currentDate) >= 0) {
			    	// create current line time string
			    	String currentLineTime = trimmedLine.substring(0, 23);
				    currentLineTime = currentLineTime.replaceAll("[^\\d]", "");
				    long currentLineTimeValue = Long.valueOf(currentLineTime);

				    // compare time
				    if (nextCheckPoint != 0) {
				    	if (currentLineTimeValue < currentCheckPoint || currentLineTimeValue >= nextCheckPoint) {
				    		continue;
				    	} else {
				    		writer.write(currentLine + System.getProperty("line.separator"));
				    	}
				    } else {
				    	if (currentLineTimeValue < currentCheckPoint) {
				    		continue;
				    	} else {
				    		writer.write(currentLine + System.getProperty("line.separator"));
				    	}
				    }
			    }
			}
			writer.close();
			reader.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * get the file in folder with the specified name
	 * 
	 * @param dir : full path to folder
	 * @return File object
	 */
	private static File getFile(String dir, String fileName) {
	    File fl = new File(dir);
	    File[] files = fl.listFiles(new FileFilter() {          
	        public boolean accept(File file) {
	            return file.isFile();
	        }
	    });
	    File choice = null;
	    for (File file : files) {
	    	if (file.getName().equals(fileName)) {
	    		choice = file;
	    	}
	    }
	    return choice;
	}

	/**
	 * setup driver and URL for file SELENIUM run.
	 */
	static void setupTestSelenium(String baseUrl) {
		
		
		
	}

	static String[] setImagePath(String screenId, String status) {
		// define test mode image path here
		final String IE_VN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/IE/vn/";
		final String IE_JP_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/IE/jp/";
		final String IE_EN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/IE/en/";
		final String FF_VN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Firefox/vn/";
		final String FF_JP_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Firefox/jp/";
		final String FF_EN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Firefox/en/";
		final String CHR_VN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Chrome/vn/";
		final String CHR_JP_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Chrome/jp/";
		final String CHR_EN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Chrome/en/";
		final String EDGE_VN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Edge/vn/";
		final String EDGE_JP_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Edge/jp/";
		final String EDGE_EN_IMG_PATH = "selenium_test_images/" + screenId + "/"+status+"/Edge/en/";
		String[] testImagePathArray = { IE_VN_IMG_PATH, IE_JP_IMG_PATH, IE_EN_IMG_PATH, FF_VN_IMG_PATH, FF_JP_IMG_PATH,
				FF_EN_IMG_PATH, CHR_VN_IMG_PATH, CHR_JP_IMG_PATH, CHR_EN_IMG_PATH, EDGE_VN_IMG_PATH, EDGE_JP_IMG_PATH,
				EDGE_EN_IMG_PATH, };
		return testImagePathArray;
	}
}
