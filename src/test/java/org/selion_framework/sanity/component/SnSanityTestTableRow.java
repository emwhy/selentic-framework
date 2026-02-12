package org.selion_framework.sanity.component;

import org.selion_framework.lib.SnComponent;
import org.selion_framework.lib.SnComponentRule;
import org.selion_framework.lib.SnGenericComponent;
import org.selion_framework.lib.SnXPathSelector;

public class SnSanityTestTableRow extends SnComponent {
    private static final SnXPathSelector PRODUCT_NAME_TEXT = _xpath.descendant("td", _cssClasses("product-name"));
    private static final SnXPathSelector SERIAL_NUMBER_TEXT = _xpath.descendant("td", _cssClasses("serial-number"));
    private static final SnXPathSelector PRODUCT_TYPE_TEXT = _xpath.descendant("td", _cssClasses("product-type"));
    private static final SnXPathSelector BUILT_DATE_TEXT = _xpath.descendant("td", _cssClasses("built-date"));
    private static final SnXPathSelector PRICE_AMOUNT_TEXT = _xpath.descendant("td", _cssClasses("price"));

    @Override
    protected void rules(SnComponentRule rule) {
        rule.tag().is("tr");
    }

    @Override
    public String key() {
        return productNameText.key();
    }

    public final SnGenericComponent productNameText = $genericComponent(PRODUCT_NAME_TEXT);

    public final SnGenericComponent serialNumberText = $genericComponent(SERIAL_NUMBER_TEXT);

    public final SnGenericComponent productTypeText = $genericComponent(PRODUCT_TYPE_TEXT);

    public final SnGenericComponent builtDateText = $genericComponent(BUILT_DATE_TEXT);

    public final SnGenericComponent priceAmountText = $genericComponent(PRICE_AMOUNT_TEXT);
}
