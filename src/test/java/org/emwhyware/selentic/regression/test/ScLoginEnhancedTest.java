package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.ScPage;
import org.emwhyware.selentic.lib.Selentic;
import org.emwhyware.selentic.lib.SnWithPage;
import org.emwhyware.selentic.regression.page.ScLoginEnhancedPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ScLoginEnhancedTest {
    private final SnWithPage<ScLoginEnhancedPage> loginPage = ScPage.with(ScLoginEnhancedPage.class);

    @BeforeClass
    public void setup() {
        Selentic.open("file://" + System.getProperty("user.dir") + "/build/resources/test/test_file/login-enhanced.htm");
    }

    @AfterClass(alwaysRun = true)
    public void finish() {
        Selentic.quit();
    }

    @Test
    public void testLogin() {
        loginPage.inPage(p -> {
            Assert.assertEquals(p.accountTypeDropdown.selectedText(), "");

            p.accountTypeDropdown.select("Viewer");

            Assert.assertEquals(p.accountTypeDropdown.selectedText(), "Viewer");

            p.accountTypeDropdown.select("Editor");

            Assert.assertEquals(p.accountTypeDropdown.selectedText(), "Editor");

            p.userNameTextbox.enterText("test");
            p.passwordTextbox.enterText("test");
            p.loginButton.click();
        });
    }
}
