package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.ScPage;
import org.emwhyware.selentic.lib.ScWithPage;
import org.emwhyware.selentic.lib.Selentic;
import org.emwhyware.selentic.regression.page.ScLoginEnhancedPage;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Regression test suite for validating high-level, user-defined components.
 * This class tests the interaction with specialized UI abstractions (e.g., custom dropdowns
 * and enhanced textboxes) to ensure the framework correctly handles complex component behaviors
 * beyond standard HTML elements.
 */
public class ScUserDefinedComponentTest extends ScBaseTest {

    /** Page object wrapper for the enhanced login page containing custom components. */
    private final ScWithPage<ScLoginEnhancedPage> loginPage = ScPage.with(ScLoginEnhancedPage.class);

    /**
     * Prepares the test environment by launching the browser and navigating to the
     * enhanced login test resource.
     */
    @BeforeClass
    public void setup() {
        Selentic.open("file://" + System.getProperty("user.dir") + "/build/resources/test/test_file/login-enhanced.htm");
    }

    /**
     * Performs cleanup by closing the browser session after test execution.
     */
    @AfterClass(alwaysRun = true)
    public void finish() {
        Selentic.quit();
    }

    /**
     * Exercises a standard login workflow to verify the functionality of user-defined components:
     * <ul>
     *     <li>Verifies default and updated states of a custom Dropdown component.</li>
     *     <li>Verifies text entry and retrieval for custom Textbox components.</li>
     *     <li>Verifies click action on a custom Button component.</li>
     * </ul>
     */
    @Test
    public void testLogin() {
        loginPage.inPage(p -> {
            // Validate initial state of the custom account type dropdown
            Assert.assertEquals(p.accountTypeDropdown().selectedText(), "");

            // Test selection logic for multiple options
            p.accountTypeDropdown().select("Viewer");
            Assert.assertEquals(p.accountTypeDropdown().selectedText(), "Viewer");

            p.accountTypeDropdown().select("Editor");
            Assert.assertEquals(p.accountTypeDropdown().selectedText(), "Editor");

            // Test interaction with the enhanced Username textbox
            p.userNameTextbox().enterText("test");
            Assert.assertEquals(p.userNameTextbox().text(), "test");

            // Test interaction with the enhanced Password (masked) textbox
            p.passwordTextbox().enterText("test123");
            Assert.assertEquals(p.passwordTextbox().text(), "test123");

            // Execute submission via the custom login button
            p.loginButton().click();
        });
    }
}
