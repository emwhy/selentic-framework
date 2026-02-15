package org.selion_framework.lib;

public class SnButton extends SnFormComponent {
    @Override
    protected void rules(SnComponentRule rule) {
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
