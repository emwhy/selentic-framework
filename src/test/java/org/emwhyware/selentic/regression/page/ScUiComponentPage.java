package org.emwhyware.selentic.regression.page;

import org.emwhyware.selentic.lib.*;
import org.emwhyware.selentic.regression.component.*;
import org.emwhyware.selentic.regression.component.dialog.ScTestDialog;
import org.emwhyware.selentic.regression.component.dialog.ScTestModalDialog;

public class ScUiComponentPage extends ScPage {
    private static final ScCssSelector TEST_TEXTBOX = _cssSelector.descendant(_id("test-textbox"));
    private static final ScCssSelector TEST_TEXTAREA_TEXTBOX = _cssSelector.descendant(_id("test-textarea"));
    private static final ScCssSelector TEST_DATE_TEXTBOX = _cssSelector.descendant(_id("test-date-textbox"));
    private static final ScCssSelector TEST_DROPDOWN = _cssSelector.descendant(_id("test-dropdown"));
    private static final ScCssSelector TEST_MULTI_SELECT = _cssSelector.descendant(_id("test-multi-select"));
    private static final ScCssSelector TEST_CHECKBOX = _cssSelector.descendant(_id("test-checkbox"));
    private static final ScCssSelector TEST_RADIO_BUTTONS = _cssSelector.descendant(_name().is("test-radio-buttons"));
    private static final ScCssSelector TEST_INPUT_BUTTON = _cssSelector.descendant(_id("test-input-button"));
    private static final ScCssSelector TEST_INPUT_BUTTON_INDICATOR_TEXT = _cssSelector.descendant(_id("test-input-button-indicator"));
    private static final ScCssSelector TEST_BUTTON = _cssSelector.descendant(_id("test-button"));
    private static final ScCssSelector TEST_BUTTON_INDICATOR_TEXT = _cssSelector.descendant(_id("test-button-indicator"));
    private static final ScCssSelector TEST_IFRAME = _cssSelector.descendant(_id("test-iframe"));
    private static final ScCssSelector TEST_TABLE_ROWS = _cssSelector.descendant(_id("test-table")).descendant(_tag("tr"), _cssClasses("data"));
    private static final ScCssSelector OPEN_EXTERNAL_WINDOW_LINK = _cssSelector.descendant(_id("test-external-window-link"));
    private static final ScCssSelector TEST_LONG_COMPONENT_LIST = _cssSelector.descendant(_id("long-component-list")).child(_tag("div"), _cssClasses("long-component-list-entry"));
    private static final ScCssSelector TEST_CSV_DOWNLOAD_LINK = _cssSelector.descendant(_id("test-csv-download-link"));
    private static final ScCssSelector TEST_OWN_TEXT = _cssSelector.descendant(_id("test-own-text"));

    private static final ScCssSelector OPEN_TEST_DIALOG_BUTTON = _cssSelector.descendant(_id("open-test-dialog-button"));
    private static final ScCssSelector OPEN_TEST_MODAL_DIALOG_BUTTON = _cssSelector.descendant(_id("open-test-modal-dialog-button"));

    private static final ScCssSelector TEST_DIALOG = _cssSelector.descendant(_id("test-dialog"));
    private static final ScCssSelector TEST_MODAL_DIALOG = _cssSelector.descendant(_id("test-modal-dialog"));

    private static final ScCssSelector TEST_NON_EXISTING_COMPONENT = _cssSelector.descendant(_id("does-not-exist"));

    private static final ScCssSelector ANIMATED_BOX = _cssSelector.descendant(_id("animated-box"));
    private static final ScCssSelector ANIMATE_MOVE_BUTTON = _cssSelector.descendant(_id("animate-move-button"));
    private static final ScCssSelector ANIMATE_ROTATE_BUTTON = _cssSelector.descendant(_id("animate-rotate-button"));
    private static final ScCssSelector ANIMATE_OPACITY_BUTTON = _cssSelector.descendant(_id("animate-opacity-button"));
    private static final ScCssSelector ANIMATE_SIZE_BUTTON = _cssSelector.descendant(_id("animate-size-button"));
    private static final ScCssSelector ANIMATE_LONG_BUTTON = _cssSelector.descendant(_id("animate-long-button"));

    private static final ScCssSelector SHOW_ALERT_BUTTON = _cssSelector.descendant(_id("show-alert"));
    private static final ScCssSelector SHOW_CONFIRM_BUTTON = _cssSelector.descendant(_id("show-confirm"));
    private static final ScCssSelector SHOW_PROMPT_BUTTON = _cssSelector.descendant(_id("show-prompt"));

