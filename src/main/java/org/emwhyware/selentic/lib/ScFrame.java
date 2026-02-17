package org.emwhyware.selentic.lib;

public final class ScFrame extends ScComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().isOneOf("frame", "iframe");
    }
}
