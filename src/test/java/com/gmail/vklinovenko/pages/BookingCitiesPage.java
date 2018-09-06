//  https://www.booking.com/city.ru.html
package com.gmail.vklinovenko.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingCitiesPage {
    private final WebDriver driver;

    public BookingCitiesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@id='cityTmpl']/div[1]/div[@class='block_header']//a")
    private WebElement mostPopularCity;

    public void clickMostPopularCity() {
        mostPopularCity.click();
    }
}
