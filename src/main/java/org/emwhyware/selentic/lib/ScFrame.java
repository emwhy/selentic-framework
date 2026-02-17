package org.emwhyware.selentic.lib;

public final class ScFrame extends ScComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().isOneOf("frame", "iframe");
    }
}
