package org.emwhyware.selentic.lib;

/**
 * Configuration class for customizing element scroll behavior for {@link ScComponent}.
 * <p>
 * The {@code SnScrollOptions} class encapsulates scroll options that control how elements
 * are scrolled into view on the page. It implements the builder pattern, allowing for
 * fluent configuration of scroll behavior, vertical alignment (block), and horizontal
 * alignment (inline).
 * 
 *
 * <h2>Default Configuration:</h2>
 * <ul>
 *   <li><strong>Behavior</strong>: {@link Behavior#instant} - Scroll without animation</li>
 *   <li><strong>Block</strong>: {@link Block#center} - Align element to center vertically</li>
 *   <li><strong>Inline</strong>: {@link Inline#center} - Align element to center horizontally</li>
 * </ul>
 *
 * <h3>Usage Example:</h3>
 * <pre>
 * // You can only get this inside of a subclass of {@link ScComponent} because methods are protected.
 * this.scrolled(this.scrollOptions().behavior(SnScrollOptions.Behavior.instant).block(SnScrollOptions.Block.center)).
 *
 * </pre>
 *
 * <h3>Scroll Behavior Visualization:</h3>
 * <pre>
 * Block alignment (vertical):
 *   start   ┌─────────────────────────┐  ← Element aligns to top of viewport
 *   center  │                         │
 *           ├─────────────────────────┤  ← Element aligns to center of viewport
 *           │                         │
 *   end     └─────────────────────────┘  ← Element aligns to bottom of viewport
 *
 * Inline alignment (horizontal):
 *   start   │←── Element at left edge
 *   center  │←── Element at horizontal center
 *   end     │←── Element at right edge
 * </pre>
 *
 * @see #behavior(Behavior)
 * @see #block(Block)
 * @see #inline(Inline)
 */
public class SnScrollOptions {
    private Behavior behavior;
    private Block block;
    private Inline inline;

    /**
     * Constructs a new {@code SnScrollOptions} with default scroll configuration.
     * <p>
     * The default settings are:
     * <ul>
     *   <li>Behavior: {@link Behavior#instant} - immediate scroll without animation</li>
     *   <li>Block: {@link Block#center} - center element vertically in viewport</li>
     *   <li>Inline: {@link Inline#center} - center element horizontally in viewport</li>
     * </ul>
     * 
     * <p>
     * Individual options can be customized using the builder-style methods after construction.
     * 
     */
    SnScrollOptions() {
        this.behavior = Behavior.instant;
        this.block = Block.center;
        this.inline = Inline.center;
    }

    /**
     * Sets the scroll behavior for this scroll operation.
     * <p>
     * Controls how the element is scrolled into view:
     * <ul>
     *   <li>{@link Behavior#smooth} - Animates the scroll smoothly</li>
     *   <li>{@link Behavior#instant} - Scrolls immediately without animation</li>
     *   <li>{@link Behavior#auto} - Browser-dependent behavior (typically instant)</li>
     * </ul>
     * 
     *
     * @param behavior the scroll behavior, must not be null
     * @return this {@code SnScrollOptions} instance for method chaining
     * @throws NullPointerException if behavior is null
     */
    public SnScrollOptions behavior(Behavior behavior) {
        this.behavior = behavior;
        return this;
    }

    /**
     * Sets the vertical alignment of the element within the viewport.
     * <p>
     * Controls where the element is positioned vertically when scrolled into view:
     * <ul>
     *   <li>{@link Block#start} - Align element to the top of the viewport</li>
     *   <li>{@link Block#center} - Center element vertically in the viewport</li>
     *   <li>{@link Block#end} - Align element to the bottom of the viewport</li>
     *   <li>{@link Block#nearest} - Minimize scrolling distance (scroll minimally)</li>
     * </ul>
     * 
     *
     * @param block the vertical alignment option, must not be null
     * @return this {@code SnScrollOptions} instance for method chaining
     * @throws NullPointerException if block is null
     */
    public SnScrollOptions block(Block block) {
        this.block = block;
        return this;
    }

    /**
     * Sets the horizontal alignment of the element within the viewport.
     * <p>
     * Controls where the element is positioned horizontally when scrolled into view:
     * <ul>
     *   <li>{@link Inline#start} - Align element to the left of the viewport</li>
     *   <li>{@link Inline#center} - Center element horizontally in the viewport</li>
     *   <li>{@link Inline#end} - Align element to the right of the viewport</li>
     *   <li>{@link Inline#nearest} - Minimize scrolling distance (scroll minimally)</li>
     * </ul>
     * 
     *
     * @param inline the horizontal alignment option, must not be null
     * @return this {@code SnScrollOptions} instance for method chaining
     * @throws NullPointerException if inline is null
     */
    public SnScrollOptions inline(Inline inline) {
        this.inline = inline;
        return this;
    }

    /**
     * Returns a JavaScript-compatible string representation of these scroll options.
     * <p>
     * The returned string format is suitable for use with JavaScript's
     * {@code Element.scrollIntoView()} API. The format is:
     * {@code {behavior: '<behavior>', block: '<block>', inline: '<inline>'}}
     * 
     *
     * @return a JavaScript-formatted string representation of the scroll options
     */
    @Override
    public String toString() {
        return "{behavior: '%s', block: '%s', inline: '%s'}".formatted(this.behavior, this.block, this.inline);
    }

    /**
     * Enumeration for scroll animation behavior options.
     * <p>
     * Controls how the scroll operation is performed when an element is scrolled into view.
     * 
     *
     * @since 1.0
     */
    public enum Behavior {
        /**
         * Scrolls smoothly with animation. The duration and easing function are browser-dependent.
         */
        smooth,

        /**
         * Scrolls immediately to the target position without animation.
         */
        instant,

        /**
         * Browser-dependent behavior. Typically behaves like {@link #instant}, but the exact
         * behavior may vary across different browsers.
         */
        auto
    }

    /**
     * Enumeration for vertical alignment options when scrolling into view.
     * <p>
     * Controls where the element is positioned vertically within the viewport after scrolling.
     * 
     *
     * @since 1.0
     */
    public enum Block {
        /**
         * Aligns the element to the top edge of the viewport. The element's top edge will be
         * aligned with the top of the viewport.
         */
        start,

        /**
         * Centers the element vertically within the viewport. The element will be positioned
         * such that it appears in the middle of the visible area.
         */
        center,

        /**
         * Aligns the element to the bottom edge of the viewport. The element's bottom edge will
         * be aligned with the bottom of the viewport.
         */
        end,

        /**
         * Minimizes the scroll distance. Scrolls minimally to make the element visible, keeping
         * it at its current position if already in the viewport.
         */
        nearest
    }

    /**
     * Enumeration for horizontal alignment options when scrolling into view.
     * <p>
     * Controls where the element is positioned horizontally within the viewport after scrolling.
     * 
     *
     * @since 1.0
     */
    public enum Inline {
        /**
         * Aligns the element to the left edge of the viewport. The element's left edge will be
         * aligned with the left side of the viewport.
         */
        start,

        /**
         * Centers the element horizontally within the viewport. The element will be positioned
         * such that it appears in the middle of the visible area horizontally.
         */
        center,

        /**
         * Aligns the element to the right edge of the viewport. The element's right edge will
         * be aligned with the right side of the viewport.
         */
        end,

        /**
         * Minimizes the scroll distance. Scrolls minimally to make the element visible horizontally,
         * keeping it at its current position if already in the viewport.
         */
        nearest
    }
}