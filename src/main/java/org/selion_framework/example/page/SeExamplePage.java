package org.selion_framework.example.page;

import org.selion_framework.example.component.SeExampleFrameContent;
import org.selion_framework.example.component.SeExampleLongListEntryComponent;
import org.selion_framework.example.component.SeExampleTableRow;
import org.selion_framework.example.component.dialog.SeExampleDialog;
import org.selion_framework.example.component.dialog.SeExampleModalDialog;
import org.selion_framework.lib.*;

public class SeExamplePage extends SePage {
    private static final SeLocatorNode EXAMPLE_TEXTBOX = _xpath.descendant("input", _id().is("example-textbox"));
    private static final SeLocatorNode EXAMPLE_TEXTAREA_TEXTBOX = _xpath.descendant("textarea", _id().is("example-textarea"));
    private static final SeLocatorNode EXAMPLE_DATE_TEXTBOX = _xpath.descendant("input", _id().is("example-date-textbox"));
    private static final SeLocatorNode EXAMPLE_DROPDOWN = _xpath.descendant("select", _id().is("example-dropdown"));
    private static final SeLocatorNode EXAMPLE_MULTI_SELECT = _xpath.descendant("select", _id().is("example-multi-select"));
    private static final SeLocatorNode EXAMPLE_CHECKBOX = _xpath.descendant("input", _id().is("example-checkbox"));
    private static final SeLocatorNode EXAMPLE_RADIO_BUTTONS = _xpath.descendant("input", _name().is("example-radio-buttons"));
    private static final SeLocatorNode EXAMPLE_INPUT_BUTTON = _xpath.descendant("input", _id().is("example-input-button"));
    private static final SeLocatorNode EXAMPLE_INPUT_BUTTON_INDICATOR_TEXT = _xpath.descendant("span", _id().is("example-input-button-indicator"));
    private static final SeLocatorNode EXAMPLE_BUTTON = _xpath.descendant("button", _id().is("example-button"));
    private static final SeLocatorNode EXAMPLE_BUTTON_INDICATOR_TEXT = _xpath.descendant("span", _id().is("example-button-indicator"));
    private static final SeLocatorNode EXAMPLE_IFRAME = _xpath.descendant("iframe", _id().is("example-iframe"));
    private static final SeLocatorNode EXAMPLE_TABLE_ROWS = _xpath.descendant("table", _id().is("example-table")).descendant("tr", _cssClasses("data"));
    private static final SeLocatorNode OPEN_EXTERNAL_WINDOW_LINK = _xpath.descendant("a", _id().is("example-external-window-link"));
    private static final SeLocatorNode EXAMPLE_LONG_COMPONENT_LIST = _xpath.descendant("div", _id().is("long-component-list")).child("div", _cssClasses("long-component-list-entry"));
    private static final SeLocatorNode EXAMPLE_CSV_DOWNLOAD_LINK = _xpath.descendant("a", _id().is("example-csv-download-link"));

    private static final SeLocatorNode OPEN_EXAMPLE_DIALOG_BUTTON = _xpath.descendant("button", _id().is("open-example-dialog-button"));
    private static final SeLocatorNode OPEN_EXAMPLE_MODAL_DIALOG_BUTTON = _xpath.descendant("button", _id().is("open-example-modal-dialog-button"));

    private static final SeLocatorNode EXAMPLE_DIALOG = _xpath.descendant("div", _id().is("example-dialog"));
    private static final SeLocatorNode EXAMPLE_MODAL_DIALOG = _xpath.descendant("dialog", _id().is("example-modal-dialog"));

    @Override
    protected void additionalWait() {
        waitForComponent(exampleTextbox);
    }

    public final SeTextbox exampleTextbox = $component(EXAMPLE_TEXTBOX, SeTextbox.class);
    public final SeTextbox exampleTextarea = $component(EXAMPLE_TEXTAREA_TEXTBOX, SeTextbox.class);
    public final SeDateTextbox exampleDateTextbox = $component(EXAMPLE_DATE_TEXTBOX, SeDateTextbox.class);
    public final SeDropdown exampleDropdown = $component(EXAMPLE_DROPDOWN, SeDropdown.class);
    public final SeMultiSelect exampleMultiSelect = $component(EXAMPLE_MULTI_SELECT, SeMultiSelect.class);
    public final SeCheckbox exampleCheckbox = $component(EXAMPLE_CHECKBOX, SeCheckbox.class);
    public final SeRadioButtonGroup<SeRadioButton> exampleRadioButtons = $radioButtons(EXAMPLE_RADIO_BUTTONS);
    public final SeButton exampleInputButton = $component(EXAMPLE_INPUT_BUTTON, SeButton.class);
    public final SeGenericComponent exampleInputButtonIndicatorText = $component(EXAMPLE_INPUT_BUTTON_INDICATOR_TEXT, SeGenericComponent.class);
    public final SeButton exampleButton = $component(EXAMPLE_BUTTON, SeButton.class);
    public final SeGenericComponent exampleButtonIndicatorText = $component(EXAMPLE_BUTTON_INDICATOR_TEXT, SeGenericComponent.class);
    public final SeButton openExampleDialogButton = $component(OPEN_EXAMPLE_DIALOG_BUTTON, SeButton.class);
    public final SeButton openExampleModalDialogButton = $component(OPEN_EXAMPLE_MODAL_DIALOG_BUTTON, SeButton.class);
    public final SeComponentCollection<SeExampleTableRow> exampleTableRows = $$components(EXAMPLE_TABLE_ROWS, SeExampleTableRow.class);
    public final SeLink openExternalWindowLink = $component(OPEN_EXTERNAL_WINDOW_LINK, SeLink.class);
    public final SeComponentCollection<SeExampleLongListEntryComponent> longComponentEntries = $$components(EXAMPLE_LONG_COMPONENT_LIST, SeExampleLongListEntryComponent.class);
    public final SeLink exampleCsvDownloadLink = $component(EXAMPLE_CSV_DOWNLOAD_LINK, SeLink.class);

    public void inExampleInnerFrame(SeFrameAction<SeExampleFrameContent> predicate) {
        $frame(EXAMPLE_IFRAME, SeExampleFrameContent.class, predicate);
    }

    public void inExampleDialog(SeDialogAction<SeExampleDialog> predicate) {
        $dialog(EXAMPLE_DIALOG, SeExampleDialog.class, predicate);
    }

    public void inExampleModalDialog(SeDialogAction<SeExampleModalDialog> predicate) {
        $dialog(EXAMPLE_MODAL_DIALOG, SeExampleModalDialog.class, predicate);
    }

//
//    public Textbox exampleTextbox() {
//        return $component(EXAMPLE_TEXTBOX, Textbox.class);
//    }
//
//    public Textbox exampleTextarea() {
//        return $component(EXAMPLE_TEXTAREA_TEXTBOX, Textbox.class);
//    }
//
//    public DateTextbox exampleDateTextbox() {
//        return $component(EXAMPLE_DATE_TEXTBOX, DateTextbox.class);
//    }
//
//    public DateTextbox exampleMaskedDateTextbox() {
//        return $component(EXAMPLE_MASKED_DATE_TEXTBOX, DateTextbox.class);
//    }
//
//    public Dropdown exampleDropdown() {
//        return $component(EXAMPLE_DROPDOWN, Dropdown.class);
//    }
//
//    public MultiSelect exampleMultiSelect() {
//        return $component(EXAMPLE_MULTI_SELECT, MultiSelect.class);
//    }
//
//    public Checkbox exampleCheckbox() {
//        return $component(EXAMPLE_CHECKBOX, Checkbox.class);
//    }
}
