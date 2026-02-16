package org.selion_framework.sanity.page;

import org.selion_framework.lib.*;
import org.selion_framework.sanity.component.*;
import org.selion_framework.sanity.component.dialog.SnTestDialog;
import org.selion_framework.sanity.component.dialog.SnTestModalDialog;

public class SnUiComponentPage extends SnPage {
    private static final SnCssSelector TEST_TEXTBOX = _cssSelector.descendant(_id("test-textbox"));
    private static final SnCssSelector TEST_TEXTAREA_TEXTBOX = _cssSelector.descendant(_id("test-textarea"));
    private static final SnCssSelector TEST_DATE_TEXTBOX = _cssSelector.descendant(_id("test-date-textbox"));
    private static final SnCssSelector TEST_DROPDOWN = _cssSelector.descendant(_id("test-dropdown"));
    private static final SnCssSelector TEST_MULTI_SELECT = _cssSelector.descendant(_id("test-multi-select"));
    private static final SnCssSelector TEST_CHECKBOX = _cssSelector.descendant(_id("test-checkbox"));
    private static final SnCssSelector TEST_RADIO_BUTTONS = _cssSelector.descendant(_name().is("test-radio-buttons"));
    private static final SnCssSelector TEST_INPUT_BUTTON = _cssSelector.descendant(_id("test-input-button"));
    private static final SnCssSelector TEST_INPUT_BUTTON_INDICATOR_TEXT = _cssSelector.descendant(_id("test-input-button-indicator"));
    private static final SnCssSelector TEST_BUTTON = _cssSelector.descendant(_id("test-button"));
    private static final SnCssSelector TEST_BUTTON_INDICATOR_TEXT = _cssSelector.descendant(_id("test-button-indicator"));
    private static final SnCssSelector TEST_IFRAME = _cssSelector.descendant(_id("test-iframe"));
    private static final SnCssSelector TEST_TABLE_ROWS = _cssSelector.descendant(_id("test-table")).descendant(_tag("tr"), _cssClasses("data"));
    private static final SnCssSelector OPEN_EXTERNAL_WINDOW_LINK = _cssSelector.descendant(_id("test-external-window-link"));
    private static final SnCssSelector TEST_LONG_COMPONENT_LIST = _cssSelector.descendant(_id("long-component-list")).child(_tag("div"), _cssClasses("long-component-list-entry"));
    private static final SnCssSelector TEST_CSV_DOWNLOAD_LINK = _cssSelector.descendant(_id("test-csv-download-link"));
    private static final SnCssSelector TEST_OWN_TEXT = _cssSelector.descendant(_id("test-own-text"));

    private static final SnCssSelector OPEN_TEST_DIALOG_BUTTON = _cssSelector.descendant(_id("open-test-dialog-button"));
    private static final SnCssSelector OPEN_TEST_MODAL_DIALOG_BUTTON = _cssSelector.descendant(_id("open-test-modal-dialog-button"));

    private static final SnCssSelector TEST_DIALOG = _cssSelector.descendant(_id("test-dialog"));
    private static final SnCssSelector TEST_MODAL_DIALOG = _cssSelector.descendant(_id("test-modal-dialog"));

    private static final SnCssSelector TEST_NON_EXISTING_COMPONENT = _cssSelector.descendant(_id("does-not-exist"));

    private static final SnCssSelector ANIMATED_BOX = _cssSelector.descendant(_id("animated-box"));
    private static final SnCssSelector ANIMATE_MOVE_BUTTON = _cssSelector.descendant(_id("animate-move-button"));
    private static final SnCssSelector ANIMATE_ROTATE_BUTTON = _cssSelector.descendant(_id("animate-rotate-button"));
    private static final SnCssSelector ANIMATE_OPACITY_BUTTON = _cssSelector.descendant(_id("animate-opacity-button"));
    private static final SnCssSelector ANIMATE_SIZE_BUTTON = _cssSelector.descendant(_id("animate-size-button"));
    private static final SnCssSelector ANIMATE_LONG_BUTTON = _cssSelector.descendant(_id("animate-long-button"));

    private static final SnCssSelector SHOW_ALERT_BUTTON = _cssSelector.descendant(_id("show-alert"));
    private static final SnCssSelector SHOW_CONFIRM_BUTTON = _cssSelector.descendant(_id("show-confirm"));
    private static final SnCssSelector SHOW_PROMPT_BUTTON = _cssSelector.descendant(_id("show-prompt"));

