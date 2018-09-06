//  https://www.booking.com/index.ru.html
package com.gmail.vklinovenko.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BookingHomePage {
    private final WebDriver driver;

    public BookingHomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(id = "ss")
    private WebElement destinationInput;

    @FindBy(xpath = "//input[@id='ss']/..//li[@data-i='0']")
    private WebElement destinationFirstAutocomplete;

    @FindBy(xpath = "//div[@data-mode='checkin']//button")
    private WebElement checkInButton;

    @FindBy(xpath = "//div[@data-mode='checkout']//button")
    private WebElement checkOutButton;

    @FindBy(xpath = "//div[@data-mode='checkin']//div[@class='sb-date-field__controls']")
    private WebElement checkinDateField;

    @FindBy(xpath = "//div[@data-mode='checkout']//div[@class='sb-date-field__controls']")
    private WebElement checkoutDateField;

    @FindBy(id = "xp__guests__toggle")
    private WebElement guestsToggle;

    @FindBy(id = "no_rooms")
    private WebElement roomsNumSelect;

    @FindBy(id = "group_adults")
    private WebElement adultsNumSelect;

    @FindBy(id = "group_children")
    private WebElement childrenNumSelect;

    @FindBy(xpath = "//select[@name='age']")
    private List<WebElement> childrenAgeSelects;

    @FindBy(css = ".sb-searchbox__button")
    private WebElement searchboxButton;

    @FindBy(id = "sb_travel_purpose_checkbox")
    private WebElement purposeCheckbox;

    @FindBy(id = "sb_results_on_map")
    private WebElement mapResultsCheckbox;

    @FindBy(css = ".sb-searchbox__error")
    private WebElement searchboxError;

    @FindBy(css = ".sb-searchbox__error > span:nth-child(1)")
    private WebElement searchboxInvisibleError;

    @FindBy(css = "div.fe_banner:nth-child(1) > div:nth-child(1)")
    private WebElement dateboxError;

    @FindBy(xpath = "//div[@id='midYearDealsBanner']//a")
    private WebElement midyearDeals;

    //@FindBy(xpath = "//div[@id='footer_links']//a[text()='Города']") //  ???// TODO text!
    @FindBy(xpath = "//div[@id='footer_links']/div[1]//li[3]/a")
    private WebElement citiesLink;

    //  Select an option in dropdown list
    private void selector(@NotNull WebElement select, byte num) {
        select.click();
        select.findElement(By.xpath("./option[@value='" + num + "']")).click();
    }

    //  Check checkbox
    private void checkBox(@NotNull WebElement checkbox) {
        if (!checkbox.isSelected())
            checkbox.findElement(By.xpath("./following-sibling::label")).click();
    }

    //  Uncheck checkbox
    private void uncheckBox(@NotNull WebElement checkbox) {
        if (checkbox.isSelected())
            checkbox.findElement(By.xpath("./following-sibling::label")).click();
    }

    //  Set destination
    public void setDestination(String destination) {
        destinationInput.click();
        destinationInput.clear();
        destinationInput.sendKeys(destination);
        try {
            destinationFirstAutocomplete.click();
        }
        catch (NoSuchElementException e) {}
    }

    //  Set destination without autocomplete
    public void setDestinationNoAuto(String destination) {
        destinationInput.click();
        destinationInput.clear();
        destinationInput.sendKeys(destination);
    }

    //  Set check in date
    public void setCheckInDate(String date) {
        checkInButton.click();
        checkInButton.sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(date.replaceAll("\\D", ""));
    }

    //  Set check out date
    public void setCheckOutDate(String date) {
        checkOutButton.click();
        checkOutButton.sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(date.replaceAll("\\D", ""));
    }

    //  Set number of rooms in dropdown list
    private void setRooms(byte num) {
        selector(roomsNumSelect, num);
    }

    //  Set number of adults in dropdown list
    private void setAdults(byte num) {
        selector(adultsNumSelect, num);
    }

    //  Set number of children and their aged in dropdown list
    private void setChildren(byte num, byte[] age) {
        selector(childrenNumSelect, num);
        if (num == 0) return;                                      // If no children - no need to set age
        byte i = 0;
        for (WebElement ageSelect : childrenAgeSelects) {
            if (i < age.length) selector(ageSelect, age[i]);
            else selector(ageSelect, (byte)0);                     //  If ages are not specified set them as "0"
            i++;
        }
    }

    //  Set guests: All 4 parameters - numbers of rooms, adults and children and children's ages
    public void setGuests(byte rooms, byte adults, byte children, byte[] age)
    {
        guestsToggle.click();
        setRooms(rooms);
        setAdults(adults);
        setChildren(children, age);
        guestsToggle.click();
    }

    //  Set guests: 3 parameters - numbers of adults and children and children's ages
    public void setGuests(byte adults, byte children, byte[] age)
    {
        setGuests((byte)1, adults, children, age);
    }

    //  Set guests: 2 parameters - numbers of rooms and adults
    public void setGuests(byte rooms, byte adults)
    {
        byte[] age = {};
        setGuests(rooms, adults, (byte)0, age);
    }

    //  Set guests: 1 parameter - only adults number
    public void setGuests(byte adults)
    {
        byte[] age = {};
        setGuests((byte)1, adults, (byte)0, age);
    }

    //  Click button "Search"
    public void clickSearchboxButton() {
        searchboxButton.click();
    }

    //  Check "I'm travelling for work" checkbox
    public void setWorkTravel() {
        checkBox(purposeCheckbox);
    }

    //  Unheck "I'm travelling for work" checkbox
    public void unsetWorkTravel() {
        uncheckBox(purposeCheckbox);
    }

    //  Unheck "Show search results on the map" checkbox
    public void noMapResults() {
        uncheckBox(mapResultsCheckbox);
    }

    //  Get text of the incorrect input error for the destination field
    public String getSearchboxErrorText() {
        return searchboxError.getText().replaceAll(searchboxInvisibleError.getText() + "\n", "");
    }

    //  Get text of the incorrect input error for the date field
    public String getDateboxErrorText() {
        return dateboxError.getText();
    }

    //  Click "Save 15% or more with Mid-Year Deals" banner
    public void clickMidyearDeals() {
        midyearDeals.click();
    }

    //  Click "Cities" link
    public void clickCitiesLink() {
        citiesLink.click();
    }
}
