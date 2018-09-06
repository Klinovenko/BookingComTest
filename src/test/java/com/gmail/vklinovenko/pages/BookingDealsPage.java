//  https://www.booking.com/dealspage.ru.html
package com.gmail.vklinovenko.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

//import java.util.List;

public class BookingDealsPage {
    private final WebDriver driver;

    public BookingDealsPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

//    @FindBy(css = ".bui-grid")
//    private List<WebElement> dealsList;

    @FindBy(css = ".bui-grid > div:nth-child(1)")
    private WebElement firstSearchResult;

    //  Choose first search result
    public void chooseFirstSearchResult() {
        firstSearchResult.findElement(By.tagName("a")).click();
    }

//    //  Choose n-th search result
//    public void chooseSearchResult(byte number) {
//        dealsList.get(number - 1).findElement(By.tagName("a")).click();
//    }
//
//    //  Choose first search result on default
//    public void chooseSearchResult() {
//        chooseSearchResult((byte)1);
//    }
}
