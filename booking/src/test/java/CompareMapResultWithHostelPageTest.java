import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pageObjects.HotelPage;
import pageObjects.IndexPage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompareMapResultWithHostelPageTest {
    private static HotelPage hotelPage;

    private static String title;
    private static String rating;
    private static String reviewsCount;
    private static int stars;
    private static String price;


    @BeforeAll
    public static void init() {
        Configuration.holdBrowserOpen = true;
        IndexPage indexPage = new IndexPage();

        indexPage.get();
        var searchResultPage = indexPage.fillForm("Вашингтон");
        searchResultPage.openMap();
        searchResultPage.printFirstResultOnMap();

        title = searchResultPage.getHotelTitle();
        rating = searchResultPage.getHotelRating();
        reviewsCount = searchResultPage.getHotelReviewsCount();
        stars = searchResultPage.getHotelStars();
        price = searchResultPage.getHotelPrice();

        searchResultPage.hoverOnFirstResult();
        hotelPage = searchResultPage.clickOnActiveMarker();
    }

    @Test
    public void titlesAreEqual(){
        assertEquals(title, hotelPage.getTitle());
    }

    @Test
    public void starsAreEqual(){
        assertEquals(stars, hotelPage.getStars());
    }

    @Test
    public void priceIsEqual(){
        assertEquals(price, hotelPage.getPrice());
    }

    @Test
    public void reviewsCountAreEqual(){
        assertEquals(reviewsCount, hotelPage.getReviewsCount());
    }

    @Test
    public void ratingIsEqual(){
        assertEquals(rating, hotelPage.getRating());
    }
}
