package org.emwhyware.selentic.regression.component;

import org.emwhyware.selentic.lib.ScComponent;
import org.emwhyware.selentic.lib.ScComponentCollection;
import org.emwhyware.selentic.lib.ScComponentRule;
import org.emwhyware.selentic.lib.ScGenericComponent;
import org.emwhyware.selentic.lib.selector.ScXPath;

public class ScXPathSelectorTestTable extends ScComponent {
    private static final ScXPath XPATH_BOUNDARY_TEST_TEXTS = _xpath.descendant("tr", _indexFrom(3)).boundary(_xpath.page("tr", _id().is("test-between-elements-title-row2"))).child("td", _indexOf(0));

    @Override
    protected void rules(ScComponentRule rule) {
        rule.tag().is("table");
        rule.id().is("test-between-elements-table");
    }

    public ScComponentCollection<ScGenericComponent> xPathBoundaryTestTexts() {
        return $$components(XPATH_BOUNDARY_TEST_TEXTS, ScGenericComponent.class);
    }
}
