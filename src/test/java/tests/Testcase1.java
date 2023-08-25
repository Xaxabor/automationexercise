package tests;

import com.aventstack.extentreports.Status;
import helper.Utilities;
import modules.ExtentTestManager;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;
import pages.*;
import java.util.Arrays;
import java.util.List;

public class Testcase1 extends BaseTest{
    HomePage homePage = new HomePage();
    LoginSignUpPage loginSignUpPage = new LoginSignUpPage();
    PaymentPage paymentPage = new PaymentPage();
    CartPage cartPage = new CartPage();
    Assertion assertion = new Assertion();
    SoftAssert softAssert = new SoftAssert();

    String userName = Utilities.generateUserName();
    String emailAddress = Utilities.generateEmail();
    String commentForTextArea = Utilities.generateComment();
    String cvcNumber = Utilities.generateZip();
    String cardNumber = Utilities.generateMobile();
    String currentMonth = Utilities.getCurrentMonth();
    String cardExpirationYear = String.valueOf(Integer.parseInt(Utilities.getCurrentYear()) + 1);
    String yearOfBirth = String.valueOf(Integer.parseInt(Utilities.getCurrentYear()) - 20);
    String currentDay = Utilities.getCurrentDay();
    String firstName=  Utilities.generateFirstName();
    String lastName=  Utilities.generateLastName();
    String companyName = userName+ " Company";
    String address = Utilities.generateAddress();
    String address2 = Utilities.generateAddress2();
    String state = Utilities.generateState();
    String city = Utilities.generateCity();
    String zip = Utilities.generateZip();
    String mobileNumber = Utilities.generateMobile();
    String title = "Mr.";
    String country = "India";
    String itemName= "Fancy Green Top";
    String expectedProductLink = HomePage.baseUrl+"product_details/8";

    List<String> textFieldList = Arrays.asList(LoginSignUpPage.passwordFieldLabel, LoginSignUpPage.firstNameFieldLabel, LoginSignUpPage.lastNameFieldLabel,
            LoginSignUpPage.companyFieldLabel, LoginSignUpPage.addressFieldLabel, LoginSignUpPage.address2FieldLabel, LoginSignUpPage.stateFieldLabel,
            LoginSignUpPage.cityFieldLabel, LoginSignUpPage.zipCodeFieldLabel, LoginSignUpPage.mobileNumberFieldLabel);
    List<String> fieldValue = Arrays.asList(Utilities.generatePassword(),  firstName,  lastName, companyName, address, address2, state, city, zip, mobileNumber);
    List<String> expectedAddressValues = Arrays.asList(title+" "+firstName+" "+lastName, companyName, address, address2, city+" "+state+" "+zip,country, mobileNumber);
    List<String> expectedCartTableValueList  = Arrays.asList("Women > Tops", "Rs. 700", "1", "Rs. 700");

