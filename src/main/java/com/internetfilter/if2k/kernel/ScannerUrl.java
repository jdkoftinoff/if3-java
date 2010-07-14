package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class ScannerUrl extends Scanner {

    public ScannerUrl(TreeLoader loader) {
        super(new TreeUrl(), new PrefixTesterUrl(), new PatternExpanderUrl(), loader);
        this.getPatternExpander().setTarget(getTree());
    }
}
