package com.nopcommerce.share;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import commons.BaseTest;
import commons.PageGeneratorManager;
import pageObjects.user.AddressesPageObject;
import pageObjects.user.ChangePasswordPageObject;
import pageObjects.user.CustomerPageObject;
import pageObjects.user.HomePageObject;
import pageObjects.user.MyProductReviewsPageObject;
import pageObjects.user.UserLoginPageObject;

public class NopCommerce_03_MyAccount extends BaseTest {
	private WebDriver driver;

	private HomePageObject homePage;
	private UserLoginPageObject loginPage;
	private CustomerPageObject customerPage;
	private AddressesPageObject addressesPage;
	private ChangePasswordPageObject changePasswordPage;
	private MyProductReviewsPageObject myProductReviewsPage;

	@Parameters("browser")
	@BeforeClass
	public void beforeClass(String browserName) {
		driver = getBrowserName(browserName);
		homePage = PageGeneratorManager.getHomePage(driver);
		loginPage = homePage.clickToLoginLink();

		loginPage.enterToEmailTextbox(Common_Register.emailAddress);
		loginPage.enterToPasswordTextbox(Common_Register.password);
		
		homePage = loginPage.clickToUserLoginButton();
		customerPage = homePage.clickToMyaccountLink();
	}

	@Test
	public void MyAccount_01_Update_Customer_Info() {
		customerPage.updateGenderValue("Female");
		customerPage.updateFirstNameValue("Automation");
		customerPage.updateLastNameValue("FC");
		customerPage.updateEmailValue("automationfc@gmail.com");
		customerPage.updateDateOfBirthValue("1", "DateOfBirthDay");
		customerPage.updateDateOfBirthValue("January", "DateOfBirthMonth");
		customerPage.updateDateOfBirthValue("1999", "DateOfBirthYear");
		
		customerPage.updateCompanyNameValue("Automation FC");
		customerPage.clickToSaveButton();
		

		Assert.assertEquals(customerPage.getFirstNameAttributeValue(), "Automation");
		Assert.assertEquals(customerPage.getLastNameAttributeValue(), "FC");
		Assert.assertEquals(customerPage.getEmailAttributeValue(), "automationfc@gmail.com");
		Assert.assertEquals(customerPage.getCompanyNameAttributeValue(), "Automation FC");

	}
	
	@Test
	public void MyAccount_02_Add_Address_To_Customer_Info() {
		
		customerPage.openDynamicSidebarPage(driver, "Addresses");
		addressesPage = PageGeneratorManager.getAddressesPage(driver);
		addressesPage.clickToAddNewAddress();
		addressesPage.inputAddressFirstName("Automation");
		addressesPage.inputAddressLastName("FC");
		addressesPage.inputAddressEmai("automationfc@gmail.com");
		addressesPage.inputAddressCompany("Automation FC");
		addressesPage.inputAddressCountry("Viet Nam");
		addressesPage.inputAddressState("Other");
		addressesPage.inputAddressCity("Da Nang");
		addressesPage.inputAddress1("123/4 Le Lai");
		addressesPage.inputAddress2("234 Hong Bang");
		addressesPage.inputAddressZipCode("50000");
		addressesPage.inputAddressPhoneNumber("123456789");
		addressesPage.inputAddressFaxNumber("987654321");
		addressesPage.clickToSaveButton();
	
		Assert.assertEquals(addressesPage.getSuccessRegisteredText(), "The new address has been added successfully.");
	
		
	}
	
	@Test
	public void MyAccount_03_Change_Password() {
		addressesPage.openDynamicSidebarPage(driver, "Change password");
		changePasswordPage = PageGeneratorManager.getChangePasswordPage(driver);
		changePasswordPage.inputOldPassword(Common_Register.password);
		changePasswordPage.inputNewPassword(Common_Register.newPassword);
		changePasswordPage.inputConfirmNewPassword(Common_Register.newPassword);
		changePasswordPage.clickChangePasswordButton(Common_Register.newPassword);
		Assert.assertEquals(changePasswordPage.getChangePasswordSuccessText(), "Password was changed");
		changePasswordPage.closeChangePasswordText();
		changePasswordPage.sleepInSecond(2);
		changePasswordPage.clickToLogOutLink();
		
		loginPage = homePage.clickToLoginLink();
		loginPage.loginToUser(Common_Register.emailAddress, Common_Register.password);

		Assert.assertEquals(loginPage.getLoginErrorText(), "Login was unsuccessful. Please correct the errors and try again.\n" + "The credentials provided are incorrect");
		
		loginPage = homePage.clickToLoginLink();
		loginPage.loginToUser(Common_Register.emailAddress, Common_Register.newPassword);
		
		}
	@Test
	public void MyAccount_04_Add_Review_Product() {
		homePage.clickToProductName("Build your own computer");
		homePage.clickToAddYourReview();
		homePage.inputReviewTitle("First review");
		homePage.inputReviewText("Good product for gaming");
		homePage.clickSubmitReviewButton();
		
		customerPage = homePage.clickToMyaccountLink();
		customerPage.openDynamicSidebarPage(driver, "My product reviews");
		myProductReviewsPage = PageGeneratorManager.getMyProductReviewsPage(driver);
		Assert.assertTrue(myProductReviewsPage.isProductReviewDisplayed("Build your own computer"));
		
	}
	
	@AfterClass
	public void afterClass() {
		closeBrowser();
	}

}
