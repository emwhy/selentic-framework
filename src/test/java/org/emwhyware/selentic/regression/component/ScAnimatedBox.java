package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.SnComponentRule;

public class ScAnimatedBox extends ScComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.id().is("animated-box");
    }

    /**
     * Override the value for this class only.
     * @return
     */
    @Override
    protected long waitTimeout() {
        return 5000;
    }

    /**
     * Exposing {@link #waitForAnimation()} for testing the method.
     */
    public void exposedWaitForAnimation() {
        super.waitForAnimation();
    }
}
