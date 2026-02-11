package org.selion_framework.sanity.page;

import org.selion_framework.lib.*;
import org.selion_framework.sanity.component.SeSanityTestFrameContent;
import org.selion_framework.sanity.component.SeSanityTestLongListEntryComponent;
import org.selion_framework.sanity.component.SeSanityTestTableRow;
import org.selion_framework.sanity.component.dialog.SeSanityTestDialog;
import org.selion_framework.sanity.component.dialog.SeSanityTestModalDialog;

public class SeSanityTestPage extends SePage {
    private static final SeLocatorNode SANITYTEST_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-textbox"));
    private static final SeLocatorNode SANITYTEST_TEXTAREA_TEXTBOX = _xpath.descendant("textarea", _id().is("sanitytest-textarea"));
    private static final SeLocatorNode SANITYTEST_DATE_TEXTBOX = _xpath.descendant("input", _id().is("sanitytest-date-textbox"));
    private static final SeLocatorNode SANITYTEST_DROPDOWN = _xpath.descendant("select", _id().is("sanitytest-dropdown"));
    private static final SeLocatorNode SANITYTEST_MULTI_SELECT = _xpath.descendant("select", _id().is("sanitytest-multi-select"));
    private static final SeLocatorNode SANITYTEST_CHECKBOX = _xpath.descendant("input", _id().is("sanitytest-checkbox"));
    private static final SeLocatorNode SANITYTEST_RADIO_BUTTONS = _xpath.descendant("input", _name().is("sanitytest-radio-buttons"));
    private static final SeLocatorNode SANITYTEST_INPUT_BUTTON = _xpath.descendant("input", _id().is("sanitytest-input-button"));
    private static final SeLocatorNode SANITYTEST_INPUT_BUTTON_INDICATOR_TEXT = _xpath.descendant("span", _id().is("sanitytest-input-button-indicator"));
    private static final SeLocatorNode SANITYTEST_BUTTON = _xpath.descendant("button", _id().is("sanitytest-button"));
    private static final SeLocatorNode SANITYTEST_BUTTON_INDICATOR_TEXT = _xpath.descendant("span", _id().is("sanitytest-button-indicator"));
    private static final SeLocatorNode SANITYTEST_IFRAME = _xpath.descendant("iframe", _id().is("sanitytest-iframe"));
    private static final SeLocatorNode SANITYTEST_TABLE_ROWS = _xpath.descendant("table", _id().is("sanitytest-table")).descendant("tr", _cssClasses("data"));
    private static final SeLocatorNode OPEN_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("sanitytest-external-window-link"));
    private static final SeLocatorNode SANITYTEST_LONG_COMPONENT_LIST = _xpath.descendant("div", _id().is("long-component-list")).child("div", _cssClasses("long-component-list-entry"));
    private static final SeLocatorNode SANITYTEST_CSV_DOWNLOAD_LINK = _xpath.descendant("a", _id().is("sanitytest-csv-download-link"));

    private static final SeLocatorNode OPEN_SANITYTEST_DIALOG_BUTTON = _xpath.descendant("button", _id().is("open-sanitytest-dialog-button"));
    private static final SeLocatorNode OPEN_SANITYTEST_MODAL_DIALOG_BUTTON = _xpath.descendant("button", _id().is("open-sanitytest-modal-dialog-button"));

    private static final SeLocatorNode SANITYTEST_DIALOG = _xpath.descendant("div", _id().is("sanitytest-dialog"));
    private static final SeLocatorNode SANITYTEST_MODAL_DIALOG = _xpath.descendant("dialog", _id().is("sanitytest-modal-dialog"));

    @Override
    protected void additionalWait() {
        waitForComponent(sanitytestTextbox);
    }

    public final SeTextbox sanitytestTextbox = $component(SANITYTEST_TEXTBOX, SeTextbox.class);
    public final SeTextbox sanitytestTextarea = $component(SANITYTEST_TEXTAREA_TEXTBOX, SeTextbox.class);
    public final SeDateTextbox sanitytestDateTextbox = $component(SANITYTEST_DATE_TEXTBOX, SeDateTextbox.class);
    public final SeDropdown sanitytestDropdown = $component(SANITYTEST_DROPDOWN, SeDropdown.class);
    public final SeMultiSelect sanitytestMultiSelect = $component(SANITYTEST_MULTI_SELECT, SeMultiSelect.class);
    public final SeCheckbox sanitytestCheckbox = $component(SANITYTEST_CHECKBOX, SeCheckbox.class);
    public final SeRadioButtonGroup<SeRadioButton> sanitytestRadioButtons = $radioButtons(SANITYTEST_RADIO_BUTTONS);
    public final SeButton sanitytestInputButton = $component(SANITYTEST_INPUT_BUTTON, SeButton.class);
    public final SeGenericComponent sanitytestInputButtonIndicatorText = $component(SANITYTEST_INPUT_BUTTON_INDICATOR_TEXT, SeGenericComponent.class);
    public final SeButton sanitytestButton = $component(SANITYTEST_BUTTON, SeButton.class);
    public final SeGenericComponent sanitytestButtonIndicatorText = $component(SANITYTEST_BUTTON_INDICATOR_TEXT, SeGenericComponent.class);
    public final SeButton openSanityTestDialogButton = $component(OPEN_SANITYTEST_DIALOG_BUTTON, SeButton.class);
    public final SeButton openSanityTestModalDialogButton = $component(OPEN_SANITYTEST_MODAL_DIALOG_BUTTON, SeButton.class);
    public final SeComponentCollection<SeSanityTestTableRow> sanitytestTableRows = $$components(SANITYTEST_TABLE_ROWS, SeSanityTestTableRow.class);
    public final SeLink openExternalWindowLink = $component(OPEN_EXTERNAL_WINDOW_LINK, SeLink.class);
    public final SeComponentCollection<SeSanityTestLongListEntryComponent> longComponentEntries = $$components(SANITYTEST_LONG_COMPONENT_LIST, SeSanityTestLongListEntryComponent.class);
    public final SeLink sanitytestCsvDownloadLink = $component(SANITYTEST_CSV_DOWNLOAD_LINK, SeLink.class);

    public void inSanityTestInnerFrame(SeFrameAction<SeSanityTestFrameContent> predicate) {
        $frame(SANITYTEST_IFRAME, SeSanityTestFrameContent.class, predicate);
    }

    public void inSanityTestDialog(SeDialogAction<SeSanityTestDialog> predicate) {
        $dialog(SANITYTEST_DIALOG, SeSanityTestDialog.class, predicate);
    }

    public void inSanityTestModalDialog(SeDialogAction<SeSanityTestModalDialog> predicate) {
        $dialog(SANITYTEST_MODAL_DIALOG, SeSanityTestModalDialog.class, predicate);
    }

}
