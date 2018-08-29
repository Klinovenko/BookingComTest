package com.gmail.vklinovenko;

import com.gmail.vklinovenko.pages.BookingHomePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.util.concurrent.TimeUnit;

public class BookingComTest {

    private final String BASE_URL = "https://www.booking.com/";

    private FirefoxOptions options = new FirefoxOptions()
            .setProfile(new FirefoxProfile())
            .addPreference("browser.startup.homepage", "about:blank");

    private static WebDriver driver;

    private static BookingHomePage homePage;

    @BeforeClass
    public void beforeClass() {

        // Драйвер находится по пути, прописанном в $PATH, если нет - раскомментировать нужную строку и прописать путь:
        //System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");                              // Linux
        // System.setProperty("webdriver.gecko.driver", "C://Program Files/geckodriver.exe");                 // Windows

        driver = new FirefoxDriver(options);
        homePage = new BookingHomePage(driver);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }
}
