package org.selion_framework.lib;

import org.selion_framework.lib.util.SnWait;

public abstract class SnDialog extends SnComponent {
    protected void waitForHidden() {
        SnWait.waitUntil(() -> !this.isDisplayed());
    }
}
