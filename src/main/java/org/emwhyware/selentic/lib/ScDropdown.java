package org.emwhyware.selentic.lib;

import org.emwhyware.selentic.lib.exception.ScEntryNotFoundException;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ScDropdown extends ScFormComponent {

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("select");
        rule.attr("multiple").isAbsent();
    }

    public void select(String text) {
        final Select select = new Select(this.scrolledElement());

        select.selectByVisibleText(text);
    }

    public void select(Pattern pattern) {
        final Optional<String> matchedOption = optionTexts().stream().filter(option -> pattern.matcher(option).matches()).findFirst();

        if (matchedOption.isPresent()) {
            select(matchedOption.get());
        } else {
            throw new ScEntryNotFoundException("Cannot find option with pattern: " + pattern.pattern());
        }
    }

    public String selectedText() {
        final Select select = new Select(this.existingElement());

        return select.getFirstSelectedOption().getText();
    }

    public List<String> optionTexts() {
        final Select select = new Select(this.existingElement());

        return select.getOptions().stream().map(e -> e.getText().trim()).toList();
    }

}
