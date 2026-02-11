package org.selion_framework.lib;

import org.openqa.selenium.support.ui.Select;
import org.selion_framework.lib.exception.SeEntryNotFoundException;

import java.util.List;
import java.util.Optional;

public class SeDropdown extends SeFormComponent {

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("select");
    }

    public void select(String text) {
        final Select select = new Select(this.scrolled());

        select.selectByVisibleText(text);
    }

    public void selectStartsWith(String text) {
        final Optional<String> matchedOption = optionTexts().stream().filter(p -> p.startsWith(text)).findFirst();

        if (matchedOption.isPresent()) {
            select(matchedOption.get());
        } else {
            throw new SeEntryNotFoundException("Cannot find option that starts with: " + text);
        }
    }

    public void selectEndsWith(String text) {
        final Optional<String> matchedOption = optionTexts().stream().filter(p -> p.endsWith(text)).findFirst();

        if (matchedOption.isPresent()) {
            select(matchedOption.get());
        } else {
            throw new SeEntryNotFoundException("Cannot find option that ends with: " + text);
        }
    }

    public void selectContains(String text) {
        final Optional<String> matchedOption = optionTexts().stream().filter(p -> p.contains(text)).findFirst();

        if (matchedOption.isPresent()) {
            select(matchedOption.get());
        } else {
            throw new SeEntryNotFoundException("Cannot find option that contains: " + text);
        }
    }

    public String selectedText() {
        final Select select = new Select(this.existing());

        return select.getFirstSelectedOption().getText();
    }

    public List<String> optionTexts() {
        final Select select = new Select(this.existing());

        return select.getOptions().stream().map(e -> e.getText().trim()).toList();
    }

}
