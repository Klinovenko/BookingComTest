//https://www.booking.com/hotel/$COUNTRY/$HOTEL.ru.html
package com.gmail.vklinovenko.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookingHotelPage {
    private final WebDriver driver;

    public BookingHotelPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = ".js-group-recommendation-reserve-btn")
    private WebElement bookRecommendedButton;

    //  Close hotel window
    public void close() {
        driver.close();
    }

    //  Book recommended rooms
    public void bookRecommendedRooms() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.elementToBeClickable(bookRecommendedButton)).click();
    }
}
