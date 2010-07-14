package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public abstract class PatternExpander {

    public PatternExpander(PatternExpanderTarget target) {
        this.target = target;
    }

    public PatternExpander() {
        this.target = null;
    }

    public void add(String pattern, TreeFlag flags) {
        add("", pattern, flags);
    }

    public boolean remove(String pattern) {
        return remove("",pattern);
    }

    public abstract void add(String prefix, String pattern, TreeFlag flags);

    public abstract boolean remove(String prefix, String pattern );

    public PatternExpanderTarget getTarget() {
        return this.target;
    }

    public void setTarget(PatternExpanderTarget target) {
        this.target = target;
    }
    private PatternExpanderTarget target;
}
