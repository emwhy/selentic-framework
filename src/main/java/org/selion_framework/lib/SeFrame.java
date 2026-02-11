package org.selion_framework.lib;

public final class SeFrame extends SeComponent {
    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().isOneOf("frame", "iframe");
    }
}
