package com.internetfilter.if2k.kernel;

import java.io.PrintWriter;

public class PatternExpanderTargetDumper implements PatternExpanderTarget {

    @Override
    public void add(String pattern, TreeFlag flags) {
        System.out.println("ADD: " + flags.getFlag() + " " + pattern);
    }

    @Override
    public boolean remove(String pattern) {
        System.out.println("REMOVE: " + pattern );
        return true;
    }
}
