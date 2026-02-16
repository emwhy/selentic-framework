package org.selion_framework.sanity.page;

import org.selion_framework.lib.*;
import org.selion_framework.sanity.component.*;
import org.selion_framework.sanity.component.dialog.SnSanityTestDialog;
import org.selion_framework.sanity.component.dialog.SnSanityTestModalDialog;

public class SnSanityTestCssSelectorPage extends SnPage {
    private static final SnCssSelector SANITYTEST_TEXTBOX = _cssSelector.descendant(_id("sanitytest-textbox"));
    private static final SnCssSelector SANITYTEST_TEXTAREA_TEXTBOX = _cssSelector.descendant(_id("sanitytest-textarea"));
    private static final SnCssSelector SANITYTEST_DATE_TEXTBOX = _cssSelector.descendant(_id("sanitytest-date-textbox"));
    private static final SnCssSelector SANITYTEST_DROPDOWN = _cssSelector.descendant(_id("sanitytest-dropdown"));
    private static final SnCssSelector SANITYTEST_MULTI_SELECT = _cssSelector.descendant(_id("sanitytest-multi-select"));
    private static final SnCssSelector SANITYTEST_CHECKBOX = _cssSelector.descendant(_id("sanitytest-checkbox"));
    private static final SnCssSelector SANITYTEST_RADIO_BUTTONS = _cssSelector.descendant(_name().is("sanitytest-radio-buttons"));
    private static final SnCssSelector SANITYTEST_INPUT_BUTTON = _cssSelector.descendant(_id("sanitytest-input-button"));
    private static final SnCssSelector SANITYTEST_INPUT_BUTTON_INDICATOR_TEXT = _cssSelector.descendant(_id("sanitytest-input-button-indicator"));
    private static final SnCssSelector SANITYTEST_BUTTON = _cssSelector.descendant(_id("sanitytest-button"));
    private static final SnCssSelector SANITYTEST_BUTTON_INDICATOR_TEXT = _cssSelector.descendant(_id("sanitytest-button-indicator"));
    private static final SnCssSelector SANITYTEST_IFRAME = _cssSelector.descendant(_id("sanitytest-iframe"));
    private static final SnCssSelector SANITYTEST_TABLE_ROWS = _cssSelector.descendant(_id("sanitytest-table")).descendant(_tag("tr"), _cssClasses("data"));
    private static final SnCssSelector OPEN_EXTERNAL_WINDOW_LINK = _cssSelector.descendant(_id("sanitytest-external-window-link"));
    private static final SnCssSelector SANITYTEST_LONG_COMPONENT_LIST = _cssSelector.descendant(_id("long-component-list")).child(_tag("div"), _cssClasses("long-component-list-entry"));
    private static final SnCssSelector SANITYTEST_CSV_DOWNLOAD_LINK = _cssSelector.descendant(_id("sanitytest-csv-download-link"));
    private static final SnCssSelector SANITYTEST_OWN_TEXT = _cssSelector.descendant(_id("sanitytest-own-text"));

    private static final SnCssSelector OPEN_SANITYTEST_DIALOG_BUTTON = _cssSelector.descendant(_id("open-sanitytest-dialog-button"));
    private static final SnCssSelector OPEN_SANITYTEST_MODAL_DIALOG_BUTTON = _cssSelector.descendant(_id("open-sanitytest-modal-dialog-button"));

    private static final SnCssSelector SANITYTEST_DIALOG = _cssSelector.descendant(_id("sanitytest-dialog"));
    private static final SnCssSelector SANITYTEST_MODAL_DIALOG = _cssSelector.descendant(_id("sanitytest-modal-dialog"));

    private static final SnCssSelector SANITYTEST_NON_EXISTING_COMPONENT = _cssSelector.descendant(_id("does-not-exist"));

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
    public final SnExposedOwnText sanitytestOwnText = $component(SANITYTEST_OWN_TEXT, SnExposedOwnText.class);
    public final SnLink sanitytestNonExistingLink = $link(SANITYTEST_NON_EXISTING_COMPONENT);

    public final SnSanityTestAnimatedBox animatedBox = $component(ANIMATED_BOX, SnSanityTestAnimatedBox.class);
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
        $frame(SANITYTEST_IFRAME, SnSanityTestFrameContent.class, predicate);
    }

    public void inSanityTestDialog(SnDialogAction<SnSanityTestDialog> predicate) {
        $dialog(SANITYTEST_DIALOG, SnSanityTestDialog.class, predicate);
    }

    public void inSanityTestModalDialog(SnDialogAction<SnSanityTestModalDialog> predicate) {
        $dialog(SANITYTEST_MODAL_DIALOG, SnSanityTestModalDialog.class, predicate);
    }

}
