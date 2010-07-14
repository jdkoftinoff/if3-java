package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class IgnorerWhiteSpace implements Ignorer {

    public Boolean isIgnored(char c) {
        return Character.isWhitespace(c);
    }
}
