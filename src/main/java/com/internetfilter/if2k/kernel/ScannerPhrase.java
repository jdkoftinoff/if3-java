package com.internetfilter.if2k.kernel;

public class ScannerPhrase extends Scanner {

    public ScannerPhrase(TreeLoader loader) {
        super(new TreePhrase(), new PrefixTesterUrl(), new PatternExpanderPhrase(), loader);
        this.getPatternExpander().setTarget(getTree());
    }
}
