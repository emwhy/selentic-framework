package org.selion_framework.lib;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.selion_framework.lib.exception.SnEntryNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class SnMultiSelect extends SnFormComponent {
    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("select");
        rule.attr("multiple").isPresent();
    }

    public void select(String... texts) {
        final Select select = new Select(this.scrolled());
        final List<String> notFoundTexts = new ArrayList<>();

        for (String text : texts) {
            if (optionTexts().contains(text)) {
                select.selectByVisibleText(text);
            } else {
                notFoundTexts.add(text);
            }
        }

        if (!notFoundTexts.isEmpty()) {
            throw new SnEntryNotFoundException("'" + String.join("', '", notFoundTexts) + "'");
        }
    }

    public void select(Pattern pattern) {
        final List<String> matchedOptions = optionTexts().stream().filter(option -> pattern.matcher(option).matches()).toList();

        if (matchedOptions.isEmpty()) {
            throw new SnEntryNotFoundException("Cannot find options that match with pattern: " + pattern.pattern());
        } else {
            select(matchedOptions.toArray(new String[0]));
        }
    }

    public void deselect(String... texts) {
        final Select select = new Select(this.scrolled());
        final List<String> notFoundTexts = new ArrayList<>();

        for (String text : texts) {
            if (optionTexts().contains(text)) {
                select.deselectByVisibleText(text);
            } else {
                notFoundTexts.add(text);
            }
        }

        if (!notFoundTexts.isEmpty()) {
            throw new SnEntryNotFoundException("'" + String.join("', '", notFoundTexts) + "'");
        }
    }

    public void deselect(Pattern pattern) {
        final List<String> matchedOptions = optionTexts().stream().filter(option -> pattern.matcher(option).matches()).toList();

        if (matchedOptions.isEmpty()) {
            throw new SnEntryNotFoundException("Cannot find options that match with pattern: " + pattern.pattern());
        } else {
            deselect(matchedOptions.toArray(new String[0]));
        }
    }

    public void clear() {
        new Select(this.scrolled()).deselectAll();
    }

    public List<String> selectedTexts() {
        return new Select(this.scrolled()).getAllSelectedOptions().stream().map(WebElement::getText).toList();
    }


    public List<String> optionTexts() {
        return new Select(existing()).getOptions().stream().map(e -> e.getText().trim()).toList();
    }
}
