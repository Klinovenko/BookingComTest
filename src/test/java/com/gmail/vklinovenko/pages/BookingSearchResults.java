//  https://www.booking.com/searchresults.ru.html
package com.gmail.vklinovenko.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class BookingSearchResults {
    private final WebDriver driver;
    private WebDriverWait wait;

    public BookingSearchResults(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
    }

    @FindBy(id = "ss")
    private WebElement destinationInput;

    @FindBy(xpath = "//div[@class='sr_header ']/*[self::h1 or self::h2]")
    private WebElement searchResultsHeader;

    @FindBy(xpath = "//a[@data-category='distance_from_landmark']")
    private WebElement distanceSort;

    @FindBy(xpath = "//a[@data-category='upsort_public_transport_distance']")
    private WebElement publicTransportSort;

    @FindBy(xpath = "//a[@data-category='bayesian_review_score']")
    private WebElement bayesianReviewScoreSort;

    @FindBy(xpath = "//a[@data-category='review_score_and_price']")
    private WebElement reviewScoreAndPriceSort;

    @FindBy(xpath = "//a[@data-category='price']")
    private WebElement priceSort;

    @FindBy(id = "sortbar_dropdown_button")
    private WebElement sortbarDropdownButton;

//    @FindBy(css = "div.sr_item")
//    private List<WebElement> hotelList;

    @FindBy(css = "div.sr_item:nth-child(1)")
    private WebElement firstSearchResult;

    //@FindBy(xpath = "//div[@id='filter_price']//a[@class=' filterelement        ']")
    @FindBy(xpath = "//div[@id='filter_price']//a[contains(@class, 'filterelement')]")
    private List<WebElement> priceIntervals;

    @FindBy(xpath = "//div[@id='filter_popular_activities']//a[@data-id='popular_activities-10']")
    private WebElement saunaCheckbox;

    @FindBy(xpath = "//div[@id='filter_out_of_stock']//a[@data-id='oos-1']")
    private WebElement availableOnlyCheckbox;

    @FindBy(xpath = "//div[@id='filter_deal']//a[@data-id='any_deal-1']")
    private WebElement greatValueTodayCheckbox;

    @FindBy(xpath = "//div[@id='filter_24hr_reception']//a[@data-id='hr_24-8']")
    private WebElement reception24hrCheckbox;

    @FindBy(xpath = "//div[@id='filter_fc']//a[@data-id='fc-2']")
    private WebElement freeBokingCancelCheckbox;

    @FindBy(xpath = "//div[@id='filter_hoteltype']//a[@data-id='ht_id-220']")
    private WebElement holidayHomesCheckbox;

    @FindBy(xpath = "//div[@id='filter_hoteltype']//a[@data-id='ht_id-203']")
    private WebElement hostelsCheckbox;

    @FindBy(xpath = "//div[@id='filter_ht_beach']//a[@data-id='ht_beach-1']")
    private WebElement beachfrontCheckbox;

    @FindBy(xpath = "//div[@id='filter_mealplan']//a[@data-id='mealplan-1']")
    private WebElement breakfastCheckbox;

    @FindBy(xpath = "//div[@id='filter_review']//a[@data-id='review_score-90']")
    private WebElement review9plusCheckbox;

    @FindBy(xpath = "//div[@id='filter_review']//a[@data-id='review_score-80']")
    private WebElement review8plusCheckbox;

    @FindBy(xpath = "//div[@id='filter_review']//a[@data-id='review_score-70']")
    private WebElement review7plusCheckbox;

    @FindBy(xpath = "//div[@id='filter_facilities']//a[@data-id='hotelfacility-107']")
    private WebElement freeWiFiCheckbox;

    @FindBy(xpath = "//div[@id='filter_facilities']//a[@data-id='hotelfacility-17']")
    private WebElement airportTransferCheckbox;

    @FindBy(xpath = "//div[@id='filter_district']//a[contains(.//span, 'центр города')]")
    private WebElement cityCentreCheckbox;

    @FindBy(xpath = "//div[@id='filter_district']//a[contains(.//span, 'Любимый район гостей')]")
    private WebElement guestsFavouriteDistrictCheckbox;

    @FindBy(css = "div.sr-usp-overlay")
    private WebElement loadingOverlay;

    //  Check "filter by" checkbox
    private void checkBox(@NotNull WebElement checkbox) {
        if (checkbox.getAttribute("aria-checked").equals("false"))
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
    }

    //  Uncheck "filter by" checkbox
    private void uncheckBox(@NotNull WebElement checkbox) {
        if (checkbox.getAttribute("aria-checked").equals("true"))
            wait.until(ExpectedConditions.elementToBeClickable(checkbox)).click();
    }

    //  Choose specified sort option
    private void sortBy(@NotNull WebElement option) {
        if (!option.isDisplayed()
                && option.findElement(By.xpath("./../../../button")).equals(sortbarDropdownButton))
            wait.until(ExpectedConditions.elementToBeClickable(sortbarDropdownButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
        waitForResults();
    }

    //  Choose price interval for budget per night
    public void filterBudget(int budget) {
        for (WebElement interval: priceIntervals) {
            String str = interval.findElement(By.className("filter_label")).getText();
            if (str.contains("+")) {
                wait.until(ExpectedConditions.elementToBeClickable(interval)).click();
                return;
            }
            int low = Integer.parseInt(str.replaceAll("(?<=-).*", "")
                    .replaceAll("\\D", ""));

            int high = Integer.parseInt(str.replaceAll(".*(?=-)", "")
                    .replaceAll("\\D", ""));
            if ((budget >= low) && (budget <= high)) {
                wait.until(ExpectedConditions.elementToBeClickable(interval)).click();
                return;
            }
        }
    }

    //  Filter properties with sauna
    public void filterSauna() {
        checkBox(saunaCheckbox);
    }

    //  Filter only available bookings
    public void filterAvailableOnly() {
        checkBox(availableOnlyCheckbox);
    }

    //  Filter deals and discounts
    public void filterGreatValueToday() {
        checkBox(greatValueTodayCheckbox);
    }

    //  Filter bookings with 24/7 front desk
    public void filter24hrReception() {
        checkBox(reception24hrCheckbox);
    }

    //  Filter properties with free booking cancel
    public void filterFreeBookingCancel() {
        checkBox(freeBokingCancelCheckbox);
    }

    //  Filter holiday homes
    public void filterHolidayHomes() {
        checkBox(holidayHomesCheckbox);
    }

    //  Filter hostels
    public void filterHostels() {
        checkBox(hostelsCheckbox);
    }

    //  Filter properties on the beachfront
    public void filterBeachfront() {
        checkBox(beachfrontCheckbox);
    }

    //  Filter bookings with included breakfast
    public void filterBreakfast() {
        checkBox(breakfastCheckbox);
    }

    //  Filter 9+ rating properties
    public void filterReview9plus() {
        checkBox(review9plusCheckbox);
    }

    //  Filter 8+ rating properties
    public void filterReview8plus() {
        checkBox(review8plusCheckbox);
    }

    //  Filter 7+ rating properties
    public void filterReview7plus() {
        checkBox(review7plusCheckbox);
    }

    //  Filter properties with free WiFi
    public void filterFreeWiFi() {
        checkBox(freeWiFiCheckbox);
    }

    //  Filter properties with transfer from/to airport
    public void filterAirportTransfer() {
        checkBox(airportTransferCheckbox);
    }

    //  Filter properties in the city centre
    public void filterCityCentre() throws NoSuchElementException {
        checkBox(cityCentreCheckbox);
    }

    //  Filter properties in the guests' favourite district
    public void filterGuestsFavouriteDistrict() {
        checkBox(guestsFavouriteDistrictCheckbox);
    }

    //  Wait for filters implementing (loading-overlay disappears)
    public void waitForResults() {
        try {
            while (loadingOverlay.isDisplayed()) continue;
        }
        catch (Exception e) {}
    }

    //  Get the number of found properties
    //  Input: string like "Зона-51: найдено 1 042 варианта - из них 16 суперпредложений", output: 1024 (int)
    public int getHotelsNumber() {
        return Integer.parseInt(searchResultsHeader.getText()
                .replaceFirst(".*(?=(:\\sнайден(о?)\\s(\\d+\\s)+вариант))", "")
                .replaceFirst( "\\sвариант.*", "")
                .replaceAll("\\D", ""));
    }

    //  Sort search results by the distance from place of interest
    public void sortByDistance() {
        sortBy(distanceSort);
        if (!(distanceSort.isDisplayed() && (publicTransportSort.isDisplayed())
                && distanceSort.findElement(By.xpath("./../../../button")).equals(sortbarDropdownButton)))
            wait.until(ExpectedConditions.elementToBeClickable(sortbarDropdownButton)).click();
        else if (publicTransportSort.isDisplayed())
            wait.until(ExpectedConditions.elementToBeClickable(publicTransportSort)).click();
        wait.until(ExpectedConditions.elementToBeClickable(distanceSort)).click();
        waitForResults();
    }

    //  Sort search results by review score and number of reviews
    public void sortByBayesianReviewScore() {
        sortBy(bayesianReviewScoreSort);
    }

    //  Sort search results by review score and price
    public void sortByReviewScoreAndPrice() {
        sortBy(reviewScoreAndPriceSort);
    }

    //  Sort search results by price
    public void sortByPrice() {
        sortBy(priceSort);
    }

    //  Choose first search result
    public void chooseFirstSearchResult() {
        wait.until(ExpectedConditions.elementToBeClickable(
                firstSearchResult.findElement(By.xpath(".//a[contains(@class, 'button')]")))).click();
    }

    public boolean isFirstSeasonDeal() {
        try {
            return (firstSearchResult.findElement(By.xpath(".//p[contains(@class, 'd-deal')]")).getText()
                    .equals("Сезонное предложение"));
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

//    public boolean isSeasonDeal() {
//        return (hotelList.get((byte)1).findElement(By.xpath(".//p[contains(@class, 'd-deal')]")).getText()
//            .equals("Сезонное предложение"));
//    }
//
//    public boolean isSeasonDeal(byte number) {
//        return (hotelList.get(number - 1).findElement(By.xpath(".//p[contains(@class, 'd-deal')]")).getText()
//            .equals("Сезонное предложение"));
//    }
//
//    //  Choose n-th search result
//    public void chooseSearchResult(byte number) {
//        wait.until(ExpectedConditions.elementToBeClickable(hotelList.get(number - 1)
//            .findElement(By.xpath(".//a[contains(@class, 'button')]")))).click();
//    }
//
//    //  Choose first search result on default
//    public void chooseSearchResult() {
//        chooseSearchResult((byte)1);
//    }
}
