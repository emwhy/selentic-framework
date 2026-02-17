package org.emwhyware.selentic.lib;

import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ScDateTextbox extends ScTextbox {
    private static final DateTimeFormatter DATE_TYPE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMddyyyy");

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("date");
    }

    @Override
    public void enterText(CharSequence... text) {
        final WebElement webElement = this.scrolledElement();

        this.focus();
        webElement.clear();
        webElement.sendKeys(text);
    }

    public void enterDate(LocalDate date) {
        final WebElement webElement = this.scrolledElement();

        this.focus();
        webElement.clear();
        webElement.sendKeys(date.format(inputFormatter));
    }

    public final LocalDate parse() {
        return LocalDate.parse(this.text(), DATE_TYPE_FORMATTER);
    }

    public final boolean isValidDate() {
        try {
            parse();
            return true;
        } catch (DateTimeParseException ex) {
            return false;
        }
    }

    public ScDateTextbox withInputFormatter(DateTimeFormatter inputFormatter) {
        this.inputFormatter = inputFormatter;
        return this;
    }
}
