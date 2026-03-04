package org.emw.selentic.regression.component;

import org.emw.selentic.lib.ScComponent;
import org.emw.selentic.lib.ScComponentCollection;
import org.emw.selentic.lib.ScComponentRule;
import org.emw.selentic.lib.ScGenericComponent;
import org.emw.selentic.lib.selector.ScXPath;

public class ScXPathSelectorTestTable extends ScComponent {
    private static final ScXPath XPATH_BOUNDARY_TEST_TEXTS = _xpath.descendant("tr", _indexFrom(3), _boundary(_xpath.page("tr", _id().is("test-between-elements-title-row2")))).child("td", _indexOf(0));

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("table");
        rule.id().is("test-between-elements-table");
    }

    public ScComponentCollection<ScGenericComponent> xPathBoundaryTestTexts() {
        return $$components(XPATH_BOUNDARY_TEST_TEXTS, ScGenericComponent.class);
    }
}
