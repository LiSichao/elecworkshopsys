package com.design.elecworkshopsys.utils;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

public class ExtHtmlTagRuleBundle implements TagRuleBundle {

    @Override
    public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
        ExportTagToContentRule myscript = new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("myscript"), false);
        defaultState.addRule("myscript", myscript);

    }

    @Override
    public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {

    }
}
