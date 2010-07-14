package com.internetfilter.if2k.kernel;

public class ComparatorCaseInsensitive implements Comparator {

    public boolean isEqual(char a, char b) {
        return Character.toUpperCase(a) == Character.toUpperCase(b);
    }
}
