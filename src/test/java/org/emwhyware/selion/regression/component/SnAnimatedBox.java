package org.emwhyware.selion.regression.component;

import org.emwhyware.selion.lib.SnComponent;
import org.emwhyware.selion.lib.SnComponentRule;

public class SnAnimatedBox extends SnComponent {
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
