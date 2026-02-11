package org.selion_framework.lib;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.selion_framework.lib.exception.SeEntryNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SeMultiSelect extends SeFormComponent {
    @Override
    protected void rules(SeComponentRule rule) {
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
            throw new SeEntryNotFoundException("'" + String.join("', '", notFoundTexts) + "'");
        }
    }

    public void selectStartWith(String text) {
        final List<String> matchedOptions = optionTexts().stream().filter(p -> p.startsWith(text)).toList();

        if (matchedOptions.isEmpty()) {
            throw new SeEntryNotFoundException("Cannot find options that start with: " + text);
        } else {
            select(matchedOptions.toArray(new String[0]));
        }
    }

    public void selectEndWith(String text) {
        final List<String> matchedOptions = optionTexts().stream().filter(p -> p.endsWith(text)).toList();

        if (matchedOptions.isEmpty()) {
            throw new SeEntryNotFoundException("Cannot find options that end with: " + text);
        } else {
            select(matchedOptions.toArray(new String[0]));
        }
    }

    public void selectContain(String text) {
        final List<String> matchedOptions = optionTexts().stream().filter(p -> p.contains(text)).toList();

        if (matchedOptions.isEmpty()) {
            throw new SeEntryNotFoundException("Cannot find options that contain: " + text);
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
            throw new SeEntryNotFoundException("'" + String.join("', '", notFoundTexts) + "'");
        }
    }

    public void deselectStartWith(String text) {
        final List<String> matchedOptions = optionTexts().stream().filter(p -> p.startsWith(text)).toList();

        if (matchedOptions.isEmpty()) {
            throw new SeEntryNotFoundException("Cannot find options that start with: " + text);
        } else {
            deselect(matchedOptions.toArray(new String[0]));
        }
    }

    public void deselectEndWith(String text) {
        final List<String> matchedOptions = optionTexts().stream().filter(p -> p.endsWith(text)).toList();

        if (matchedOptions.isEmpty()) {
            throw new SeEntryNotFoundException("Cannot find options that end with: " + text);
        } else {
            deselect(matchedOptions.toArray(new String[0]));
        }
    }

    public void deselectContain(String text) {
        final List<String> matchedOptions = optionTexts().stream().filter(p -> p.contains(text)).toList();

        if (matchedOptions.isEmpty()) {
            throw new SeEntryNotFoundException("Cannot find options that contain: " + text);
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
