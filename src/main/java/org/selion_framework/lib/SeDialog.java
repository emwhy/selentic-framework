package org.selion_framework.lib;

import org.selion_framework.lib.util.SeWait;

public abstract class SeDialog extends SeComponent {
    protected void waitForHidden() {
        SeWait.waitUntil(() -> !this.isDisplayed());
    }
}
