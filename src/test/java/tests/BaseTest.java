package tests;

import modules.ExtentManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.HomePage;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class BaseTest{

    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final int defaultImpliciteWait = 15;
    private static final int defaultWait = 60;

    @BeforeSuite(alwaysRun = true)
    public void browserSetUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--remote-allow-origins=*");
        chromeOptions.setExperimentalOption("excludeSwitches", Collections.singletonList("enable-automation"));
        chromeOptions.addArguments("--start-maximized");
        chromeOptions.addArguments("disable-notifications");
        chromeOptions.addArguments("test-type");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("autofill.profile_enabled", false);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        chromeOptions.setExperimentalOption("prefs", prefs);
        chromeOptions.addExtensions(new File(System.getProperty("user.dir")+"\\src\\main\\resources\\Extentions\\AdBlock.crx"));
        driver = new ChromeDriver(chromeOptions);
        driver.get(HomePage.baseUrl);
        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(defaultImpliciteWait));
        closeAllWindowExceptCurrentOne();
        wait =  new WebDriverWait(driver,Duration.ofSeconds(defaultWait));
    }

    public void goToUrl(String pageUrl) {
        driver.get(pageUrl);
    }

    public String getCurrentURL() {
        return driver.getCurrentUrl();
    }

    public boolean isProperUrlLoaded(String expectedUrl){
        return expectedUrl.contains(getCurrentURL());
    }

    public WebElement getElementFromLocator(String elementLocator){
        return  driver.findElement(By.xpath(elementLocator));
    }

    public List <WebElement> getAllElementsFromLocator(String elementLocator){
        return driver.findElements(By.xpath(elementLocator));
    }

    public static void closeAllWindowExceptCurrentOne(){
        String originalHandle = driver.getWindowHandle();
        //Do something to open new tabs
        for(String handle : driver.getWindowHandles()) {
            if (!handle.equals(originalHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(originalHandle);
    }

    public void clickOnElement(String elementLocator, boolean withJSExecutor){
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        if(withJSExecutor){
            JavascriptExecutor js = (JavascriptExecutor)driver;
            js.executeScript("arguments[0].click();", element);
        }
        else{
            element.click();
        }
    }

    public void moveToSpecificElement(String elementLocator){
        Actions actions = new Actions(driver);
        WebElement element = driver.findElement(By.xpath(elementLocator));
        actions.moveToElement(element).perform();
    }

    public void moveToSpecificElement(WebElement element){
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    public String getTextFromElement(String elementLocator){
        WebElement element = driver.findElement(By.xpath(elementLocator));
        return element.getText();
    }

    public List<String> getTextFromAllElements(String elementsLocator){
        List<WebElement> webElements = driver.findElements(By.xpath(elementsLocator));
        List<String> values = new ArrayList<>(Collections.emptyList());
        for (WebElement element: webElements) {
            values.add(StringUtils.normalizeSpace(element.getAttribute("innerText")));
        }
        return values;
    }

    public String getAttributeFromElement(String elementLocator, String attributeName){
        WebElement element = driver.findElement(By.xpath(elementLocator));
        return element.getAttribute(attributeName);
    }

    public void setTextInField(String fieldLocator, String textValue){
        WebElement element = driver.findElement(By.xpath(fieldLocator));
        moveToSpecificElement(element);
        element.click();
        element.sendKeys(textValue);
    }

    public boolean isElementDisplayed(String elementLocator){
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        return element.isDisplayed();
    }

    public boolean isElementSelected(String elementLocator){
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        return element.isSelected();
    }

    public boolean isElementEnabled(String elementLocator){
        WebElement element = driver.findElement(By.xpath(elementLocator));
        moveToSpecificElement(element);
        return element.isEnabled();
    }

    public void waitForElementVisibility(String locator){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public void waitForElementsVisibility(String locator){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator)));
    }

    public void waitForElementPresence(String locator){
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
    }

    public void waitForElementsPresence(String locator){
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locator)));
    }

    public static void captureScreenshot() {
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        // Call method to capture screenshot
        File src = screenshot.getScreenshotAs(OutputType.FILE);
        try {
            Date d = new Date();
            Timestamp ts=new Timestamp(d.getTime());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss");
            String timeStamp = String.valueOf(simpleDateFormat.format(ts)).replace(" ", "_").replace("-", "_");
            String screenshotName = timeStamp + ".jpg";
            FileUtils.copyFile(src, new File(ExtentManager.reportFilepath+ ExtentManager.fileSeperator +"Screenshots" +ExtentManager.fileSeperator+ screenshotName));
            System.out.println("Successfully captured a screenshot");
        } catch (IOException e) {
            System.out.println("Exception while taking screenshot " + e.getMessage());
        }
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown(){
        driver.quit();
    }

}

