package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.SnComponentRule;

public class ScExposedOwnText extends ScComponent {
    @Override
    protected void rules(SnComponentRule rule) {
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
