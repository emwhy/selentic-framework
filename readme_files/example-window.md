[< Return to README](../README.md)
## Example: Handling Multiple Browser Windows

Multiple browser windows is handled using **ScWindow** class, which is accessible by a page object (extending from **ScPage**). 
Using lambda function, it neatly isolates any action that happens in another browser window while handling switching and closing extra browser windows automatically.


```java

    testPage.inPage(p -> {
        // Open a new window.
        p.openExternalWindowLink().click();

        // New window has the control inside predicate.
        p.inWindow(testExternalPage, p1 -> {
            // Open another new window. Multiple external windows can be nested.
            p1.openExternalWindowLink().click();

            // Now the new window gets the control.
            p1.inWindow(testExternalPage, (p2, controller) -> {
    
            // Controller allows temporary switching control to other windows
            // without closing them, then return the control back to the original
            // window.
            controller.inOtherWindow(testPage, 0, p3 -> {
                // Ensure that the focused window is the root one.
                Assert.assertEquals(p3.testTableRows().entry("SanityTest 1").serialNumberText.text(), "#TDD987");
                Assert.assertEquals(p3.testTableRows().entry("SanityTest 2").serialNumberText.text(), "#AEV974");
                Assert.assertEquals(p3.testTableRows().entry("SanityTest 3").serialNumberText.text(), "#CCA106");});
                // At the end of the predicate, the control is automatically returned to the calling window.
    
                // Assertion on the external window after the control is returned from
                // "the other window".
                Assert.assertEquals(p2.testExternalTextbox().text(), "external textbox text");
    
                Assert.assertEquals(p2.testExternalTableRows().size(), 3);
    
                Assert.assertEquals(p2.testExternalTableRows().at(0).text(), "External SanityTest 1");
                Assert.assertEquals(p2.testExternalTableRows().at(1).text(), "External SanityTest 2");
                Assert.assertEquals(p2.testExternalTableRows().at(2).text(), "External SanityTest 3");
    
                Assert.assertEquals(p2.testExternalTableRows().entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
                Assert.assertEquals(p2.testExternalTableRows().entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
                Assert.assertEquals(p2.testExternalTableRows().entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
    
                // Close the current window.
                p2.closeCurrentWindowButton().click();
            });
            // Gives the control back to the opening window.
     
            Assert.assertEquals(p1.testExternalTextbox().text(), "external textbox text");
    
            Assert.assertEquals(p1.testExternalTableRows().size(), 3);
    
            Assert.assertEquals(p1.testExternalTableRows().at(0).text(), "External SanityTest 1");
            Assert.assertEquals(p1.testExternalTableRows().at(1).text(), "External SanityTest 2");
            Assert.assertEquals(p1.testExternalTableRows().at(2).text(), "External SanityTest 3");
    
            Assert.assertEquals(p1.testExternalTableRows().entry("External SanityTest 1").serialNumberText.text(), "#EX-TDD987");
            Assert.assertEquals(p1.testExternalTableRows().entry("External SanityTest 2").serialNumberText.text(), "#EX-AEV974");
            Assert.assertEquals(p1.testExternalTableRows().entry("External SanityTest 3").serialNumberText.text(), "#EX-CCA106");
        });
         // The original window gets the control back.
    });
    
```
