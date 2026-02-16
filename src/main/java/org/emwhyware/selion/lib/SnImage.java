package org.emwhyware.selion.lib;

public class SnImage extends SnComponent {

    @Override
    protected void rules(SnComponentRule rule) {
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