    private static final ScCssSelector OPEN_DRAG_AND_DROP_PAGE_LINK = _cssSelector.descendant(_id("open-drag-and-drop-page"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(testTextbox());
    }

    public ScTextbox testTextbox() {
        return $component(TEST_TEXTBOX, ScTextbox.class);
    }

    public ScTextbox testTextarea() {
        return $component(TEST_TEXTAREA_TEXTBOX, ScTextbox.class);
    }

    public ScDateTextbox testDateTextbox() {
        return $component(TEST_DATE_TEXTBOX, ScDateTextbox.class);
    }

    public ScDropdown testDropdown() {
        return $component(TEST_DROPDOWN, ScDropdown.class);
    }

    public ScMultiSelect testMultiSelect() {
        return $component(TEST_MULTI_SELECT, ScMultiSelect.class);
    }

    public ScCheckbox testCheckbox() {
        return $component(TEST_CHECKBOX, ScCheckbox.class);
    }

    public ScRadioButtonGroup<ScRadioButton> testRadioButtons() {
        return $radioButtons(TEST_RADIO_BUTTONS);
    }

    public ScButton testInputButton() {
        return $component(TEST_INPUT_BUTTON, ScButton.class);
    }

    public ScGenericComponent testInputButtonIndicatorText() {
        return $component(TEST_INPUT_BUTTON_INDICATOR_TEXT, ScGenericComponent.class);
    }

    public ScButton testButton() {
        return $component(TEST_BUTTON, ScButton.class);
    }

    public ScGenericComponent testButtonIndicatorText() {
        return $component(TEST_BUTTON_INDICATOR_TEXT, ScGenericComponent.class);
    }

    public ScButton openSanityTestDialogButton() {
        return $component(OPEN_TEST_DIALOG_BUTTON, ScButton.class);
    }

    public ScButton openSanityTestModalDialogButton() {
        return $component(OPEN_TEST_MODAL_DIALOG_BUTTON, ScButton.class);
    }

    public ScComponentCollection<ScTestTableRow> testTableRows() {
        return $$components(TEST_TABLE_ROWS, ScTestTableRow.class);
    }

    public ScLink openExternalWindowLink() {
        return $component(OPEN_EXTERNAL_WINDOW_LINK, ScLink.class);
    }

    public ScComponentCollection<ScLongListEntryComponent> longComponentEntries() {
        return $$components(TEST_LONG_COMPONENT_LIST, ScLongListEntryComponent.class);
    }

    public ScLink testCsvDownloadLink() {
        return $component(TEST_CSV_DOWNLOAD_LINK, ScLink.class);
    }

    public ScExposedOwnText testOwnText() {
        return $component(TEST_OWN_TEXT, ScExposedOwnText.class);
    }

    public ScLink testNonExistingLink() {
        return $link(TEST_NON_EXISTING_COMPONENT);
    }

    public ScAnimatedBox animatedBox() {
        return $component(ANIMATED_BOX, ScAnimatedBox.class);
    }

    public ScButton animateMoveButton() {
        return $button(ANIMATE_MOVE_BUTTON);
    }

    public ScButton animateRotateButton() {
        return $button(ANIMATE_ROTATE_BUTTON);
    }

    public ScButton animateOpacityButton() {
        return $button(ANIMATE_OPACITY_BUTTON);
    }

    public ScButton animateSizeButton() {
        return $button(ANIMATE_SIZE_BUTTON);
    }

    public ScButton animateLongButton() {
        return $button(ANIMATE_LONG_BUTTON);
    }

    public ScButton showAlertButton() {
        return $button(SHOW_ALERT_BUTTON);
    }

    public ScButton showConfirmButton() {
        return $button(SHOW_CONFIRM_BUTTON);
    }

    public ScButton showPromptButton() {
        return $button(SHOW_PROMPT_BUTTON);
    }

    public ScLink openDragAndDropPageLink() {
        return $link(OPEN_DRAG_AND_DROP_PAGE_LINK);
    }


    public void inSanityTestInnerFrame(ScFrameAction<ScSanityTestFrameContent> predicate) {
        $frame(TEST_IFRAME, ScSanityTestFrameContent.class, predicate);
    }

    public void inSanityTestDialog(ScDialogAction<ScTestDialog> predicate) {
        $dialog(TEST_DIALOG, ScTestDialog.class, predicate);
    }

    public void inSanityTestModalDialog(ScDialogAction<ScTestModalDialog> predicate) {
        $dialog(TEST_MODAL_DIALOG, ScTestModalDialog.class, predicate);
    }

}
