package local.test.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import static org.testng.Assert.*;

/**
 * @author hernan
 * @version 1.0
 * 
 */
public class TestCaseExt {

	private static WebDriver _driver;
	private String testCaseName;
	private boolean acceptNextAlert;
	private StringBuffer verificationErrors;
	private String baseUrl;
	private int timeout;
	private Dimension dimension;
	private Point point;

	private static final String TYPE_NOT_FOUND = "Type Not Found";
	private static final String ERROR = "Error";
	private static final String EXCEPTION = "Exception";
	private static final String THROWABLE = "Throwable";
	private static final String SLASH = "/";

	protected static final int PASS = 1;
	protected static final int FAIL = 0;
	protected static final int ERR1 = -1;
	protected static final int ERR2 = -2;

	protected static final String LINK_TEXT = "linkText";
	protected static final String ID = "id";
	protected static final String XPATH = "xpath";
	protected static final String CSS_SELECTOR = "cssSelector";

	protected static final String ELEMENT_PRESENT = "ElementPresent";
	protected static final String ELEMENT_NOT_PRESENT = "ElementNotPresent";
	protected static final String TEXT_PRESENT = "TextPresent";
	protected static final String TEXT_NOT_PRESENT = "TextNotPresent";

	/*
	 * Constructor and Destructor
	 */

	public TestCaseExt() {
		this.setAcceptNextAlert(true);
		this.setVerificationErrors(new StringBuffer());
	}

	public void finalize() {
		if (_driver != null)
			_driver.quit();
	}

	/*
	 * Getters
	 */

