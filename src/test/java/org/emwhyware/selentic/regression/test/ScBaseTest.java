package org.emwhyware.selentic.regression.test;

import org.emwhyware.selentic.lib.Selentic;
import org.testng.annotations.BeforeClass;

public class ScBaseTest {
    @BeforeClass
    public void baseSetup() {
        Selentic.enableHeadless();
    }
}
