package org.emw.selentic.regression.component;

import org.emw.selentic.lib.ScComponent;
import org.emw.selentic.lib.ScComponentRule;

public class ScExposedOwnText extends ScComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.any();
    }

    /**
     * Expose ownText() for testing purpose only.
     * @return Own text, exposed as public.
     */
    public String exposedOwnText() {
        return super.ownText();
    }
}
