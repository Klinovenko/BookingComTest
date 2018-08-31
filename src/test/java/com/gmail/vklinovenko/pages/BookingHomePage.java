//  https://www.booking.com/index.ru.html
package com.gmail.vklinovenko.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

    private void selector(@NotNull WebElement select, byte num) {
        select.click();
        select.findElement(By.xpath("./option[@value='" + num + "']")).click();
    }

    private void checkBox(@NotNull WebElement checkbox) {
        if (!checkbox.isSelected())
            checkbox.findElement(By.xpath("./following-sibling::label")).click();
    }

    private void uncheckBox(@NotNull WebElement checkbox) {
        if (checkbox.isSelected())
            checkbox.findElement(By.xpath("./following-sibling::label")).click();
    }

    public void setDestination(String destination) {
        destinationInput.click();
        destinationInput.clear();
        destinationInput.sendKeys(destination);
    }

    public void setCheckInDate(String date) {
        checkInButton.click();
        checkInButton.sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(date.replaceAll("\\D", ""));
    }

    public void setCheckOutDate(String date) {
        checkOutButton.click();
        checkOutButton.sendKeys(Keys.TAB);
        driver.switchTo().activeElement().sendKeys(date.replaceAll("\\D", ""));
    }

    public void clickGuestsToggle() {
        guestsToggle.click();
    }

    private void setRooms(byte num) {
        selector(roomsNumSelect, num);
    }

    private void setAdults(byte num) {
        selector(adultsNumSelect, num);
    }

    private void setChildren(byte num, byte[] age) {
        selector(childrenNumSelect, num);
        if (num == 0) return;
        byte i = 0;
        for (WebElement ageSelect : childrenAgeSelects) {
            if (i < age.length) selector(ageSelect, age[i]);
            else selector(ageSelect, (byte)0);
            i++;
        }
    }

    private void setChildren(byte num) {
        byte[] age = {};
        setChildren(num, age);
    }

    public void setGuests(byte rooms, byte adults, byte children, byte[] age)
    {
        setRooms(rooms);
        setAdults(adults);
        setChildren(children, age);
    }

    public void setGuests(byte adults, byte children, byte[] age)
    {
        setRooms((byte)1);
        setAdults(adults);
        setChildren(children, age);
    }

    public void setGuests(byte rooms, byte adults)
    {
        setRooms(rooms);
        setAdults(adults);
        setChildren((byte)0);
    }

    public void setGuests(byte adults)
    {
        setRooms((byte)1);
        setAdults(adults);
        setChildren((byte)0);
    }

    public void clickSearchboxButton() {
        searchboxButton.click();
    }

    public void setWorkTravel() {
        checkBox(purposeCheckbox);
    }

    public void noMapResults() {
        uncheckBox(mapResultsCheckbox);
    }

    public String getSearchboxErrorText() {
        return searchboxError.getText().replaceAll(searchboxInvisibleError.getText() + "\n", "");
    }

    public String getDateboxErrorText() {
        return dateboxError.getText();
    }
}
