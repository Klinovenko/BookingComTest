//  https://www.booking.com/searchresults.ru.html
package com.gmail.vklinovenko.pages;

import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BookingSearchResults {
    private final WebDriver driver;

    public BookingSearchResults(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//div[@id='filter_out_of_stock']/div[2]/a")
    private WebElement availableOnlyCheckbox;

    @FindBy(xpath = "//div[@id='filter_review']/div[2]/a[@data-id='review_score-80']")
    private WebElement review8plusCheckbox;

    private void checkBox(@NotNull WebElement checkbox) {
        if (!checkbox.findElement(By.xpath("./..")).getAttribute("class").contains("selected"))
            checkbox.click();
    }

    private void uncheckBox(@NotNull WebElement checkbox) {
        if (!checkbox.findElement(By.xpath("./..")).getAttribute("class").contains("selected"))
            checkbox.click();
    }

    public void checkAvailableOnlyCheckbox() {
        checkBox(availableOnlyCheckbox);
    }

    public void checkReview8plusCheckbox() {
        checkBox(review8plusCheckbox);
    }

    // TODO IT DOESN'T WORK!
}
