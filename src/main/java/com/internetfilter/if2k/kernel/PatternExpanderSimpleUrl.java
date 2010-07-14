package com.internetfilter.if2k.kernel;

public class PatternExpanderSimpleUrl extends PatternExpander {

    public PatternExpanderSimpleUrl(PatternExpanderTarget target) {
        super(target);
    }

    public PatternExpanderSimpleUrl() {
        super();
    }

    public void add(String prefix, String pattern, TreeFlag flags) {
        String real_pattern = prefix + pattern;
        if (real_pattern.startsWith("http://www.")) {
            real_pattern = real_pattern.substring(11);
        } else if (real_pattern.startsWith("http://")) {
            real_pattern = real_pattern.substring(7);
        }
        // Note: We leave all other prefixes as is. We want to know about other
        // subdomains besides www and we want to
        // explicitly allows specialization of searches with https:// prefix.

        getTarget().add(real_pattern, flags);
    }

    @Override
    public boolean remove(String prefix, String pattern) {
        String real_pattern = prefix + pattern;
        if (real_pattern.startsWith("http://www.")) {
            real_pattern = real_pattern.substring(11);
        } else if (real_pattern.startsWith("http://")) {
            real_pattern = real_pattern.substring(7);
        }
        // Note: We leave all other prefixes as is. We want to know about other
        // subdomains besides www and we want to
        // explicitly allows specialization of searches with https:// prefix.

        return getTarget().remove(real_pattern);
    }

}
