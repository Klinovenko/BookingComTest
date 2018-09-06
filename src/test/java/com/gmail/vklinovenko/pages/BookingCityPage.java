//  https://www.booking.com/city/ru/$CITY.ru.html
package com.gmail.vklinovenko.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingCityPage {
    private final WebDriver driver;

    public BookingCityPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@id='lp-upcoming-date-suggestions-block']//a[contains(@class, 'cheapest_weekend_price')]")
    private WebElement cheapestWeekendPriceButton;

    public void clickCheapestWeekendPrice() {
        cheapestWeekendPriceButton.click();
    }
}