    private static final SnCssSelector OPEN_DRAG_AND_DROP_PAGE_LINK = _cssSelector.descendant(_id("open-drag-and-drop-page"));

    @Override
    protected void waitForDisplayed() {
        waitForComponent(testTextbox);
    }

    public final SnTextbox testTextbox = $component(TEST_TEXTBOX, SnTextbox.class);
    public final SnTextbox testTextarea = $component(TEST_TEXTAREA_TEXTBOX, SnTextbox.class);
    public final SnDateTextbox testDateTextbox = $component(TEST_DATE_TEXTBOX, SnDateTextbox.class);
    public final SnDropdown testDropdown = $component(TEST_DROPDOWN, SnDropdown.class);
    public final SnMultiSelect testMultiSelect = $component(TEST_MULTI_SELECT, SnMultiSelect.class);
    public final SnCheckbox testCheckbox = $component(TEST_CHECKBOX, SnCheckbox.class);
    public final SnRadioButtonGroup<SnRadioButton> testRadioButtons = $radioButtons(TEST_RADIO_BUTTONS);
    public final SnButton testInputButton = $component(TEST_INPUT_BUTTON, SnButton.class);
    public final SnGenericComponent testInputButtonIndicatorText = $component(TEST_INPUT_BUTTON_INDICATOR_TEXT, SnGenericComponent.class);
    public final SnButton testButton = $component(TEST_BUTTON, SnButton.class);
    public final SnGenericComponent testButtonIndicatorText = $component(TEST_BUTTON_INDICATOR_TEXT, SnGenericComponent.class);
    public final SnButton openSanityTestDialogButton = $component(OPEN_TEST_DIALOG_BUTTON, SnButton.class);
    public final SnButton openSanityTestModalDialogButton = $component(OPEN_TEST_MODAL_DIALOG_BUTTON, SnButton.class);
    public final SnComponentCollection<SnTestTableRow> testTableRows = $$components(TEST_TABLE_ROWS, SnTestTableRow.class);
    public final SnLink openExternalWindowLink = $component(OPEN_EXTERNAL_WINDOW_LINK, SnLink.class);
    public final SnComponentCollection<SnLongListEntryComponent> longComponentEntries = $$components(TEST_LONG_COMPONENT_LIST, SnLongListEntryComponent.class);
    public final SnLink testCsvDownloadLink = $component(TEST_CSV_DOWNLOAD_LINK, SnLink.class);
    public final SnExposedOwnText testOwnText = $component(TEST_OWN_TEXT, SnExposedOwnText.class);
    public final SnLink testNonExistingLink = $link(TEST_NON_EXISTING_COMPONENT);

    public final SnAnimatedBox animatedBox = $component(ANIMATED_BOX, SnAnimatedBox.class);
    public final SnButton animateMoveButton = $button(ANIMATE_MOVE_BUTTON);
    public final SnButton animateRotateButton = $button(ANIMATE_ROTATE_BUTTON);
    public final SnButton animateOpacityButton = $button(ANIMATE_OPACITY_BUTTON);
    public final SnButton animateSizeButton = $button(ANIMATE_SIZE_BUTTON);
    public final SnButton animateLongButton = $button(ANIMATE_LONG_BUTTON);

    public final SnButton showAlertButton = $button(SHOW_ALERT_BUTTON);
    public final SnButton showConfirmButton = $button(SHOW_CONFIRM_BUTTON);
    public final SnButton showPromptButton = $button(SHOW_PROMPT_BUTTON);

    public final SnLink openDragAndDropPageLink = $link(OPEN_DRAG_AND_DROP_PAGE_LINK);

    public void inSanityTestInnerFrame(SnFrameAction<SnSanityTestFrameContent> predicate) {
        $frame(TEST_IFRAME, SnSanityTestFrameContent.class, predicate);
    }

    public void inSanityTestDialog(SnDialogAction<SnTestDialog> predicate) {
        $dialog(TEST_DIALOG, SnTestDialog.class, predicate);
    }

    public void inSanityTestModalDialog(SnDialogAction<SnTestModalDialog> predicate) {
        $dialog(TEST_MODAL_DIALOG, SnTestModalDialog.class, predicate);
    }

}
