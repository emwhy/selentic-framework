package org.selion_framework.lib;

public abstract class SnFormComponent extends SnClickableComponent {
    public boolean isEnabled() {
        return this.exists() && this.existing().isEnabled();
    }

    protected String value() {
        return this.existing().getAttribute("value");
    }
}
