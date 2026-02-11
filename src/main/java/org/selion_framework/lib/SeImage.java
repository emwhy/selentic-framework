package org.selion_framework.lib;

public class SeImage extends SeComponent {

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("img");
    }

    @Override
    public String key() {
        return source();
    }

    public String source() {
        return this.attr("src").orElse("");
    }
}
