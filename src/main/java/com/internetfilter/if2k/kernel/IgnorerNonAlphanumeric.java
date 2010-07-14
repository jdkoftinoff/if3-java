package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class IgnorerNonAlphanumeric implements Ignorer {

    public Boolean isIgnored(char c) {
        return !Character.isLetterOrDigit(c);
    }
}
