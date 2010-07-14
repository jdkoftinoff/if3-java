package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class PrefixTesterUrl implements PrefixTester {

    @Override
    public boolean isPrefix(char c) {
        return c == '.' || c == '/' || c == '=' || c == '&';
    }
}
