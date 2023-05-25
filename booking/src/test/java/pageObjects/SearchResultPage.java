package pageObjects;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.LoadableComponent;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Represents a search result page
 */
public class SearchResultPage extends LoadableComponent<SearchResultPage> {
    private final SelenideElement loginPopup = $(".e5aa33035e");
    private final SelenideElement loginPopupCloseButton = $(".e5aa33035e button");

    private final SelenideElement openMapButton = $("div[data-testid='map-trigger'] button");
    private final SelenideElement mapWithList = $(By.className("map_with_list__container"));
    private final SelenideElement firstResultOnMap = $(By.xpath("//div[@class=\"map-card__content-container\" or @class=\"dccc7e1586\"]"));
    private final SelenideElement firstMarker = $(By.xpath("//div[contains(@class, \"svg-marker svg-poi atlas-marker hotel fading\")]"));

    public void closeLoginPopupIfAny() {
        if (loginPopup.exists() && loginPopup.isDisplayed()) {
            loginPopupCloseButton.click();

            loginPopup.should(hidden);
        }
    }

    public void openMap() {
        closeLoginPopupIfAny();
        openMapButton.click();
        mapWithList.should(visible);
    }

    public void printFirstResultOnMap() {
        closeLoginPopupIfAny();
        firstResultOnMap.should(visible);

        System.out.println(getHotelTitle());
    }

    public String getHotelTitle() {
        By titleSelector = By.xpath(".//span[@class=\"map-card__title-link\" or @data-testid=\"header-title\"]");
        return firstResultOnMap.findElement(titleSelector).getText();
    }


    public String getHotelRating() {
        By selector = By.xpath(".//div[@class=\"b5cd09854e d10a6220b4\" or @class=\"bui-review-score__badge\"]");

        try {
            return firstResultOnMap.findElement(selector).getText();
        } catch (NoSuchElementException ex) {
            System.out.println("Блока с отзывами нет");
            return "";
        }
    }

    public String getHotelReviewsCount() {
        By selector = By.xpath(".//div[@class=\"bui-review-score__text\" or @class=\"d8eab2cf7f c90c0a70d3 db63693c62\"]");

        try {
            return firstResultOnMap.findElement(selector).getText();
        } catch (NoSuchElementException ex) {
            System.out.println("Блока с отзывами нет");
            return "";
        }
    }

    public int getHotelStars() {
        By selector = By.xpath(".//span[contains(@class, \"bui-rating__item\") or @class=\"b6dc9a9e69 adc357e4f1 fe621d6382\"]");

        return firstResultOnMap.findElements(selector).size();
    }

    public String getHotelPrice() {
        By selector = By.xpath(".//*[@class=\"fcab3ed991 bd73d13072\" or @class=\"prco-valign-middle-helper\"]");
        return firstResultOnMap.findElement(selector).getText();
    }

    public void hoverOnFirstResult() {
        closeLoginPopupIfAny();
        actions().moveToElement(firstResultOnMap).build().perform();
    }

    public HotelPage clickOnActiveMarker() {
        closeLoginPopupIfAny();
        hoverOnFirstResult();
        firstMarker.click();
        switchTo().window(1);
        return page(HotelPage.class);
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        try {
            if (!url().contains("booking.com/searchresults")) {
                fail();
            }
        } catch (IllegalStateException e) {
            System.out.println(e.getMessage());
            fail();
        }

        try {
            openMapButton.should(visible);
        } catch (TimeoutException e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}
