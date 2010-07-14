package com.internetfilter.if2k.kernel;

public class ScannerTargetDumping implements ScannerTarget {

    public void matchFound(Scanner s, TreeNode match_item) {
        String match_string = match_item.extractWord();

        System.out.println("Found Match: " + match_string);
        System.out.println("Flags: " + match_item.getFlags());
    }
}
