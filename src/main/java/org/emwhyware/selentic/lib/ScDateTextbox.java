package org.emwhyware.selentic.lib;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.openqa.selenium.WebElement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ScDateTextbox extends ScFormComponent {
    private static final DateTimeFormatter DATE_TYPE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("MMddyyyy");

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("input");
        rule.type().is("date");
    }

    @Override
    public String text() {
        return this.value();
    }

    public void enterDate(@NonNull LocalDate date) {
        final WebElement webElement = this.scrolledElement();

        this.focus();
        webElement.clear();

        // For Firefox browser, the input format must be set differently.
        webElement.sendKeys(date.format(Selentic.browser() == ScBrowser.Firefox ? DATE_TYPE_FORMATTER : INPUT_FORMATTER));
    }

    public final LocalDate parse() {
        return LocalDate.parse(this.text(), DATE_TYPE_FORMATTER);
    }
}
