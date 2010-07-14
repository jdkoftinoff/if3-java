package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class PatternExpanderSimple extends PatternExpander {

    public PatternExpanderSimple(PatternExpanderTarget target) {
        super(target);
    }

    public PatternExpanderSimple() {
        super();
    }

    public void add(String prefix, String pattern, TreeFlag flags) {
        getTarget().add(prefix + pattern, flags);
    }

    public boolean remove(String prefix, String pattern) {
        return getTarget().remove(prefix + pattern);
    }

}
