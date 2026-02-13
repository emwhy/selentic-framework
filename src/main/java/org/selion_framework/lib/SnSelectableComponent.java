package org.selion_framework.lib;

public abstract class SnSelectableComponent extends SnFormComponent {

    /**
     * Override to look for <label> tag for the text content for this selectable. If not found,
     * "value" is used as its text value.
     * @return
     */
    @Override
    public String key() {
        final SnGenericComponent $parentComponent = $component(_xpath.parent(), SnGenericComponent.class);

        if ($parentComponent.tag().equals("label")) {
            return $parentComponent.key();
        } else if (this.id().isPresent()){
            final SnGenericComponent $label = $component(_xpath.page("label", _attr("for").is(this.id().get())), SnGenericComponent.class);

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
