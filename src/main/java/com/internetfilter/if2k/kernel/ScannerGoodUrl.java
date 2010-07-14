package com.internetfilter.if2k.kernel;

/**
 * @author jeffk
 */
public class ScannerGoodUrl extends Scanner {

    public ScannerGoodUrl(TreeLoader loader) {
        super(new TreeUrl(), new PrefixTesterUrl(), new PatternExpanderUrl(), loader);
        getPatternExpander().setTarget(getTree());
    }
}
