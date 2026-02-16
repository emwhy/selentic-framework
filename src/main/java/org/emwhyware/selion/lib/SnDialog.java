package org.emwhyware.selion.lib;

import org.emwhyware.selion.lib.util.SnWait;

public abstract class SnDialog extends SnComponent {
    protected void waitForHidden() {
        SnWait.waitUntil(() -> !this.isDisplayed());
    }
}
