package local.test.util;

import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import local.test.util.Config;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;


/**
 * @author hernan
 * @version 1.0
 * 
 */
public class TestCaseHelper extends TestCaseExt {

	private Map<String, String> fields = new HashMap<String, String>();
	
	protected ArrayList<Map<String, String>> elementList;
	protected String testing;
	protected int counter;
	protected int counterPass;
	protected int counterFail;
	protected int retval;
	protected static int counterOverall;
	protected static int counterPassOverall;
	protected static int counterFailOverall;
	protected Iterator<String> iterator;
	protected String lnkName;
	protected String msg;
	
	public static final String TESTCASE = "testcase";
	public static final String BASE_URL = "selenium_baseurl";
	public static final String TIMEOUT = "selenium_timeout";
	public static final String DIMENSION_X = "selenium_dimensionx";
	public static final String DIMENSION_Y = "selenium_dimensiony";
	public static final String POINT_X = "selenium_pointx";
	public static final String POINT_Y = "selenium_pointy";
	public static final String SHARED_DRIVER = "selenium_sharedDriver";
	public static final String separator = "------------- ---------------- ---------------";

	public TestCaseHelper() {
		super();
		this.elementList = new ArrayList<Map<String, String>>();
		this.counter = 0;
		this.counterPass = 0;
		this.counterFail = 0;
		this.retval = -1;
	}

	public void setParams(Map<String, String> params) {
		this.fields = params;
	}
	
	public void finalize() {
		println("Cleaning up...");
		if (super.getDriver() != null)
			super.finalize();
	}

