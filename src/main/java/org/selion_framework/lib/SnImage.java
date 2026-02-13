package org.selion_framework.lib;

public class SnImage extends SnComponent {

    @Override
    protected void rules(SnComponentRule rule) {
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