    @Test
    void Test001_002_validateUserLandedOnProperBaseUrl() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: validate user navigated to url 'http://automationexercise.com'");
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl), true, "Base Url '"+
                HomePage.baseUrl +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        ExtentTestManager.getTest().log(Status.INFO, "End: validate user navigated to url 'http://automationexercise.com'");
    }

    @Test
    void Test003_validateHomePageIsLoaded() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify that home page is visible successfully");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.homeTabLabel, "style"), "color: orange;",  HomePage.homeTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify that home page is visible successfully");
    }

    @Test
    void Test004_validateAddProductsToCart() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify Add products to cart");
        homePage.clickOnAddToCartButtonForSpecificItem(itemName);
        assertion.assertEquals(homePage.getTextFromModal(HomePage.cartModalDivID, HomePage.h4Tag), "Added!",  "'Added!' text is not displayed");
        assertion.assertEquals(homePage.getTextFromModal(HomePage.cartModalDivID, HomePage.pTag), "Your product has been added to cart.",  "'Your product has been added to cart.' text is not displayed");
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify Add products to cart");
    }

    @Test
    void Test005_006_validateViewCart() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify click on View Cart");
        homePage.clickOnElementWithTagName(HomePage.uTag, HomePage.viewCartButtonLabel,true);
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify click on View Cart");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify  cart page is displayed");
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+CartPage.viewCartUrlextention), true, "Cart Url '"+
                HomePage.baseUrl+CartPage.viewCartUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.cartTabLabel, "style"), "color: orange;",  HomePage.cartTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify  cart page is displayed");
    }

    @Test
    void Test007_validateProceedTOCheckOutWithoutLogin() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify click on Proceed To Checkout");
        homePage.clickOnButton(CartPage.proceedToCheckoutButtonLabel, false);
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify click on Proceed To Checkout");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify checkout modal is displayed");
        assertion.assertEquals(homePage.getTextFromModal(CartPage.checOutModalDivID, HomePage.h4Tag), "Checkout",  "'Checkout' text is not displayed");
        assertion.assertEquals(homePage.getTextFromModal(CartPage.checOutModalDivID, HomePage.pTag), "Register / Login account to proceed on checkout.",  "'Register / Login account to proceed on checkout.' is not displayed");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify checkout modal is displayed");
    }

    @Test
    void Test008_validateClickOnRegisterLogin() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify click on Register / Login");
        homePage.clickOnElementWithTagName(HomePage.uTag, CartPage.registerLoginButtonLabel,true);
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify click on Register / Login");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify Register / Login page is displayed");
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+LoginSignUpPage.loginUrlextention), true,
                HomePage.baseUrl+LoginSignUpPage.loginUrlextention +"' url is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.signUpLoginTabLabel, "style"), "color: orange;",  HomePage.signUpLoginTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify Register / Login page is displayed");
    }

    @Test
    void Test009_validateSignUpAndCreateAccount() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Verify New User Signup!");
        loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.newUserSignupText, LoginSignUpPage.nameFieldPlaceholderText, userName);
        loginSignUpPage.setTextInSpecificFieldWithPalceHolder(LoginSignUpPage.newUserSignupText, LoginSignUpPage.emailAddressFieldPlaceholderText, emailAddress);
        homePage.clickOnButton(LoginSignUpPage.signUpButtonLabel, false);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl + LoginSignUpPage.signupUrlextention), true,
                HomePage.baseUrl + LoginSignUpPage.signupUrlextention + "' url is not loaded. Currently loaded url is '" + homePage.getCurrentURL() + "'");
        ExtentTestManager.getTest().log(Status.INFO, "End: Verify New User Signup!");

        ExtentTestManager.getTest().log(Status.INFO, "Start: ENTER ACCOUNT & ADDRESS INFORMATION");
        loginSignUpPage.selectRadioButtonOption(title);
        loginSignUpPage.selectValueFromDropdown(LoginSignUpPage.dateOfBirthDropdownLabel, LoginSignUpPage.dayDropdownLabel, currentDay);
        loginSignUpPage.selectValueFromDropdown(LoginSignUpPage.dateOfBirthDropdownLabel, LoginSignUpPage.yearDropdownLabel, yearOfBirth);
        loginSignUpPage.selectValueFromDropdown(LoginSignUpPage.dateOfBirthDropdownLabel, LoginSignUpPage.monthDropdownLabel, currentMonth);
        for (int i=0; i< textFieldList.size(); i++) {
            homePage.setTextInSpecificField(textFieldList.get(i), fieldValue.get(i));
        }
        homePage.clickOnButton(LoginSignUpPage.createAccountLabel, false);
        ExtentTestManager.getTest().log(Status.INFO, "End: ENTER ACCOUNT & ADDRESS INFORMATION");
    }

    @Test(priority=1)
    void Test010_validateAccountIsCreated(){
        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate ACCOUNT is Created");
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl + LoginSignUpPage.accountCreatedUrlextention), true,
                HomePage.baseUrl + LoginSignUpPage.accountCreatedUrlextention + "' url is not loaded. Currently loaded url is '" + homePage.getCurrentURL() + "'");
        assertion.assertEquals(homePage.getTextFromModal(LoginSignUpPage.formSectionID, HomePage.bTag), "ACCOUNT CREATED!",  "'ACCOUNT CREATED!' text is not displayed");
        assertion.assertEquals(homePage.getTextFromModal(LoginSignUpPage.formSectionID, HomePage.pTag), "Congratulations! Your new account has been successfully created!",  "'Congratulations! Your new account has been successfully created!' is not displayed ");
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate ACCOUNT is Created");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate click on Continue button");
        homePage.clickOnButton(LoginSignUpPage.continueLabel, false);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl), true, HomePage.baseUrl + "' url is not loaded. Currently loaded url is '" + homePage.getCurrentURL() + "'");
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate click on Continue button");
    }

    @Test(priority=1)
    void Test011_validateLoggedInUsername() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate Logged in as username' at top");
        assertion.assertEquals(homePage.getLoggedInUserName(), userName,  "Logged user name is not "+userName);
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate Logged in as username' at top");
    }

    @Test(priority=1)
    void Test012_validateClickOnCartButton() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Click 'Cart' button");
        homePage.clickOnButton(HomePage.cartTabLabel, false);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+CartPage.viewCartUrlextention), true,
                HomePage.baseUrl+CartPage.viewCartUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.cartTabLabel, "style"), "color: orange;",  HomePage.cartTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "End: Click 'Cart' button");
    }

    @Test(priority=1)
    void Test013_validateClickOnProceedToCheckButton() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Click 'Proceed To Check' button");
        homePage.clickOnButton(CartPage.proceedToCheckoutButtonLabel, false);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+CartPage.checkoutUrlextention), true,
                HomePage.baseUrl+CartPage.checkoutUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        assertion.assertEquals(homePage.getTabAttribute(HomePage.cartTabLabel, "style"), "color: orange;",  HomePage.cartTabLabel
                + "' page is not loaded");
        ExtentTestManager.getTest().log(Status.INFO, "End: Click 'Proceed To Check' button");
    }

    @Test(priority=1)
    void Test014_validateAddressDetailsAndReviewOrder() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate DELIVERY Address Details");
        List <String> deliveryAddressValues = cartPage.getAllTextFromArea(CartPage.addressDeliveryDivID, HomePage.liTag);
        for (int i=1; i<deliveryAddressValues.size() ;i++) {
            assertion.assertEquals(deliveryAddressValues.get(i), expectedAddressValues.get(i-1),   deliveryAddressValues.get(i)+ " is shown instead of "+ expectedAddressValues.get(i-1));
        }
        ExtentTestManager.getTest().log(Status.INFO, "End:  Validate DELIVERY Address Details");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate BILLING Address Details");
        List <String> billingAddressValues = cartPage.getAllTextFromArea(CartPage.addressInvoiceDivID, HomePage.liTag);
        for (int i=1; i<billingAddressValues.size() ;i++) {
            assertion.assertEquals(billingAddressValues.get(i), expectedAddressValues.get(i-1),   billingAddressValues.get(i)+ " is shown instead of "+ expectedAddressValues.get(i-1));
        }
        ExtentTestManager.getTest().log(Status.INFO, "End:  Validate BILLING Address Details");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate Review Your Order");
        String actualValue;
        List<String> columnList = Arrays.asList(CartPage.descriptionColumnLabel, CartPage.priceColumnLabel, CartPage.quantityColumnLabel, CartPage.totalColumnLabel);
        actualValue = cartPage.getSpecificTableValue(CartPage.cartInfoDivID, CartPage.itemColumnLabel, itemName, "src");
        softAssert.assertEquals(actualValue, expectedProductLink, expectedProductLink+ " is not shown. Instead shown value is "+ actualValue);
        for (int i = 0; i < columnList.size(); i++) {
            actualValue = cartPage.getSpecificTableValue(CartPage.cartInfoDivID, columnList.get(i), itemName, "");
            softAssert.assertEquals(actualValue, expectedCartTableValueList.get(i), expectedCartTableValueList.get(i)+ " is not shown. Instead shown value is "+ actualValue);
        }
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate Review Your Order");
    }

    @Test(priority=1)
    void Test015_validateEnterDescriptionAndPlaceOrder() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Enter description in comment text area and click 'Place Order'");
        homePage.setTextInSpecificField(CartPage.ifYouWouldLikeToAddACommentAboutYourOrderPleaseWriteItInTheFieldBelowTextAreaLabel, commentForTextArea);
        homePage.clickOnButton(CartPage.placeOrderButtonLabel, false);
        assertion.assertEquals(homePage.isProperUrlLoaded(HomePage.baseUrl+PaymentPage.paymentUrlextention), true,
                HomePage.baseUrl+PaymentPage.paymentUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        ExtentTestManager.getTest().log(Status.INFO, "End: Enter description in comment text area and click 'Place Order'");
    }

    @Test(priority=1)
    void Test016_017_018_validatePayConfirmAndSuccessOrder() {
        ExtentTestManager.getTest().log(Status.INFO, "Start: Enter payment details: Name on Card, Card Number, CVC, Expiration date");
        homePage.setTextInSpecificField(PaymentPage.nameOnCardFieldLabel, userName);
        homePage.setTextInSpecificField(PaymentPage.cardNumberFieldLabel, cardNumber);
        homePage.setTextInSpecificField(PaymentPage.cvcFieldLabel, cvcNumber);
        homePage.setTextInSpecificField(PaymentPage.expirationLabel, currentMonth);
        paymentPage.setTextInSpecificFieldInPaymentPage(PaymentPage.expirationLabel, cardExpirationYear);
        ExtentTestManager.getTest().log(Status.INFO, "End: Enter payment details: Name on Card, Card Number, CVC, Expiration date");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Click 'Pay and Confirm Order' button");
        homePage.clickOnButton(PaymentPage.payAndConfirmOrderButtonLabel, false);
        ExtentTestManager.getTest().log(Status.INFO, "End: Click 'Pay and Confirm Order' button");

        ExtentTestManager.getTest().log(Status.INFO, "Start: Validate the success message 'Your order has been placed successfully!");
        assertion.assertEquals(homePage.getTextFromModal(PaymentPage.formSectionID, HomePage.pTag), "Congratulations! Your order has been confirmed!",
                "'Congratulations! Your order has been confirmed!' text is not displayed");
        assertion.assertEquals(homePage.getCurrentURL().contains(HomePage.baseUrl+PaymentPage.paymentDoneUrlextention), true,
                HomePage.baseUrl+PaymentPage.paymentDoneUrlextention +"' is not loaded. Currently loaded url is '"+homePage.getCurrentURL() +"'");
        ExtentTestManager.getTest().log(Status.INFO, "End: Validate the success message 'Your order has been placed successfully!");
    }
}
