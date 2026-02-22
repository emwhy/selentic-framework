[< Return to README](../README.md)
## Implementing a Dialog Class

### Creating a Dialog Component
Creating a dialog component is not much different to creating any other component. It follows the same structure, with selectors, rules, and exposed components within the dialog.
The component extends from **ScDialog** instead of **ScComponent**.
```java

public class ScTestDialog extends ScDialog {
    private static final ScXPath TEXTBOX = _xpath.descendant("input", _id().is("test-dialog-textbox"));
    private static final ScXPath CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));
    
    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("div");
        rule.id().is("test-dialog");
    }
    
    public ScTextbox textbox() { 
        return $component(TEXTBOX, ScTextbox.class); 
    }
    
    public ScButton closeButton() { 
        return $component(CLOSE_BUTTON, ScButton.class); 
    }
}
```
### Adding the Dialog to a Page Object

Dialogs are defined in a page as shown below. 

This pattern allows passing a lambda expression containing the test actions once the dialog is open. After the lambda function ends, it automatically checks for the dialog being hidden before proceeding with the next step. 

It is recommended to follow the naming pattern of "in____Dialog". This makes it clear where the actions are implemented for in the test code.
```java

public class ScTestPage extends ScPage {
    // Selector.
    private static final ScCssSelector TEST_DIALOG = _cssSelector.descendant(_id("test-dialog"));

    // Other selectors and components ...

    // Dialog.    
    public void inTestDialog(ScDialogAction<ScTestDialog> predicate) {
        $dialog(TEST_DIALOG, ScTestDialog.class, predicate);
    }
}
```
### Implementing the Dialog in Test Method

Here is an example of a dialog implementation in test. Lambda expression makes the
dialog component available inside the function.

Before the lambda function, it automatically ensures that the dialog is displayed, and it throws error otherwise.

After the lambda function, it automatically ensure that the dialog is closed, and it throws error otherwise.

```java
    @Test
    public void testDialog() {
        // Page itself is accessible through lambda function.
        testPage.inPage(p -> {
            p.openTestDialogButton().click();
            p.inTestDialog(dialog -> {
                // Before it continues here, it waits for the dialog to be displayed. The behavior of the wait can be
                // changed by overriding waitForDisplayedDialog() method.

                // Any action in dialog goes in here.
                dialog.textbox.enterText("Test text in dialog");

                dialog.closeButton.click();
            });
            // At the close, it automatically wait for the dialog to be hidden. The behavior of the wait can be changed
            // by overriding waitForHiddenDialog() method.
        });

    }
 

```

### "waitForDisplayedDialog()" and "waitForHiddenDialog()" Methods

**waitForDisplayedDialog()** and **waitForHiddenDialog()** are executed before and after lambda function. 

Sometimes, a dialog on a web page could have unique behaviors (i.e., animation) that makes it not reliably wait
for a dialog to open or close.

In such cases, override these methods to add different waiting strategy.

If you see the same type of dialog throughout the application you are testing, then it might be a good idea to create your own base Dialog class which all of your dialogs extend from.

```java
public abstract class YourDialog extends ScDialog {
    // Wait for opening animation to end before checking for the dialog to be displayed.
    @Override
    public waitForDisplayedDialog() {
        this.waitForHiddenDialog(ScWaitCondition.ToStopAnimating);
        super.waitForDisplayedDialog();
    }
    
    // Wait for open animation to end before checking for the dialog to be hidden.
    @Override
    public waitForHiddenDialog() {
        this.waitForHiddenDialog(ScWaitCondition.ToStopAnimating);
        super.waitForHiddenDialog();
    }
}
```