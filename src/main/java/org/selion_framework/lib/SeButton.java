package org.selion_framework.lib;

import java.util.Arrays;
import java.util.List;

public class SeButton extends SeFormComponent {
    private static final List<String> VALID_TYPE_ATTRIBUTES = Arrays.stream(new String[] { "submit", "button" }).toList();

    @Override
    protected void rules(SeComponentRule rule) {
        // Needs to do extra IF because button can be both input and button.
        rule.tag().isOneOf("input", "button");
        if (this.tag().equals("input")) {
            rule.type().isOneOf("button", "submit", "reset");
        }
    }

    @Override
    public String key() {
        if (this.tag().equals("button")) {
            return super.key();
        } else {
            return this.value();
        }
    }
}
