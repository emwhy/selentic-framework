package org.selion_framework.lib;

import org.openqa.selenium.WebElement;
import org.selion_framework.lib.util.SnWait;

public abstract class SnClickableComponent extends SnComponent {
    public boolean isEnabled() {
        return this.exists() && this.existing().isEnabled();
    }

    protected final WebElement enabled() {
        SnWait.waitUntil(this::isEnabled);
        return scrolled();
    }

    public void click() {
        enabled();
        super.click();
    }

    public void doubleClick() {
        enabled();
        super.doubleClick();
    }

    public void clickAt(int x, int y) {
        enabled();
        super.clickAt(x, y);
    }

    public void doubleClickAt(int x, int y) {
        enabled();
        super.doubleClickAt(x, y);
    }
}
