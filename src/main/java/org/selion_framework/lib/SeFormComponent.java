package org.selion_framework.lib;

public abstract class SeFormComponent extends SeClickableComponent {
    public boolean isEnabled() {
        return this.exists() && this.existing().isEnabled();
    }

    protected String value() {
        return this.existing().getAttribute("value");
    }
}
