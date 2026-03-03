package org.emw.selentic.regression.test;

import org.emw.selentic.lib.Selentic;
import org.testng.annotations.BeforeClass;

public class ScBaseTest {
    /**
     * Enable headless for all tests.
     */
    @BeforeClass
    public void baseSetup() {
        Selentic.enableHeadless();
    }
}
