package org.selion_framework.lib;

public abstract class SnFormComponent extends SnClickableComponent {
    protected String value() {
        return this.existing().getAttribute("value");
    }
}
