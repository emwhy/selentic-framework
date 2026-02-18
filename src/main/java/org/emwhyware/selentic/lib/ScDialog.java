package org.emwhyware.selentic.lib;

public abstract class ScDialog extends ScComponent {
    protected void waitForDisplayedDialog() {
        waitForComponent(ScWaitCondition.ToBeDisplayed);
    }

    protected void waitForHiddenDialog() {
        waitForComponent(ScWaitCondition.ToBeHidden);
    }
}
