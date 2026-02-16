package org.emwhyware.selion.regression.component;

import org.emwhyware.selion.lib.SnComponent;
import org.emwhyware.selion.lib.SnComponentRule;
import org.emwhyware.selion.lib.SnGenericComponent;
import org.emwhyware.selion.lib.SnXPath;

public class SnTestTableRow extends SnComponent {
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

    public final SnGenericComponent productNameText = $genericComponent(PRODUCT_NAME_TEXT);

    public final SnGenericComponent serialNumberText = $genericComponent(SERIAL_NUMBER_TEXT);

    public final SnGenericComponent productTypeText = $genericComponent(PRODUCT_TYPE_TEXT);

    public final SnGenericComponent builtDateText = $genericComponent(BUILT_DATE_TEXT);

    public final SnGenericComponent priceAmountText = $genericComponent(PRICE_AMOUNT_TEXT);
}
