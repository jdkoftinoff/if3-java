package com.internetfilter.if2k.kernel;

public class TreePhrase extends Tree {

    public TreePhrase() {
        super(new ComparatorCaseInsensitive(),
                new IgnorerWhiteSpace());
    }
}
