package org.selion_framework.sanity.page;

import org.selion_framework.lib.*;
import org.selion_framework.sanity.component.SnSanityTestFrameContent;
import org.selion_framework.sanity.component.SnSanityTestLongListEntryComponent;
import org.selion_framework.sanity.component.SnSanityTestTableRow;
import org.selion_framework.sanity.component.dialog.SnSanityTestDialog;
import org.selion_framework.sanity.component.dialog.SnSanityTestModalDialog;

public class SnSanityTestXPathPage extends SnPage {
    private static final SnXPath SANITYTEST_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-textbox"));
    private static final SnXPath SANITYTEST_TEXTAREA_TEXTBOX = _xpath.descendant("textarea", _id().is("sanitytest-textarea"));
    private static final SnXPath SANITYTEST_DATE_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-date-textbox"));
    private static final SnXPath SANITYTEST_DROPDOWN = _xpath.descendant("select", _id().is("sanitytest-dropdown"));
    private static final SnXPath SANITYTEST_MULTI_SELECT = _xpath.descendant("select", _id().is("sanitytest-multi-select"));
    private static final SnXPath SANITYTEST_CHECKBOX = _xpath.descendant("input", _id().is("sanitytest-checkbox"));
    private static final SnXPath SANITYTEST_RADIO_BUTTONS = _xpath.descendant("input", _name().is("sanitytest-radio-buttons"));
    private static final SnXPath SANITYTEST_INPUT_BUTTON = _xpath.descendant("input", _id().is("sanitytest-input-button"));
    private static final SnXPath SANITYTEST_INPUT_BUTTON_INDICATOR_TEXT = _xpath.descendant("span", _id().is("sanitytest-input-button-indicator"));
    private static final SnXPath SANITYTEST_BUTTON = _xpath.descendant("button", _id().is("sanitytest-button"));
    private static final SnXPath SANITYTEST_BUTTON_INDICATOR_TEXT = _xpath.descendant("span", _id().is("sanitytest-button-indicator"));
    private static final SnXPath SANITYTEST_IFRAME = _xpath.descendant("iframe", _id().is("sanitytest-iframe"));
    private static final SnXPath SANITYTEST_TABLE_ROWS = _xpath.descendant("table", _id().is("sanitytest-table")).descendant("tr", _cssClasses("data"));
    private static final SnXPath OPEN_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("sanitytest-external-window-link"));
    private static final SnXPath SANITYTEST_LONG_COMPONENT_LIST = _xpath.descendant("div", _id().is("long-component-list")).child("div", _cssClasses("long-component-list-entry"));
    private static final SnXPath SANITYTEST_CSV_DOWNLOAD_LINK = _xpath.descendant("a", _id().is("sanitytest-csv-download-link"));
    private static final SnXPath SANITYTEST_OWN_TEXT = _xpath.descendant("div", _id().is("sanitytest-own-text"));

    private static final SnXPath OPEN_SANITYTEST_DIALOG_BUTTON = _xpath.descendant("button", _id().is("open-sanitytest-dialog-button"));
    private static final SnXPath OPEN_SANITYTEST_MODAL_DIALOG_BUTTON = _xpath.descendant("button", _id().is("open-sanitytest-modal-dialog-button"));

    private static final SnXPath SANITYTEST_DIALOG = _xpath.descendant("div", _id().is("sanitytest-dialog"));
    private static final SnXPath SANITYTEST_MODAL_DIALOG = _xpath.descendant("dialog", _id().is("sanitytest-modal-dialog"));

    @Override
    protected void additionalWait() {
        waitForComponent(sanitytestTextbox);
    }

    public final SnTextbox sanitytestTextbox = $component(SANITYTEST_TEXTBOX, SnTextbox.class);
    public final SnTextbox sanitytestTextarea = $component(SANITYTEST_TEXTAREA_TEXTBOX, SnTextbox.class);
    public final SnDateTextbox sanitytestDateTextbox = $component(SANITYTEST_DATE_TEXTBOX, SnDateTextbox.class);
    public final SnDropdown sanitytestDropdown = $component(SANITYTEST_DROPDOWN, SnDropdown.class);
    public final SnMultiSelect sanitytestMultiSelect = $component(SANITYTEST_MULTI_SELECT, SnMultiSelect.class);
    public final SnCheckbox sanitytestCheckbox = $component(SANITYTEST_CHECKBOX, SnCheckbox.class);
    public final SnRadioButtonGroup<SnRadioButton> sanitytestRadioButtons = $radioButtons(SANITYTEST_RADIO_BUTTONS);
    public final SnButton sanitytestInputButton = $component(SANITYTEST_INPUT_BUTTON, SnButton.class);
    public final SnGenericComponent sanitytestInputButtonIndicatorText = $component(SANITYTEST_INPUT_BUTTON_INDICATOR_TEXT, SnGenericComponent.class);
    public final SnButton sanitytestButton = $component(SANITYTEST_BUTTON, SnButton.class);
    public final SnGenericComponent sanitytestButtonIndicatorText = $component(SANITYTEST_BUTTON_INDICATOR_TEXT, SnGenericComponent.class);
    public final SnButton openSanityTestDialogButton = $component(OPEN_SANITYTEST_DIALOG_BUTTON, SnButton.class);
    public final SnButton openSanityTestModalDialogButton = $component(OPEN_SANITYTEST_MODAL_DIALOG_BUTTON, SnButton.class);
    public final SnComponentCollection<SnSanityTestTableRow> sanitytestTableRows = $$components(SANITYTEST_TABLE_ROWS, SnSanityTestTableRow.class);
    public final SnLink openExternalWindowLink = $component(OPEN_EXTERNAL_WINDOW_LINK, SnLink.class);
    public final SnComponentCollection<SnSanityTestLongListEntryComponent> longComponentEntries = $$components(SANITYTEST_LONG_COMPONENT_LIST, SnSanityTestLongListEntryComponent.class);
    public final SnLink sanitytestCsvDownloadLink = $component(SANITYTEST_CSV_DOWNLOAD_LINK, SnLink.class);
    public final SnGenericComponent sanitytestOwnText = $genericComponent(SANITYTEST_OWN_TEXT);

    public void inSanityTestInnerFrame(SnFrameAction<SnSanityTestFrameContent> predicate) {
        $frame(SANITYTEST_IFRAME, SnSanityTestFrameContent.class, predicate);
    }

    public void inSanityTestDialog(SnDialogAction<SnSanityTestDialog> predicate) {
        $dialog(SANITYTEST_DIALOG, SnSanityTestDialog.class, predicate);
    }

    public void inSanityTestModalDialog(SnDialogAction<SnSanityTestModalDialog> predicate) {
        $dialog(SANITYTEST_MODAL_DIALOG, SnSanityTestModalDialog.class, predicate);
    }

}
