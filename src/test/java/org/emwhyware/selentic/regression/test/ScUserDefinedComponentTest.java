package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.ScPage;
import org.emwhyware.selentic.lib.ScWithPage;
import org.emwhyware.selentic.lib.Selentic;
import org.emwhyware.selentic.regression.page.ScLoginEnhancedPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ScUserDefinedComponentTest extends ScBaseTest {
    private final ScWithPage<ScLoginEnhancedPage> loginPage = ScPage.with(ScLoginEnhancedPage.class);

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
            Assert.assertEquals(p.accountTypeDropdown().selectedText(), "");

            p.accountTypeDropdown().select("Viewer");

            Assert.assertEquals(p.accountTypeDropdown().selectedText(), "Viewer");

            p.accountTypeDropdown().select("Editor");

            Assert.assertEquals(p.accountTypeDropdown().selectedText(), "Editor");

            p.userNameTextbox().enterText("test");

            Assert.assertEquals(p.userNameTextbox().text(), "test");

            p.passwordTextbox().enterText("test123");

            Assert.assertEquals(p.passwordTextbox().text(), "test123");

            p.loginButton().click();
        });
    }
}
