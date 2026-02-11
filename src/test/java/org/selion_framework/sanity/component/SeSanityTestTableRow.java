package org.selion_framework.sanity.component;

import org.selion_framework.lib.SeComponent;
import org.selion_framework.lib.SeComponentRule;
import org.selion_framework.lib.SeGenericComponent;
import org.selion_framework.lib.SeLocatorNode;

public class SeSanityTestTableRow extends SeComponent {
    private static final SeLocatorNode PRODUCT_NAME_TEXT = _xpath.descendant("td", _cssClasses("product-name"));
    private static final SeLocatorNode SERIAL_NUMBER_TEXT = _xpath.descendant("td", _cssClasses("serial-number"));
    private static final SeLocatorNode PRODUCT_TYPE_TEXT = _xpath.descendant("td", _cssClasses("product-type"));
    private static final SeLocatorNode BUILT_DATE_TEXT = _xpath.descendant("td", _cssClasses("built-date"));
    private static final SeLocatorNode PRICE_AMOUNT_TEXT = _xpath.descendant("td", _cssClasses("price"));

    @Override
    protected void rules(SeComponentRule rule) {
        rule.tag().is("tr");
    }

    @Override
    public String key() {
        return productNameText.key();
    }

    public final SeGenericComponent productNameText = $genericComponent(PRODUCT_NAME_TEXT);

    public final SeGenericComponent serialNumberText = $genericComponent(SERIAL_NUMBER_TEXT);

    public final SeGenericComponent productTypeText = $genericComponent(PRODUCT_TYPE_TEXT);

    public final SeGenericComponent builtDateText = $genericComponent(BUILT_DATE_TEXT);

    public final SeGenericComponent priceAmountText = $genericComponent(PRICE_AMOUNT_TEXT);
}
