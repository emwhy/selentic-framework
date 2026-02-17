package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.util.ScWait;

public abstract class ScDialog extends ScComponent {
    protected void waitForHidden() {
        ScWait.waitUntil(() -> !this.isDisplayed());
    }
}
