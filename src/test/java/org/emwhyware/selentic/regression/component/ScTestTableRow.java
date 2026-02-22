package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.ScComponentRule;
import org.emwhyware.selentic.lib.ScGenericComponent;
import org.emwhyware.selentic.lib.selector.ScXPath;

public class ScTestTableRow extends ScComponent {
    private static final ScXPath PRODUCT_NAME_TEXT = _xpath.descendant("td", _cssClasses("product-name"));
    private static final ScXPath SERIAL_NUMBER_TEXT = _xpath.descendant("td", _cssClasses("serial-number"));
    private static final ScXPath PRODUCT_TYPE_TEXT = _xpath.descendant("td", _cssClasses("product-type"));
    private static final ScXPath BUILT_DATE_TEXT = _xpath.descendant("td", _cssClasses("built-date"));
    private static final ScXPath PRICE_AMOUNT_TEXT = _xpath.descendant("td", _cssClasses("price"));

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("tr");
    }

    @Override
    public String text() {
        return productNameText().text();
    }

    public ScGenericComponent productNameText() {
        return $genericComponent(PRODUCT_NAME_TEXT);
    }

    public ScGenericComponent serialNumberText() {
        return $genericComponent(SERIAL_NUMBER_TEXT);
    }

    public ScGenericComponent productTypeText() {
        return $genericComponent(PRODUCT_TYPE_TEXT);
    }

    public ScGenericComponent builtDateText() {
        return $genericComponent(BUILT_DATE_TEXT);
    }

    public ScGenericComponent priceAmountText() {
        return $genericComponent(PRICE_AMOUNT_TEXT);
    }
}
