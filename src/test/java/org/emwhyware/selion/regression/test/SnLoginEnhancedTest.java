package org.emwhyware.selion.regression.test;

import org.emwhyware.selion.lib.Selion;
import org.emwhyware.selion.lib.SnPage;
import org.emwhyware.selion.lib.SnWithPage;
import org.emwhyware.selion.regression.page.SnLoginEnhancedPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SnLoginEnhancedTest {
    private final SnWithPage<SnLoginEnhancedPage> loginPage = SnPage.with(SnLoginEnhancedPage.class);

    @BeforeClass
    public void setup() {
        Selion.open("file://" + System.getProperty("user.dir") + "/build/resources/test/test_file/login-enhanced.htm");
    }

    @AfterClass(alwaysRun = true)
    public void finish() {
        Selion.quit();
    }

    @Test
    public void testLogin() {
        loginPage.inPage(p -> {
            p.accountTypeDropdown.select("Viewer");

            Assert.assertEquals(p.accountTypeDropdown.text(), "Viewer");

            p.userNameTextbox.enterText("test");
            p.passwordTextbox.enterText("test");
            p.loginButton.click();
        });
    }
}
