import com.lab.helpers.SeleniumHelpers;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;


public class VkSeleniumTest {
    private static final String PATH_RESOURCES = "src/main/resources/";
    private static final String PATH_CHROME_DRIVER = PATH_RESOURCES + "driver/chromedriver.exe";
    private static final String URL_VK_COM = "https://www.vk.com";
    private static final String WEBDRIVER_CHROME_DRIVER = "webdriver.chrome.driver";
    private static final String RECORD_TEXT = "I'm studying in the educational center of Netcracker in Togliatti https://vk.com/infocom_tlt";
    private static final String USER_LOGIN = "Your login";
    private static final String USER_PASS = "Your Password";
    private static WebDriver driver;


    @BeforeClass
    public static void setUp() {
        System.setProperty(WEBDRIVER_CHROME_DRIVER, PATH_CHROME_DRIVER);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Before
    public void login() {
        driver.get(URL_VK_COM);
        try {
            WebElement loginButton = driver.findElement(By.id("index_login_button"));

            SeleniumHelpers.sendDynamicElement(driver, By.id("index_email"), USER_LOGIN);
            SeleniumHelpers.sendDynamicElement(driver, By.id("index_pass"), USER_PASS);

            loginButton.click();
        } catch (NoSuchElementException ignore) {
        }
    }

    @Test
    public void tabsClicking() {
        driver.navigate().refresh();

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_pr"));
        SeleniumHelpers.explicitWaiting(driver, By.className("profile_online_lv"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My page");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_nwsf"));
        SeleniumHelpers.explicitWaiting(driver, By.id("feed_filters"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My news");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_msg"));
        SeleniumHelpers.explicitWaiting(driver, By.id("ui_rmenu_fav"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My messages");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_fr"));
        SeleniumHelpers.explicitWaiting(driver, By.id("ui_rmenu_requests"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My friends");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_gr"));
        SeleniumHelpers.explicitWaiting(driver, By.id("ui_rmenu_events"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My groups");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_ph"));
        SeleniumHelpers.explicitWaiting(driver, By.id("photos_add_album_btn"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My photographs");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_aud"));
        SeleniumHelpers.explicitWaiting(driver, By.id("audio_search"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My music");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_vid"));
        SeleniumHelpers.explicitWaiting(driver, By.id("video_search_input"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My videos");

        SeleniumHelpers.clickDynamicElement(driver, By.id("l_ap"));
        SeleniumHelpers.explicitWaiting(driver, By.id("s_search"), 10);
        SeleniumHelpers.takesScreenshot(driver, "My games");
    }


    @Test
    public void newRecord() {
        SeleniumHelpers.clickDynamicElement(driver, By.id("l_pr"));
        SeleniumHelpers.clickDynamicElement(driver, By.id("post_field"));
        SeleniumHelpers.sendDynamicElement(driver, By.id("post_field"), RECORD_TEXT);
        SeleniumHelpers.clickDynamicElement(driver, By.id("send_post"));
        driver.navigate().refresh();
        SeleniumHelpers.clickDynamicElement(driver, By.xpath("//*[text()='" + RECORD_TEXT + "']"));
        WebElement also = driver.findElement(By.xpath("//a[contains(@class, 'ui_actions_menu_more')]"));
        SeleniumHelpers.explicitWaiting(driver, By.xpath("//a[contains(@class, 'ui_actions_menu_more')]"), 10);
        SeleniumHelpers.takesScreenshot(driver, "The record from page");
        Actions moveCursor = new Actions(driver);
        moveCursor.moveToElement(also).build().perform();
        SeleniumHelpers.clickDynamicElement(driver, By.xpath("//a[contains(@id, 'delete')]"));
    }


    @AfterClass
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
