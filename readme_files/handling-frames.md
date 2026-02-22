[< Return to README](../README.md)
## Handling Frames
Typically when handling a frame in Selenium, you would have to access the web driver and
switch back and forth between frames. 

In Selentic Framework, the lambda expression is used to handle switching between frames. This keeps the actions within the frame isolated and clear. Also this is consistent with implementation of pages, dialogs and additional windows.

Below is an example of implementing frame using **ScFrame** and **ScFrameContent**.

### Implementing Frame Content Component

The frame content component is like a page. It is implemented in the similar way.

```java

// A frame content class resembles a page class, since it is basically a page.
public class ScTestFrameContent extends ScFrameContent {
    private static final ScXPath TEST_EXTERNAL_TEXTBOX = _xpath.descendant("input", _id().is("test-external-textbox"));
    private static final ScXPath TEST_EXTERNAL_ROWS = _xpath.descendant("table", _id().is("test-external-table")).descendant("tr", _cssClasses("data"));
    
    @Override
    protected void waitForDisplayedPage() {
        waitForComponent(testExternalTextbox(), ScWaitCondition.ToBeDisplayed);
    }
    
    public ScTextbox testExternalTextbox() {
        return $component(TEST_EXTERNAL_TEXTBOX, ScTextbox.class);
    }
    
    public ScComponentCollection<ScTestTableRow> testExternalTableRows() {
        return $$components(TEST_EXTERNAL_ROWS, ScTestTableRow.class);
    }
}
```
### Adding Frame Content Component to a Page.

Adding the frame content component to the page is very similar to adding a dialog. It keeps the same pattern to ensure the consistency.

```java

public void TestPage extends ScPage {
    private static final ScCssSelector TEST_IFRAME = _cssSelector.descendant(_id("test-iframe"));

    // Other selectors and components.
    
    public void inTestInnerFrame(ScFrameAction<ScTestFrameContent> predicate) {
        $frame(TEST_IFRAME, ScTestFrameContent.class, predicate);
    }
}

```
### Implementing the Frame in Test Method

Again, the implementation of the frame to the test method is similar to the implementation with dialogs. It utilizes lambda expression for actions inside the frame.

Switching between frames are handled before and after the lambda expression automatically, not cluttering up the test code. This keeps test methods focused on important actions and assertions. 

```java

    @Test
    public void testFrame() {
        // In a test method.
        // The action within frame is inside the predicate method.
        testPage.inPage(p -> {
            p.inTestInnerFrame(frameContent -> {
                Assert.assertEquals(frameContent.testExternalTextbox().text(), "external textbox text");

                Assert.assertEquals(frameContent.testExternalTableRows().size(), 3);

                Assert.assertEquals(frameContent.testExternalTableRows().at(0).text(), "External Test 1");
                Assert.assertEquals(frameContent.testExternalTableRows().at(1).text(), "External Test 2");
                Assert.assertEquals(frameContent.testExternalTableRows().at(2).text(), "External Test 3");

                Assert.assertEquals(frameContent.testExternalTableRows().entry("External Test 1").serialNumberText.text(), "#EX-TDD987");
                Assert.assertEquals(frameContent.testExternalTableRows().entry("External Test 2").serialNumberText.text(), "#EX-AEV974");
                Assert.assertEquals(frameContent.testExternalTableRows().entry("External Test 3").serialNumberText.text(), "#EX-CCA106");
            });
        });
    } 
```
