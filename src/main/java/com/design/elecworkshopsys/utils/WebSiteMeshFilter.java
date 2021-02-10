package com.design.elecworkshopsys.utils;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class WebSiteMeshFilter extends ConfigurableSiteMeshFilter {
    @Override
    protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) {
        builder.addDecoratorPath("/*", "/decorators/decorator")
                .addExcludedPath("/login") //白名单
                .addTagRuleBundle(new ExtHtmlTagRuleBundle())
        ;
    }
}
