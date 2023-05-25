package pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Represents the index page of the booking.com website
 */
public class IndexPage extends LoadableComponent<IndexPage> {
    private final static String url = "booking.com";

    private final SelenideElement cookieBanner = $(By.id("onetrust-banner-sdk"));
    private final SelenideElement cookieAcceptButton = $(By.id("onetrust-accept-btn-handler"));
    private final SelenideElement form = $(By.className("a0ac39e217"));
    private final SelenideElement destinationInput = $(By.id(":Ra9:"));
    private final SelenideElement destinationAutocomplete = $(By.className("a80e7dc237"));
    private final SelenideElement datesContainer = $("div[data-testid=\"searchbox-dates-container\"]");
    private final SelenideElement datePicker = $("nav[data-testid=\"datepicker-tabs\"]");
    private final ElementsCollection dates = $$("span[class=\"b21c1c6c83\"]");
    private final SelenideElement submitButton = $("button[type=\"submit\"]");

    /**
     * Fills the form
     *
     * @param city Destination city
     */
    public SearchResultPage fillForm(String city) {
        acceptCookies();
        setDestination(city);
        setDates();
        return submit();
    }

    public boolean isAcceptCookiesBannerAppears() {
        try {
            cookieBanner.should(visible);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    private void acceptCookies() {
        cookieAcceptButton.click();
        cookieBanner.should(visible);
    }

    private void setDestination(String city) {
        destinationInput.setValue(city);

        destinationAutocomplete.should(visible);
        destinationAutocomplete.should(partialText(city));
        destinationAutocomplete.click();
        destinationInput.should(partialValue(city));

    }

    private void setDates() {
        openDatePicker();
        selectDates();
    }

    private void openDatePicker() {
        if (!datePicker.isDisplayed()) {
            datesContainer.click();
        }

        datePicker.should(visible);
    }

    private void selectDates() {
        Random random = new Random();

        var firstIndex = random.nextInt(0, dates.size() / 2 - 1);
        var secondIndex = random.nextInt(dates.size() / 2, dates.size() - 1);

        var firstDate = dates.get(firstIndex);
        var secondDate = dates.get(secondIndex);

        firstDate.click();
        secondDate.click();
    }

    private SearchResultPage submit() {
        submitButton.click();
        return page(SearchResultPage.class);
    }

    @Override
    protected void load() {
        open("https://booking.com");
    }

    @Override
    protected void isLoaded() throws Error {
        try {
            if (!url().contains(url)) {
                fail();
            }
        } catch (IllegalStateException e) {
            fail();
        }

        try {
            form.should(visible);
            cookieBanner.should(visible);
        } catch (Exception e) {
            fail();
        }
    }
}
