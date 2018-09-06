package com.gmail.vklinovenko;

import com.gmail.vklinovenko.pages.*;
import org.openqa.selenium.NoSuchElementException;
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

    private static BookingCitiesPage citiesPage;
    private static BookingCityPage cityPage;
    private static BookingDealsPage dealsPage;
    private static BookingHomePage homePage;
    private static BookingHotelPage hotelPage;
    private static BookingSearchResults searchResults;

    @BeforeClass
    public void beforeClass() {

        // Драйвер находится по пути, прописанном в $PATH, если нет - раскомментировать нужную строку и прописать путь:
        // System.setProperty("webdriver.gecko.driver", "/usr/bin/geckodriver");                              // Linux
        // System.setProperty("webdriver.gecko.driver", "C://Program Files/geckodriver.exe");                 // Windows

        driver = new FirefoxDriver(options);
        citiesPage = new BookingCitiesPage(driver);
        cityPage = new BookingCityPage(driver);
        dealsPage = new BookingDealsPage(driver);
        homePage = new BookingHomePage(driver);
        hotelPage = new BookingHotelPage(driver);
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
        driver.manage().deleteAllCookies();
        driver.quit();
    }



    @Test(testName = "1. Business trip", enabled = true)
    public void testBusinessTrip() {
        // TODO TEST1 NOT COMPLETE!

     /*
        1.  Enter "ПВТ Минск" as a destination, 08.10.2018 as a check in date and 19.10.2018 as a check out date
            for 1 adult;
        2.  Check "Я путешествую по работе" and uncheck "Результаты поиска будут показаны на карте" checkboxes;
        3.  Filter only available bookings with 8+ rating, including breakfast, airport transfer and free Wi-Fi;
        4.  Select hotel which is the closest to the destination;
        5.  Check standard single room, press «I'll reserve» button;
        6.  Check final price.
     */

        final String DESTINATION   = "ПВТ Минск";
        final String CHECKIN_DATE  = "08.10.2018";
        final String CHECKOUT_DATE = "19.10.2018";
        final byte   ADULTS_NUM    = 1;
        final byte MIN_ROOMS_FOUND  = 5;

        homePage.setDestination(DESTINATION);
        homePage.setCheckInDate(CHECKIN_DATE);
        homePage.setCheckOutDate(CHECKOUT_DATE);
        homePage.setGuests(ADULTS_NUM);
        homePage.setWorkTravel();
        homePage.noMapResults();
        homePage.clickSearchboxButton();

        searchResults.filterAvailableOnly();
        searchResults.filterReview8plus();
        searchResults.filterBreakfast();
        searchResults.filterFreeWiFi();
        searchResults.filterAirportTransfer();
        searchResults.waitForResults();
        //searchResults.sortByDistance();
        Assert.assertTrue((searchResults.getHotelsNumber() > MIN_ROOMS_FOUND),
                "The number of found rooms is less than" + MIN_ROOMS_FOUND);
        // TODO Choose first search result
        // TODO "I'll reserve" button
        // TODO Check final price
        // TODO Close hotel page
    }



    @Test(testName = "2. Family vacations", enabled = true)
    public void testFamilyVacations() {
        // TODO TEST2 NOT COMPLETE!

     /*
        1.  Enter Kotor as a destination, 11.05.2019 as a check in date and 25.05.2019 as a check out date
            for 2 adults and 2 children (age of 10 and 14) in one room;
        2. Filter only available bookings with 8+ rating at the beachfront;
        3. Sort search results by review score and number of reviews;
        4. Select the first result;
        5. Check first room type, press «I'll reserve» button;
        6. Check final price.
     */

        final String DESTINATION   = "Kotor";
        final String CHECKIN_DATE  = "11.05.2019";
        final String CHECKOUT_DATE = "25.05.2019";
        final byte   ADULTS_NUM    = 2;
        final byte   CHILDREN_NUM  = 2;
        final byte[] CHILDREN_AGE  = {10, 14};
        final byte MIN_ROOMS_FOUND  = 6;

        homePage.setDestination(DESTINATION);
        homePage.setCheckInDate(CHECKIN_DATE);
        homePage.setCheckOutDate(CHECKOUT_DATE);
        homePage.setGuests(ADULTS_NUM, CHILDREN_NUM, CHILDREN_AGE);
        homePage.unsetWorkTravel();
        homePage.clickSearchboxButton();

        searchResults.filterAvailableOnly();
        searchResults.filterReview8plus();
        searchResults.filterBeachfront();
        searchResults.waitForResults();
        searchResults.sortByBayesianReviewScore();
        Assert.assertTrue((searchResults.getHotelsNumber() > MIN_ROOMS_FOUND),
                "The number of found rooms is less than" + MIN_ROOMS_FOUND);
        // TODO Choose first search result
        // TODO "I'll reserve" button
        // TODO Check final price
        // TODO Close hotel page
    }


    @Test(testName = "3. Hostel", enabled = true)
    public void testHostel() {

     /*
        1. Enter «Berlin» as a destination, 07.12.2018 as a check in and 09.12.2018 as a check out date for 4 adults;
        2. Filter only available bookings in hostel with 7+ rating, free booking cancel and 24/7 front desk;
        3. Sort search results by price;
        4. Check search results: I can find at least 3 hostels.
     */

        final String DESTINATION     = "Berlin";
        final String CHECKIN_DATE    = "07.12.2018";
        final String CHECKOUT_DATE   = "09.12.2018";
        final byte   ADULTS_NUM      = 4;
        final byte   MIN_ROOMS_FOUND = 3;

        homePage.setDestination(DESTINATION);
        homePage.setCheckInDate(CHECKIN_DATE);
        homePage.setCheckOutDate(CHECKOUT_DATE);
        homePage.setGuests(ADULTS_NUM);
        homePage.unsetWorkTravel();
        homePage.clickSearchboxButton();

        searchResults.filterAvailableOnly();
        searchResults.filterHostels();
        searchResults.filterReview7plus();
        searchResults.filterFreeBookingCancel();
        searchResults.filter24hrReception();
        searchResults.waitForResults();
        searchResults.sortByPrice();

        Assert.assertTrue((searchResults.getHotelsNumber() > MIN_ROOMS_FOUND),
                "The number of found rooms is less than" + MIN_ROOMS_FOUND + "!");
    }


    @Test(testName = "4. Patriotic agrotourism", enabled = true)
    public void testPatrioticAgrotourism() {
        // TODO TEST4 NOT COMPLETE!

     /*
        1.  Enter «Браславские озёра» as a destination, 21.09.2018 as a check in date and 23.09.2018 as a check out date
            for 10 adults;
        2. Filter only available entire place bookings near the beach with 8+ rating and sauna;
        3. Sort search results by review score and price;
        4. Select the first result;
        5. Check first room type, press «I'll reserve» button;
        6. Check final price.
     */

        final String DESTINATION   = "Браславские озёра";
        final String CHECKIN_DATE  = "21.09.2018";
        final String CHECKOUT_DATE = "23.09.2018";
        final byte   ADULTS_NUM    = 10;
        final byte MIN_ROOMS_FOUND  = 2;

        homePage.setDestination(DESTINATION);
        homePage.setCheckInDate(CHECKIN_DATE);
        homePage.setCheckOutDate(CHECKOUT_DATE);
        homePage.setGuests(ADULTS_NUM);
        homePage.unsetWorkTravel();
        homePage.noMapResults();
        homePage.clickSearchboxButton();

        searchResults.filterAvailableOnly();
        searchResults.filterReview8plus();
        searchResults.filterBeachfront();
        searchResults.filterSauna();
        searchResults.filterHolidayHomes();
        searchResults.waitForResults();
        searchResults.sortByReviewScoreAndPrice();
        Assert.assertTrue((searchResults.getHotelsNumber() > MIN_ROOMS_FOUND),
                "The number of found rooms is less than" + MIN_ROOMS_FOUND);
        // TODO Choose first search result
        // TODO "I'll reserve" button
        // TODO Check final price
        // TODO Close hotel page
    }


    @Test(testName = "5. Popular Destination", enabled = true)
    public void testPopularDestination() {

     /*
        1. Click «Города»;
        2. Then choose the most popular city;
        3. Click «Лучшее предложение» (the cheapest weekend price);
        4. Choose only available booking with a 9+ rating and budget of 250 BYN per night. They should be located
           in the city centre or in guests' favourite district if possible;
        5. Check search results: I can find at least 3 rooms in the most popular city for a weekend.
     */

     final byte MIN_ROOMS_FOUND  = 3;
     final int  BUDGET_PER_NIGHT = 250;

        homePage.clickCitiesLink();
        citiesPage.clickMostPopularCity();
        cityPage.clickCheapestWeekendPrice();
        searchResults.filterReview9plus();
        searchResults.filterBudget(BUDGET_PER_NIGHT);
        try {
            searchResults.filterCityCentre();
        }
        catch (NoSuchElementException e) {
            searchResults.filterGuestsFavouriteDistrict();
        }
        searchResults.waitForResults();
        Assert.assertTrue((searchResults.getHotelsNumber() > MIN_ROOMS_FOUND),
                "The number of found rooms is less than" + MIN_ROOMS_FOUND);
    }


    @Test(testName = "6. Mid-years deals", enabled = true)
    public void testMidyearDeals() {

     /*
        1. Click «Сэкономьте на проживании от 15% с нашими Сезонными предложениями»
        2. Choose the first city in list;
        3. Find only available hotels with 7+ rating;
        4. Find properties marked as «Сезонное предложение»;
        5. Check search results.
     */

        homePage.clickMidyearDeals();
        dealsPage.chooseFirstSearchResult();
        searchResults.filterAvailableOnly();
        searchResults.filterReview7plus();
        searchResults.filterGreatValueToday();
        searchResults.waitForResults();
        Assert.assertTrue(searchResults.isFirstSeasonDeal(), "No mid-years deals found");
    }


    @Test(testName = "7. Negative test", enabled = true)
    public void testNegative() {

     /*
        1.  Enter space as a destination, get NO_LOCATION error message;
        2.  Enter unexistent destination, get UNKNOWN_LOCATION error message;
        3.  Enter booking length more than 30 nights, get LONG_BOOKING error message;
     */

        final String NO_LOCATION                 = " ";
        final String UNKNOWN_LOCATION            = "ALongTimeAgoInAGalaxyFarFarAway";
        final String KNOWN_LOCATION              = "Minsk";
        final String CHECKIN_DATE                = "11.05.2019";
        final String CHECKOUT_DATE               = "12.06.2019";
        final String NO_LOCATION_ERROR_TEXT      = "Чтобы начать поиск, введите направление.";
        final String UNKNOWN_LOCATION_ERROR_TEXT = "К сожалению, нам не известно это направление.";
        final String LONG_BOOKING_ERROR_TEXT     = "К сожалению, бронирование более чем на 30 ночей невозможно.";

        //  No location
        homePage.setDestinationNoAuto(NO_LOCATION);
        homePage.clickSearchboxButton();
        Assert.assertEquals(homePage.getSearchboxErrorText(), NO_LOCATION_ERROR_TEXT);

        //  Unknown location:
        homePage.setDestinationNoAuto(UNKNOWN_LOCATION);
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
