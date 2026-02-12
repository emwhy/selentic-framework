package org.selion_framework.lib;

import org.openqa.selenium.support.ui.Select;
import org.selion_framework.lib.exception.SeEntryNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SeDropdown extends SeFormComponent {

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("select");
    }

    public void select(String text) {
        final Select select = new Select(this.scrolled());

        select.selectByVisibleText(text);
    }

    public void select(Pattern pattern) {
        final Optional<String> matchedOption = optionTexts().stream().filter(option -> pattern.matcher(option).matches()).findFirst();

        if (matchedOption.isPresent()) {
            select(matchedOption.get());
        } else {
            throw new SeEntryNotFoundException("Cannot find option with pattern: " + pattern.pattern());
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
