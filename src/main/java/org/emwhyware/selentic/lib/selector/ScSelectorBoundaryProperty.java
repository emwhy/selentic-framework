package org.emwhyware.selentic.lib.selector;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScSelectorException;

/**
 * This selector property defines a boundary component to a list of components.
 *
 * <p>The method generates XPath, <b>[following:: ...]</b>. The XPath naming is not very clear what it does,
 * so the method implementation makes it more legible and clear.
 * <p>
 * The parameter {@link ScSelector} must be of the page level. It would fail if a relative path is passed.
 *
 * <p>
 * <strong>Example:</strong>
 * <pre>{@code
 * _xpath.descendant("div", _boundary(_xpath.descendant("div", _id().is("lower-boundary"))); // List of components, up to the given component.
 * }</pre>

 * @see ScXPath
 */
public final class ScSelectorBoundaryProperty extends ScSelectorProperty implements ScXpathPropertyType {
    private final ScXPath xpath;

    ScSelectorBoundaryProperty(ScXPath xpath) {
        this.xpath = xpath;
    }

    @Override
    public String build(@NonNull Types type) {
        String selector = "";

        if (this.xpath.isAbsolute()) {
            selector = this.xpath.toString().replaceAll("^/[a-z-]+", "following");
        } else {
            throw new ScSelectorException("XPath for 'boundary' must be based on _xpath.page(...) which looks at the entire page rather than inside of a component.");
        }

        if (this.negated()) {
            selector = "not(" + selector + ")";
        }
        return "[" + selector + "]";
    }
}
