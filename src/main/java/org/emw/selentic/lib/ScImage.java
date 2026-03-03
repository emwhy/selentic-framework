package org.emw.selentic.lib;

public class ScImage extends ScComponent {

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("img");
    }

    @Override
    public String text() {
        return source();
    }

    public String source() {
        return this.attr("src").orElse("");
    }
}
