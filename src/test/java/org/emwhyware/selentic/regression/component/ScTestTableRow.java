package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.SnComponentRule;
import org.emwhyware.selentic.lib.ScGenericComponent;
import org.emwhyware.selentic.lib.SnXPath;

public class ScTestTableRow extends ScComponent {
    private static final SnXPath PRODUCT_NAME_TEXT = _xpath.descendant("td", _cssClasses("product-name"));
    private static final SnXPath SERIAL_NUMBER_TEXT = _xpath.descendant("td", _cssClasses("serial-number"));
    private static final SnXPath PRODUCT_TYPE_TEXT = _xpath.descendant("td", _cssClasses("product-type"));
    private static final SnXPath BUILT_DATE_TEXT = _xpath.descendant("td", _cssClasses("built-date"));
    private static final SnXPath PRICE_AMOUNT_TEXT = _xpath.descendant("td", _cssClasses("price"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("tr");
    }

    @Override
    public String text() {
        return productNameText.text();
    }

    public final ScGenericComponent productNameText = $genericComponent(PRODUCT_NAME_TEXT);

    public final ScGenericComponent serialNumberText = $genericComponent(SERIAL_NUMBER_TEXT);

    public final ScGenericComponent productTypeText = $genericComponent(PRODUCT_TYPE_TEXT);

    public final ScGenericComponent builtDateText = $genericComponent(BUILT_DATE_TEXT);

    public final ScGenericComponent priceAmountText = $genericComponent(PRICE_AMOUNT_TEXT);
}
