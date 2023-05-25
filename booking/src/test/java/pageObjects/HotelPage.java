package pageObjects;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.LoadableComponent;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.fail;

public class HotelPage extends LoadableComponent<HotelPage> {
    private final SelenideElement title = $(By.className("pp-header__title"));
    private final SelenideElement rating = $(By.cssSelector(".b5cd09854e.d10a6220b4"));
    private final SelenideElement reviewsCount = $(By.cssSelector(".d8eab2cf7f.c90c0a70d3.db63693c62"));
    private final ElementsCollection stars = $$(By.cssSelector(".b6dc9a9e69.adc357e4f1.fe621d6382"));
    private final SelenideElement price = $(By.className("prco-valign-middle-helper"));


    public String getTitle() {
        return title.getText();
    }

    public String getRating() {
        return rating.getText();
    }

    public String getReviewsCount() {
        return reviewsCount.getText();
    }

    public int getStars() {
        return stars.size();
    }

    public String getPrice() {
        return price.getText();
    }

    @Override
    protected void load() {

    }

    @Override
    protected void isLoaded() throws Error {
        try {
            title.should(visible);
        } catch (TimeoutException e) {
            fail();
            System.out.println(e.getMessage());
        }
    }
}
