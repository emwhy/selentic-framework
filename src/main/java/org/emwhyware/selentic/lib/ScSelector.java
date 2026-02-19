package org.emwhyware.selentic.lib;

import org.openqa.selenium.By;

import java.util.Optional;

/**
 * Abstract sealed base class for web element selectors.
 * <p>
 * The {@code ScSelector} class defines a hierarchical selector pattern that allows building
 * complex element locators by chaining multiple selector nodes together.
 * 
 *
 * @see ScCssSelector
 * @see ScXPath
 * @see org.openqa.selenium.By
 */
public abstract sealed class ScSelector permits ScCssSelector, ScXPath {
    private final Optional<ScSelector> priorSelectorNode;

    /**
     * Constructs a root selector with no prior selector node.
     * <p>
     * This constructor is typically used when creating the first selector in a chain
     * or when creating a standalone selector that does not reference any prior selector.
     * 
     * <p>
     * The {@code priorSelectorNode} will be {@link Optional#empty() empty}.
     * 
     */
    protected ScSelector() {
        this.priorSelectorNode = Optional.empty();
    }

    /**
     * Constructs a selector that references a prior selector node.
     * <p>
     * This constructor is used when chaining selectors together, allowing the creation
     * of complex hierarchical selector structures. The prior selector node is stored as
     * an {@link Optional} reference and can be retrieved via {@link #priorSelectorNode()}.
     * 
     * <p>
     * This pattern enables flexible selector composition while maintaining references to
     * the selector hierarchy.
     * 
     *
     * @param priorSelectorNode the previous selector in the chain, must not be null
     * @throws NullPointerException if priorSelectorNode is null
     */
    protected ScSelector(ScSelector priorSelectorNode) {
        this.priorSelectorNode = Optional.of(priorSelectorNode);
    }

    /**
     * Returns the optional prior selector node in this selector chain.
     * <p>
     * If this selector is a root selector (created with the no-argument constructor),
     * the returned {@link Optional} will be empty. If this selector was created with
     * a prior node reference, the {@link Optional} will contain that node.
     * 
     * <p>
     * This method allows traversal up the selector hierarchy to inspect or rebuild
     * selector chains.
     * 
     *
     * @return an {@link Optional} containing the prior selector node, or {@link Optional#empty()}
     *         if this is a root selector
     * @see Optional
     */
    protected Optional<ScSelector> priorSelectorNode() {
        return priorSelectorNode;
    }

    /**
     * Returns true if this selector should look at the entire page instead of relatively inside of component.
     *
     * @return true if selector should look at the entire page
     */
    protected boolean isAbsolute() {
        return priorSelectorNode.map(ScSelector::isAbsolute).orElseGet(() -> this instanceof ScCssSelectorPage || this instanceof ScXPathPage);
    }

    /**
     * Returns the text representation of this selector node.
     *
     * @return the text representation of this selector node
     */
    protected abstract String nodeText();

    /**
     * Builds and returns a Selenium {@link By} locator for this selector.
     *
     * @return a {@link By} locator constructed from this selector and its chain
     */
    protected abstract By build();
}