package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.emwhyware.selentic.lib.exception.ScEntryNotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ScMultiSelect extends ScFormComponent {
    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("select");
        rule.attr("multiple").isPresent();
    }

    public void select(@NonNull String... texts) {
        final Select select = new Select(this.scrolledElement());
        final List<String> notFoundTexts = new ArrayList<>();

        for (final String text : texts) {
            if (optionTexts().contains(text)) {
                select.selectByVisibleText(text);
            } else {
                notFoundTexts.add(text);
            }
        }

        if (!notFoundTexts.isEmpty()) {
            throw new ScEntryNotFoundException("'" + String.join("', '", notFoundTexts) + "'");
        }
    }

    public void select(@NonNull Pattern pattern) {
        final List<String> matchedOptions = optionTexts().stream().filter(option -> pattern.matcher(option).matches()).toList();

        if (matchedOptions.isEmpty()) {
            throw new ScEntryNotFoundException("Cannot find options that match with pattern: " + pattern.pattern());
        } else {
            select(matchedOptions.toArray(new String[0]));
        }
    }

    public void deselect(@NonNull String... texts) {
        final Select select = new Select(this.scrolledElement());
        final List<String> notFoundTexts = new ArrayList<>();

        for (String text : texts) {
            if (optionTexts().contains(text)) {
                select.deselectByVisibleText(text);
            } else {
                notFoundTexts.add(text);
            }
        }

        if (!notFoundTexts.isEmpty()) {
            throw new ScEntryNotFoundException("'" + String.join("', '", notFoundTexts) + "'");
        }
    }

    public void deselect(@NonNull Pattern pattern) {
        final List<String> matchedOptions = optionTexts().stream().filter(option -> pattern.matcher(option).matches()).toList();

        if (matchedOptions.isEmpty()) {
            throw new ScEntryNotFoundException("Cannot find options that match with pattern: " + pattern.pattern());
        } else {
            deselect(matchedOptions.toArray(new String[0]));
        }
    }

    public void clear() {
        new Select(this.scrolledElement()).deselectAll();
    }

    public List<String> selectedTexts() {
        return new Select(this.scrolledElement()).getAllSelectedOptions().stream().map(WebElement::getText).toList();
    }


    public List<String> optionTexts() {
        return new Select(existingElement()).getOptions().stream().map(e -> e.getText().trim()).toList();
    }
}
