package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class TestUtil {
    public WebDriver driver;
    public String applicationUrl, browser;
    public int implicitWait;


    @BeforeMethod
    public void setUp(){

        readConfig( "src/test/resources/config.properties");
        setupBrowserDriver(browser);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        loadTestUrl(applicationUrl);
        driver.manage().deleteAllCookies();
        driver.manage().window().fullscreen();
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }

    private void setupBrowserDriver(String browser){
        switch (browser){
            case "chrome":
                driver = setupChromeDriver();
                break;
            case "firefox":
                driver = setupFireFoxDriver();
                break;
        }
    }

    private WebDriver setupChromeDriver(){
        WebDriverManager.chromedriver().setup(); //Automatically downloads and prepare webdriver for the version of the browser
        return new ChromeDriver(); //uses the download driver version
    }

    private WebDriver setupFireFoxDriver(){
        WebDriverManager.firefoxdriver().setup(); //Automatically downloads and prepare webdriver for the version of the browser
        return new FirefoxDriver();
    }

    private void readConfig(String filePath){
        try {
            FileInputStream configFile = new FileInputStream(filePath);
            Properties config = new Properties();
            config.load(configFile);
            applicationUrl = config.getProperty("url");
            browser = config.getProperty("browser");
            implicitWait = Integer.parseInt(config.getProperty("implicitWait"));
        }catch (IOException e){
            System.out.println(e);
        }

    }

    private void loadTestUrl(String url){
        driver.get(url);
    }
}
