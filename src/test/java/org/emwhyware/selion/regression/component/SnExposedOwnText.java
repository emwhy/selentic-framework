package org.emwhyware.selion.regression.component;

import org.emwhyware.selion.lib.SnComponent;
import org.emwhyware.selion.lib.SnComponentRule;

public class SnExposedOwnText extends SnComponent {
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
