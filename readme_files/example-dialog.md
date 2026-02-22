[< Return to README](../README.md)
## Example: Implementing a Dialog Class
Below is an example implementation of dialog component.

```java
 
      // A dialog object is defined like this. It is almost identical, except that it extends from ScDialog class.
      public class ScTestDialog extends ScDialog {
          private static final ScXPath TEXTBOX = _xpath.descendant("input", _id().is("test-dialog-textbox"));
          private static final ScXPath CLOSE_BUTTON = _xpath.descendant("button", _cssClasses("close"));
 
          @Override
          protected void rules(ScComponentRule rule) {
              rule.tag().is("div");
              rule.id().is("test-dialog");
          }
 
          public ScTextbox textbox() { return} $component(TEXTBOX, ScTextbox.class); }
          public ScButton closeButton() { $component(CLOSE_BUTTON, ScButton.class); }
      }
 
      ...
 
      // In a page object, a dialog is defined like this.
      private static final ScCssSelector TEST_DIALOG = _cssSelector.descendant(_id("test-dialog"));
 
      public void inSanityTestDialog(ScDialogAction&lt;ScTestDialog&gt; predicate) {
          $dialog(TEST_DIALOG, ScTestDialog.class, predicate);
      }
 
      ....
 
      // In a test method.
      // The action within dialog is inside the predicate method.
      testPage.inPage(p -> {
          p.openSanityTestDialogButton().click();
          p.inSanityTestDialog(dialog -> {
              // Before it continues here, it waits for the dialog to be displayed. The behavior of the wait can be
              // changed by overriding waitForDisplayedDialog() method.
 
              // Any action in dialog goes in here.
              dialog.textbox.enterText("Test text in dialog");
 
              dialog.closeButton.click();
          });
          // At the close, it automatically wait for the dialog to be hidden. The behavior of the wait can be changed
          // by overriding waitForHiddenDialog() method.
      });
 

```