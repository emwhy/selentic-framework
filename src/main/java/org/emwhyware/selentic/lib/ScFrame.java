package org.emwhyware.selentic.lib;

/**
 * A frame itself. This is a final class that can not be overridden.
 * <p>
 * The content of the frame should override {@link ScFrameContent}.
 *
 * <pre>{@code
 *
 *     // A frame content class resembles a page class, since it is basically a page.
 *     public class ScTestFrameContent extends ScFrameContent {
 *         private static final ScXPath TEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("test-external-textbox"));
 *         private static final ScXPath TEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("test-external-table")).descendant("tr", _cssClasses("data"));
 *
 *         @Override
 *         protected void waitForDisplayedPage() {
 *             waitForComponent(testExternalTextbox(), ScWaitCondition.ToBeDisplayed);
 *         }
 *
 *         public ScTextbox testExternalTextbox() {
 *             return $component(TEST_EXTERNAL_TEXTBOX, ScTextbox.class);
 *         }
 *
 *         public ScComponentCollection<ScTestTableRow> testExternalTableRows() {
 *             return $$components(TEST_EXTERNAL_ROWS, ScTestTableRow.class);
 *         }
 *     }
 *
 *     ...
 *
 *     // In a page object, a frame is defined like this.
 *     private static final ScCssSelector TEST_IFRAME = _cssSelector.descendant(_id("test-iframe"));
 *
 *     public void inTestInnerFrame(ScFrameAction&lt;ScTestFrameContent&gt; predicate) {
 *         $frame(TEST_IFRAME, ScTestFrameContent.class, predicate);
 *     }
 *
 *     ....
 *
 *     // In a test method.
 *     // The action within frame is inside the predicate method.
 *     testPage.inPage(p -> {
 *         p.inTestInnerFrame(frameContent -> {
 *             Assert.assertEquals(frameContent.testExternalTextbox().text(), "external textbox text");
 *
 *             Assert.assertEquals(frameContent.testExternalTableRows().size(), 3);
 *
 *             Assert.assertEquals(frameContent.testExternalTableRows().at(0).text(), "External Test 1");
 *             Assert.assertEquals(frameContent.testExternalTableRows().at(1).text(), "External Test 2");
 *             Assert.assertEquals(frameContent.testExternalTableRows().at(2).text(), "External Test 3");
 *
 *             Assert.assertEquals(frameContent.testExternalTableRows().entry("External Test 1").serialNumberText.text(), "#EX-TDD987");
 *             Assert.assertEquals(frameContent.testExternalTableRows().entry("External Test 2").serialNumberText.text(), "#EX-AEV974");
 *             Assert.assertEquals(frameContent.testExternalTableRows().entry("External Test 3").serialNumberText.text(), "#EX-CCA106");
 *         });
 *     });
 *
 * }</pre> *
 * @see ScFrameContent
 */
public final class ScFrame extends ScComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().isOneOf("frame", "iframe");
    }
}
