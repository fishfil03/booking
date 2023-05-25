import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pageObjects.IndexPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class IsCookieBarOnIndexPageAppearsTest {
    private static IndexPage indexPage;
    @BeforeAll
    public static void init() {
        Configuration.holdBrowserOpen = true;
        indexPage = new IndexPage();

        indexPage.get();
    }

    @Test
    public void isCookieBarAppears(){
        assertTrue(indexPage.isAcceptCookiesBannerAppears());
    }
}
