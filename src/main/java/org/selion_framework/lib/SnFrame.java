package org.selion_framework.lib;

public final class SnFrame extends SnComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().isOneOf("frame", "iframe");
    }
}
