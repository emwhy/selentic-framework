package org.selion_framework.lib;

public abstract class SeSelectableComponent extends SeFormComponent {

    /**
     * Override to look for <label> tag for the text content for this selectable. If not found,
     * "value" is used as its text value.
     * @return
     */
    @Override
    public String key() {
        final SeGenericComponent $parentComponent = $component(_xpath.parent(), SeGenericComponent.class);

        if ($parentComponent.tag().equals("label")) {
            return $parentComponent.key();
        } else {
            final SeGenericComponent $label = $component(_xpath.page("label", _attr("for").is(this.id())), SeGenericComponent.class);

            if ($label.exists()) {
                return $label.key();
            }
        }
        return this.value();
    }

    public boolean isSelected() {
        return this.existing().isSelected();
    }

    public void select() {
        if (!this.isSelected()) {
            this.scrolled().click();
        }
    }

}