	public Boolean init(String testcase) {
		Config conf = new Config("config.properties");

		if(!this.fields.containsKey(BASE_URL))
			this.fields.put(BASE_URL, conf.getProperty("selenium_baseurl"));
		if(!this.fields.containsKey(TIMEOUT))
			this.fields.put(TIMEOUT, conf.getProperty("selenium_timeout").trim());
		if(!this.fields.containsKey(DIMENSION_X))
			this.fields.put(DIMENSION_X, conf.getProperty("selenium_dimensionx").trim());
		if(!this.fields.containsKey(DIMENSION_Y))
			this.fields.put(DIMENSION_Y, conf.getProperty("selenium_dimensiony").trim());
		if(!this.fields.containsKey(POINT_X))
			this.fields.put(POINT_X, conf.getProperty("selenium_pointx").trim());
		if(!this.fields.containsKey(POINT_Y))
			this.fields.put(POINT_Y, conf.getProperty("selenium_pointy").trim());
		if(!this.fields.containsKey(SHARED_DRIVER))
			this.fields.put(SHARED_DRIVER, conf.getProperty("selenium_sharedDriver"));		
		
		if (this.fields.get(BASE_URL) == null)
			return false;

		if (Integer.parseInt(this.fields.get(TIMEOUT)) == -1)
			this.fields.put(TIMEOUT, "30"); // default

		// re-use if there is already an instance
		if (Boolean.parseBoolean(this.fields.get(SHARED_DRIVER))) {
			WebDriver dr = super.getDriver();
			if (dr == null) {
				//println("Driver is null. Creating new instance...");
				if (!super.setDriver(new FirefoxDriver()))
					return false;
			}
		} else {
			if (!super.setDriver(new FirefoxDriver()))
				return false;
		}

		if (!super.setTestCaseName(testcase))
			return false;

		// setting params based on config file
		try {
			if (!super.setDimension(
					new Dimension(
							Integer.parseInt(this.fields.get(DIMENSION_X)),
							Integer.parseInt(this.fields.get(DIMENSION_Y))
					)
				)
			)
				return false;
			if (!super.setPoint(new Point(Integer.parseInt(this.fields.get(POINT_X)), Integer.parseInt(this.fields.get(POINT_Y)))))
				return false;
			if (!super.setBaseUrl(this.fields.get(BASE_URL)))
				return false;
			if (!super.setTimeout(Integer.parseInt(this.fields.get(TIMEOUT))))
				return false;
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public Map<String, String> getParams() {
		return this.fields;
	}

	public boolean setDriverBaseUrl(String url) {
		if (!super.setBaseUrl(url))
			return false;
		println("set baseUrl: " + url);
		return true;
	}

	public boolean setDriverTimeout(int to) {
		if (!super.setTimeout(to))
			return false;
		println("set timeout: " + to);
		return true;
	}

	public boolean setDriverDimension(int x, int y) {
		Dimension d = new Dimension(x, y);
		if (!super.setDimension(d))
			return false;
		println("set dimension(" + x + "," + y + ")");
		return true;
	}

	public boolean setDriverPoint(int x, int y) {
		Point p = new Point(1, 1);
		if (!super.setPoint(p))
			return false;
		println("set point(" + x + "," + y + ")");
		return true;
	}

	private void generateResult(int result, String method, String type,
			String target, String locationStr) {
		if (result == TestCaseHelper.PASS) {
			result(target, super.getTestCaseName(), true, method + "-" + type,
					locationStr);
			incrementCounterPass();
		} else {
			result(target, super.getTestCaseName(), false, method + "-" + type,
					locationStr);
			incrementCounterFail();
		}
	}

	public String convertToRegEx(String text) {
		return "^[\\s\\S]*" + text + "[\\s\\S]*$";
	}

	/*
	 * Printers
	 */

	public void result(String target, String tcName, Boolean pass,
			String method, String xpath) {
		reportTemplate(target, tcName, pass, method, xpath);
	}

	private void reportTemplate(String target, String tcName, Boolean pass,
			String method, String xpath) {
		String status = "Passed";
		if (!pass)
			status = "!Failed!";

		StringBuilder fStr = trimTestCaseName(tcName);

		StringBuilder text = new StringBuilder();
		text.append(status);
		text.append(" <" + fStr + ">");
		text.append(" <" + method + ">");
		text.append(" <" + target + ">");
		if (xpath != null)
			text.append(" <" + xpath + ">");
		System.out.println(text);
	}

	// remove the com.xxx
	private StringBuilder trimTestCaseName(String tcName) {
		final String dot = ".";
		String[] tmp = tcName.split("\\" + dot);
		StringBuilder str = new StringBuilder();
		String prep = "";
		for (int x = 0; x < tmp.length; x++) {
			if (x != 0 && x != 1) {
				str.append(prep + tmp[x]);
				prep = dot;
			}
		}
		return str;
	}

	public void println(Object msg) {
		System.out.println(msg);
	}

	public void printTotalVerification() {
		this.println(super.getTestCaseName() + " TestCase Total: "
				+ this.counter + " Pass: " + this.counterPass + " Fail: "
				+ this.counterFail);
		this.println(super.getTestCaseName() + " Suite Total: "
				+ TestCaseHelper.counterOverall + " Pass: "
				+ TestCaseHelper.counterPassOverall + " Fail: "
				+ TestCaseHelper.counterFailOverall);
		this.println(TestCaseHelper.separator);
	}

	/*
	 * Helpers
	 */

	public Map<String, String> newElement(String k, String v) {
		Map<String, String> tmp = new HashMap<String, String>();
		tmp.put(k, v);
		return tmp;
	}

	public void elementListReset() {
		this.elementList = new ArrayList<Map<String, String>>();
	}

	public void wait(int sec) throws InterruptedException {
		long msec = sec * 1000;
		Thread.sleep(msec);
	}

	public void incrementCounterPass() {
		this.counterPass++;
		this.incrementCounters();
		TestCaseHelper.counterPassOverall++;
	}

	public void incrementCounterFail() {
		this.counterFail++;
		this.incrementCounters();
		TestCaseHelper.counterFailOverall++;
	}

	private void incrementCounters() {
		this.counter++;
		TestCaseHelper.counterOverall++;
	}

	/*
	 * Operations
	 */

	public void doVerifyTextPresentList(ArrayList<Map<String, String>> e,
			String locationStr) {
		for (Map<String, String> list : e) {
			for (Map.Entry<String, String> listEntry : list.entrySet()) {

				// @param1 e.g. "xpath", "cssSelector", etc
				// @param2 e.g. "Dodge", "Chevrolet", etc
				// @param3 xpath value e.g. "//div[@id='topic_container']/h1"
				retval = this.doVerifyTextPresent(listEntry.getKey(),
						listEntry.getValue(), locationStr);
				generateResult(retval, "doVerifyTextPresentList",
						listEntry.getKey(), listEntry.getValue(), locationStr);
			}
		}
	}

	public void doVerifyElementPresentList(ArrayList<Map<String, String>> e) {
		for (Map<String, String> list : e) {
			for (Map.Entry<String, String> listEntry : list.entrySet()) {

				// @param1 element type e.g. "id", "linkText", etc
				// @param2 e.g. "Dodge", "Chevrolet", etc
				retval = this.doVerifyElementPresent(listEntry.getKey(),
						listEntry.getValue());
				generateResult(retval, "doVerifyElementPresentList",
						listEntry.getKey(), listEntry.getValue(), null);
			}
		}
	}

	public void doVerifyTextNotPresentList(ArrayList<Map<String, String>> e,
			String locationStr) {
		for (Map<String, String> list : e) {
			for (Map.Entry<String, String> listEntry : list.entrySet()) {

				// @param1 e.g. "xpath", "cssSelector", etc
				// @param2 e.g. "Dodge", "Chevrolet", etc
				// @param3 xpath value e.g. "//div[@id='topic_container']/h1"
				retval = this.doVerifyTextNotPresent(listEntry.getKey(),
						listEntry.getValue(), locationStr);
				generateResult(retval, "doVerifyTextNotPresentList",
						listEntry.getKey(), listEntry.getValue(), locationStr);
			}
		}
	}

	public void doVerifyElementNotPresentList(ArrayList<Map<String, String>> e) {
		for (Map<String, String> list : e) {
			for (Map.Entry<String, String> listEntry : list.entrySet()) {

				// @param1 element type e.g. "id", "linkText", etc
				// @param2 e.g. "Dodge", "Chevrolet", etc
				retval = this.doVerifyElementNotPresent(listEntry.getKey(),
						listEntry.getValue());
				generateResult(retval, "doVerifyElementNotPresentList",
						listEntry.getKey(), listEntry.getValue(), null);
			}
		}
	}

	public void generateManualResult(int testStatus, String type,
			String target, String location) {
		generateResult(testStatus, "generateManualPassResult", type, target,
				location);
	}

	public void checkStatus() {
		if (counterFail > 0) {
			assertTrue(false);
		}
	}
	
	/**
	 * 
	 * @return String random size of 10 alpha-numeric characters
	 */
	public static String rand(int length) {
		Random r = new Random();
		String alphabet = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append(alphabet.charAt(r.nextInt(alphabet.length())));
		}
		return builder.toString();
	}
}
