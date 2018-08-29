// https://www.booking.com/index.ru.html
package com.gmail.vklinovenko.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BookingHomePage {
    private final WebDriver driver;

    public BookingHomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }
}
