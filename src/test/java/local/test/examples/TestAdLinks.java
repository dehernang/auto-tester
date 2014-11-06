package local.test.examples;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import local.test.util.TestCaseHelper;

/**
 * @author hernan
 * @version 1.0
 * 
 */
public class TestAdLinks extends TestCaseHelper {

	@BeforeClass
	public void oneTimeSetUp() throws Exception {
		System.out.println("Running " + getClass().getSimpleName() + "...");
		Boolean good = super.init(TestAdLinks.class.getName());
		if (!good)
			throw new Exception();
	}

	@Test(priority = 1)
	public void testHomeAdLinks() throws Exception {

		// define Google logo by tag id
		elementList.add(newElement(ID, "hplogo"));
		// verify the list
		doVerifyElementPresentList(elementList);
		// enter "selenium" in text box
		input(ID, "gbqfq", "selenium");
		// click search button
		click(ID, "gbqfba");

		// reset list
		elementList.clear();
		// define result stat container
		elementList.add(newElement(XPATH, "//div[@id='resultStats']"));
		// define first ad result
		elementList.add(newElement(CSS_SELECTOR, "h3.r > a"));
		// verify the list
		doVerifyElementPresentList(elementList);

		super.checkStatus();
	}

	@AfterClass
	public void oneTimeTearDown() throws Exception {
		printTotalVerification();
	}

}
