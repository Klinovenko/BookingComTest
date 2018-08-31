package com.gmail.vklinovenko;

import com.gmail.vklinovenko.pages.BookingHomePage;
import com.gmail.vklinovenko.pages.BookingSearchResults;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BookingComTest {

    private final String BASE_URL = "https://www.booking.com/index.ru.html";

    private FirefoxOptions options = new FirefoxOptions()
            .setProfile(new FirefoxProfile())
            .addPreference("browser.startup.homepage", "about:blank");

    private static WebDriver driver;

    private static BookingHomePage homePage;
    private static BookingSearchResults searchResults;

    @BeforeClass
    public void beforeClass() {

        // Драйвер находится по пути, прописанном в $PATH, если нет - раскомментировать нужную строку и прописать путь:
        //System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");                              // Linux
        // System.setProperty("webdriver.gecko.driver", "C://Program Files/geckodriver.exe");                 // Windows

        driver = new FirefoxDriver(options);
        homePage = new BookingHomePage(driver);
        searchResults = new BookingSearchResults(driver);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @BeforeMethod
    public void beforeMethod() {
        driver.get(BASE_URL);
    }

    @AfterClass
    public void afterClass() {
        driver.quit();
    }



    @Test(testName = "1. Business trip")

    /*
        1.  Enter "ПВТ Минск" as a destination, 08.10.2018 as a checkin date and 19.10.2018 as a checkout date for 1 adult;
        2.  Check "I'm travelling for work" and uncheck "Your results will be shown on the map." checkboxes;
        3.  Filter only available bookings with 8+ rating, including breakfast and free Wi-Fi;
        4.  Select hotel which is the closest to the destination;
        5.  Check standard single room, press «I'll reserve» button;
        6.  Check final price.
     */

    public void testBusinessTrip() {
        // TODO TEST1 NOT COMPLETE!

        final String DESTINATION   = "ПВТ Минск";
        final String CHECKIN_DATE  = "08.10.2018";
        final String CHECKOUT_DATE = "19.10.2018";
        final byte   ADULTS_NUM    = 1;

        homePage.setDestination(DESTINATION);
        homePage.setCheckInDate(CHECKIN_DATE);
        homePage.setCheckOutDate(CHECKOUT_DATE);
        homePage.clickGuestsToggle();
        homePage.setGuests(ADULTS_NUM);
        homePage.setWorkTravel();
        homePage.noMapResults();
        homePage.clickSearchboxButton();

        Assert.assertTrue(true, "Заглушка");
    }



    @Test(testName = "2. Family vacations")

    /*
        1.  Enter Kotor as a destination, 11.05.2019 as a checkin date and 25.05.2019 as a checkout date for 2 adults
            and 2 children (age of 10 and 14) in one room;
        2.
     */

    public void testFamilyVacations() {
        // TODO TEST2 NOT COMPLETE!

        final String DESTINATION   = "Kotor";
        final String CHECKIN_DATE  = "11.05.2019";
        final String CHECKOUT_DATE = "25.05.2019";
        final byte   ADULTS_NUM    = 2;
        final byte   CHILDREN_NUM  = 2;
        final byte[] CHILDREN_AGE  = {10, 14};

        homePage.setDestination(DESTINATION);
        homePage.setCheckInDate(CHECKIN_DATE);
        homePage.setCheckOutDate(CHECKOUT_DATE);
        homePage.clickGuestsToggle();
        homePage.setGuests(ADULTS_NUM, CHILDREN_NUM, CHILDREN_AGE);
        homePage.clickSearchboxButton();

        //searchResults.checkAvailableOnlyCheckbox();   // It doesn't work yet
        //searchResults.checkReview8plusCheckbox();

        Assert.assertTrue(true, "Заглушка");
    }


    @Test(testName = "7. Negative test")

    /*
        1.  Enter space as a destination, get NO_LOCATION error message;
        2.  Enter unexistent destination, get UNKNOWN_LOCATION error message;
        3.  Enter booking length more than 30 nights, get LONG_BOOKING error message;
        // TODO EXTEND TEST7, IT MUST BE MORE NEGATIVE!
    */

    public void testBadLocation() {
        final String NO_LOCATION                 = " ";
        final String UNKNOWN_LOCATION            = "ALongTimeAgoInAGalaxyFarFarAway";
        final String KNOWN_LOCATION              = "Minsk";
        final String CHECKIN_DATE                = "11.05.2019";
        final String CHECKOUT_DATE               = "12.06.2019";
        final String NO_LOCATION_ERROR_TEXT      = "Чтобы начать поиск, введите направление.";
        final String UNKNOWN_LOCATION_ERROR_TEXT = "К сожалению, нам не известно это направление.";
        final String LONG_BOOKING_ERROR_TEXT     = "К сожалению, бронирование более чем на 30 ночей невозможно.";

        //  No location
        homePage.setDestination(NO_LOCATION);
        homePage.clickSearchboxButton();
        Assert.assertEquals(homePage.getSearchboxErrorText(), NO_LOCATION_ERROR_TEXT);

        //  Unknown location:
        homePage.setDestination(UNKNOWN_LOCATION);
        homePage.clickSearchboxButton();
        Assert.assertEquals(homePage.getSearchboxErrorText(), UNKNOWN_LOCATION_ERROR_TEXT);

        //  Booking more than 30 nights
        homePage.setDestination(KNOWN_LOCATION);
        homePage.setCheckInDate(CHECKIN_DATE);
        homePage.setCheckOutDate(CHECKOUT_DATE);
        homePage.clickSearchboxButton();
        Assert.assertEquals(homePage.getDateboxErrorText(), LONG_BOOKING_ERROR_TEXT);
    }
}
