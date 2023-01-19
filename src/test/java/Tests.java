import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import org.openqa.selenium.WebDriver;

import automation.Utils.Constants;
import automation.Utils.FrameworkProperties;
import automation.Utils.Utils;
import automation.drivers.DriverSingleton;
import automation.pages.BuyItemPage;
import automation.pages.CheckoutPage;
import automation.pages.HomePage;
import automation.pages.SearchResultsPage;
import automation.pages.SignInPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class Tests {
	static FrameworkProperties frameworkProperties;
	static WebDriver driver;
	static SignInPage signInPage;
	static HomePage homePage;
	static SearchResultsPage searchResultsPage;
	static BuyItemPage buyItemPage;
	static CheckoutPage checkoutPage;
	static String inputString;
	static Boolean expectedResult;

	@BeforeAll
	public static void initializeObjects() {
		frameworkProperties = new FrameworkProperties();
		DriverSingleton.getInstance(frameworkProperties.getProperty(Constants.BROWSER));
		driver = DriverSingleton.getDriver();
		homePage = new HomePage();
		searchResultsPage = new SearchResultsPage();
		buyItemPage = new BuyItemPage();
		checkoutPage = new CheckoutPage();
	}

	@Test
	public void test01TestingAddingProductToCartAsGuest() {
		driver.get(Constants.URL);
		homePage.searchElement(frameworkProperties.getProperty("product_name"));
		searchResultsPage.clickOnfirstResult();

		Utils.switchDriverToTab(1, driver);

		buyItemPage.setQuantity(frameworkProperties.getProperty("number_of_items"));
		buyItemPage.clickBuyNow();
		buyItemPage.clickCheckoutAsGuest();
		checkoutPage.ProvideBillingDetails();

		assertEquals(checkoutPage.getSummaryProductsString(), frameworkProperties.getProperty("number_of_items"));
	}

	@AfterAll
	public static void closeObjects() {
		driver.close();
	}
}
