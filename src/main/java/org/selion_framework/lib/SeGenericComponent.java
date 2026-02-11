package org.selion_framework.lib;

public class SeGenericComponent extends SeComponent {
    @Override
    protected void rules(SeComponentRule componentRule) {
        componentRule.any();
    }
}
