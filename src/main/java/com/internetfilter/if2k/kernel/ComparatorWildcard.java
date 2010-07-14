package com.internetfilter.if2k.kernel;

public class ComparatorWildcard implements Comparator {

    private Comparator chained;

    public ComparatorWildcard(Comparator chained) {
        this.chained = chained;
    }

    @Override
    public boolean isEqual(char a, char b) {
        Boolean r = false;
        if (a == '?' || chained.isEqual(a, b)) {
            r = true;
        }
        return r;
    }
}
