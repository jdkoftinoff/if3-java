package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public interface ScannerTarget {

    public void matchFound(Scanner s, TreeNode match_item);
}
