package org.selion_framework.sanity.component;

import org.selion_framework.lib.SnComponent;
import org.selion_framework.lib.SnComponentRule;

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
