import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Init {
    public WebDriver driver;
    String LOBaseURL = "https://hov-uat-lo01.productmadness.com/admin";

    @BeforeClass
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @Before
    public void setupTest() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
}

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }


}