	public WebDriver getDriver() {
		return _driver;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public boolean isAcceptNextAlert() {
		return acceptNextAlert;
	}

	public StringBuffer getVerificationErrors() {
		return verificationErrors;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public int getTimeout() {
		return timeout;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public Point getPoint() {
		return point;
	}

	/*
	 * Setters
	 */

	public Boolean setDriver(WebDriver driver) {
		_driver = driver;
		return true;
	}

	public Boolean setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
		return true;
	}

	public Boolean setAcceptNextAlert(boolean acceptNextAlert) {
		this.acceptNextAlert = acceptNextAlert;
		return true;
	}

	public Boolean setVerificationErrors(StringBuffer verificationErrors) {
		this.verificationErrors = verificationErrors;
		return true;
	}

	public Boolean setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		try {
			_driver.get(this.baseUrl + SLASH);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public Boolean setTimeout(int timeout) {
		this.timeout = timeout;
		try {
			_driver.manage().timeouts()
					.implicitlyWait(this.timeout, TimeUnit.SECONDS);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public Boolean setDimension(Dimension dimension) {
		this.dimension = dimension;
		try {
			_driver.manage().window().setSize(this.dimension);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	public Boolean setPoint(Point point) {
		this.point = point;
		try {
			_driver.manage().window().setPosition(this.point);
		} catch (Throwable e) {
			return false;
		}
		return true;
	}

	/**
	 * Helpers
	 */

	private String convertToRegEx(String text) {
		return "^[\\s\\S]*" + text + "[\\s\\S]*$";
	}

	/*
	 * Selenium Methods
	 */

	private boolean isElementPresent(By by) {
		try {
			_driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/*
	 * Extensions
	 */

	private String verifyElementPresentById(String idStr, Boolean verifyExist) {
		try {
			if (verifyExist)
				assertTrue(isElementPresent(By.id(idStr)));
			else {
				assertFalse(isElementPresent(By.id(idStr)));
			}
		} catch (Exception e) {
			return EXCEPTION;
		} catch (Error e) {
			return ERROR;
		} catch (Throwable e) {
			return THROWABLE;
		}
		return null;
	}

	private String verifyElementPresentBytLinkText(String linkTextStr,
			Boolean verifyExist) {
		try {
			if (verifyExist) {
				assertTrue(isElementPresent(By.linkText(linkTextStr)));
			} else {
				assertFalse(isElementPresent(By.linkText(linkTextStr)));
			}
		} catch (Exception e) {
			return EXCEPTION;
		} catch (Error e) {
			return ERROR;
		} catch (Throwable e) {
			return THROWABLE;
		}
		return null;
	}

	private String verifyElementPresentByXpath(String xpathStr,
			Boolean verifyExist) {
		try {
			if (verifyExist)
				assertTrue(isElementPresent(By.xpath(xpathStr)));
			else {
				assertFalse(isElementPresent(By.xpath(xpathStr)));
			}
		} catch (Exception e) {
			return EXCEPTION;
		} catch (Error e) {
			return ERROR;
		} catch (Throwable e) {
			return THROWABLE;
		}
		return null;
	}

	private String verifyElementPresentByCssSelector(String cssSelectorStr,
			Boolean verifyExist) {
		try {
			if (verifyExist)
				assertTrue(isElementPresent(By.cssSelector(cssSelectorStr)));
			else {
				assertFalse(isElementPresent(By.cssSelector(cssSelectorStr)));
			}
		} catch (Exception e) {
			return EXCEPTION;
		} catch (Error e) {
			return ERROR;
		} catch (Throwable e) {
			return THROWABLE;
		}
		return null;
	}

	private String verifyTextPresentByCssSelector(String cssSelectorStr,
			String textStr, Boolean verifyExist) {
		try {
			if (verifyExist)
				assertTrue(_driver.findElement(By.cssSelector(cssSelectorStr))
						.getText().matches(textStr));
			else {
				assertFalse(_driver.findElement(By.cssSelector(cssSelectorStr))
						.getText().matches(textStr));
			}
		} catch (Exception e) {
			return EXCEPTION;
		} catch (Error e) {
			return ERROR;
		} catch (Throwable e) {
			return THROWABLE;
		}
		return null;
	}

	private String verifyTextPresentByXpath(String xpathStr, String textStr,
			Boolean verifyExist) {
		String textStrRegEx = this.convertToRegEx(textStr);
		try {
			if (verifyExist)
				assertTrue(_driver.findElement(By.xpath(xpathStr)).getText()
						.matches(textStrRegEx));
			else {
				assertFalse(_driver.findElement(By.xpath(xpathStr)).getText()
						.matches(textStrRegEx));
			}
		} catch (Exception e) {
			return EXCEPTION;
		} catch (Error e) {
			return ERROR;
		} catch (Throwable e) {
			return THROWABLE;
		}
		return null;
	}

	/*
	 * Controllers
	 */

	private String verifyElementPresent(String type, String targetStr,
			Boolean verifyExist) {
		if (type.equals(LINK_TEXT)) {
			return this.verifyElementPresentBytLinkText(targetStr, verifyExist);
		} else if (type.equals(ID)) {
			return this.verifyElementPresentById(targetStr, verifyExist);
		} else if (type.equals(XPATH)) {
			return this.verifyElementPresentByXpath(targetStr, verifyExist);
		} else if (type.equals(CSS_SELECTOR)) {
			return this.verifyElementPresentByCssSelector(targetStr,
					verifyExist);
		} else {
			return TYPE_NOT_FOUND;
		}
	}

	private String verifyTextPresent(String type, String targetStr,
			Boolean verifyExist, String locationStr) {
		if (type.equals(CSS_SELECTOR)) {
			return this.verifyTextPresentByCssSelector(locationStr, targetStr,
					verifyExist);
		} else if (type.equals(XPATH)) {
			return this.verifyTextPresentByXpath(locationStr, targetStr,
					verifyExist);
		} else {
			return TYPE_NOT_FOUND;
		}
	}

	private int doVerify(String method, String type, String target,
			String locationStr) {
		String retval = null;
		if (method.equals(ELEMENT_PRESENT)) {
			retval = this.verifyElementPresent(type, target, true);
		} else if (method.equals(ELEMENT_NOT_PRESENT)) {
			retval = this.verifyElementPresent(type, target, false);
		} else if (method.equals(TEXT_PRESENT)) {
			retval = this.verifyTextPresent(type, target, true, locationStr);
		} else if (method.equals(TEXT_NOT_PRESENT)) {
			retval = this.verifyTextPresent(type, target, false, locationStr);
		} else {
			return ERR2;
		}
		if (retval == null)
			return PASS;
		return FAIL;
	}

	/*
	 * Wrappers
	 */

	protected int doVerifyElementPresent(String type, String target) {
		return this.doVerify(ELEMENT_PRESENT, type, target, null);
	}

	protected int doVerifyTextPresent(String type, String target,
			String locationStr) {
		return this.doVerify(TEXT_PRESENT, type, target, locationStr);
	}

	protected int doVerifyElementNotPresent(String type, String target) {
		return this.doVerify(ELEMENT_NOT_PRESENT, type, target, null);
	}

	protected int doVerifyTextNotPresent(String type, String target,
			String locationStr) {
		return this.doVerify(TEXT_NOT_PRESENT, type, target, locationStr);
	}

	protected int click(String type, String target) {
		try {
			if (type.equals(LINK_TEXT)) {
				_driver.findElement(By.linkText(target)).click();
			} else if (type.equals(CSS_SELECTOR)) {
				_driver.findElement(By.cssSelector(target)).click();
			} else if (type.equals(ID)) {
				_driver.findElement(By.id(target)).click();
			} else {
				return ERR2;
			}
		} catch (Exception e) {
			return ERR1;
		} catch (Error e) {
			return ERR1;
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

	protected int select(String type, String id, String target) {
		try {
			if (type.equals(ID)) {
				new Select(_driver.findElement(By.id(id)))
						.selectByVisibleText(target);
			} else {
				return ERR2;
			}
		} catch (Exception e) {
			return ERR1;
		} catch (Error e) {
			return ERR1;
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

	protected int input(String type, String target, String text) {
		try {
			if (type.equals(ID)) {
				_driver.findElement(By.id(target)).clear();
				_driver.findElement(By.id(target)).sendKeys(text);
			} else if (type.equals(CSS_SELECTOR)) {
				_driver.findElement(By.cssSelector(target)).clear();
				_driver.findElement(By.cssSelector(target)).sendKeys(text);
			} else {
				return ERR2;
			}
		} catch (Exception e) {
			return ERR1;
		} catch (Error e) {
			return ERR1;
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

	protected int moveToElement(String type, String target) {
		Actions actions = new Actions(_driver);
		WebElement el;
		try {
			if (type.equals(ID)) {
				el = _driver.findElement(By.id(target));
				actions.moveToElement(el).perform();
			} else if (type.equals(LINK_TEXT)) {
				el = _driver.findElement(By.linkText(target));
				actions.moveToElement(el).perform();
			} else if (type.equals(CSS_SELECTOR)) {
				el = _driver.findElement(By.cssSelector(target));
				actions.moveToElement(el).perform();
			} else if (type.equals(XPATH)) {
				el = _driver.findElement(By.xpath(target));
				actions.moveToElement(el).perform();
			} else {
				return ERR2;
			}
		} catch (Exception e) {
			return ERR1;
		} catch (Error e) {
			return ERR1;
		} catch (Throwable e) {
			return ERR1;
		}
		return PASS;
	}

	protected String getText(String type, String target) {
		String val = null;
		try {
			if (type.equals(XPATH)) {
				WebElement tmp = _driver.findElement(By.xpath(target));
				val = tmp.getText();
			} else {
				return ERROR;
			}
		} catch (Exception e) {
			return ERROR;
		} catch (Error e) {
			return ERROR;
		} catch (Throwable e) {
			return ERROR;
		}
		return val;
	}

	protected List<String> getSelectOptions(String type, String target) {
		WebElement el;
		List<WebElement> Options = new ArrayList<WebElement>();
		List<String> values = new ArrayList<String>();
		Select select;
		if (type.equals(ID)) {
			el = _driver.findElement(By.id(target));
			select = new Select(el);
			Options = select.getOptions();
		} else if (type.equals(XPATH)) {
			el = _driver.findElement(By.xpath(target));
			select = new Select(el);
			Options = select.getOptions();
		} else {
			return null;
		}
		for (WebElement option : Options) {
			values.add(option.getText());
		}
		return values;
	}

	protected String getAttributeValue(String type, String target,
			String attribute) {
		String retVal = null;
		if (type.equals(CSS_SELECTOR)) {
			retVal = _driver.findElement(By.cssSelector(target)).getAttribute(
					attribute);
		} else {
			return retVal;
		}
		return retVal;
	}

}
