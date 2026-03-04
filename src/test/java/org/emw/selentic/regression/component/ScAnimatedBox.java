package org.emw.selentic.regression.component;

import org.emw.selentic.lib.ScComponent;
import org.emw.selentic.lib.ScComponentRule;

public class ScAnimatedBox extends ScComponent {
    @Override
    protected void rules(ScComponentRule rule) {
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

    public void exposedWaitForAnimation() {
        super.waitForComponent(ScWaitCondition.ToStopAnimating);
    }
}
