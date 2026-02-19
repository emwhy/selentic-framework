package org.emwhyware.selentic.lib;

/**
 * A generic component with only basic properties exposed.
 * <p>
 * This is generally used to handle any text based tags (i.e., span, label, h1, etc.)
 *
 */
public final class ScGenericComponent extends ScComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.any();
    }
}
