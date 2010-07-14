package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class TreeUrl extends Tree {

    public TreeUrl() {
        super(new ComparatorCaseInsensitive(),
                new IgnorerWhiteSpace());
    }
}
