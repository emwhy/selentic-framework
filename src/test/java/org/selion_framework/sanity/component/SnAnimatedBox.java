package org.selion_framework.sanity.component;

import org.selion_framework.lib.SnComponent;
import org.selion_framework.lib.SnComponentRule;

public class SnSanityTestAnimatedBox extends SnComponent {
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
