package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public interface PatternExpanderTarget {

    void add(String pattern, TreeFlag flags);
    boolean remove( String pattern );
}
