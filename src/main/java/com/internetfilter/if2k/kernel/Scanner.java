package com.internetfilter.if2k.kernel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

/**
 * 
 * The Scanner class is given a tree to use for pattern matching. It can scan a
 * buffer starting at each position in the buffer for matches in the Tree.
 * 
 * @author jeffk
 * 
 */
public class Scanner {

    /**
     *
     * Construct a Scanner object, attached to a Tree and a PrefixTester object
     *
     * @param t
     *            Tree to use
     * @param prefix_tester
     *            PrefixTester to use
     */
    public Scanner(Tree t, PrefixTester prefix_tester,
            PatternExpander pattern_expander, TreeLoader loader) {
        this.tree = t;
        this.prefix_tester = prefix_tester;
        this.pattern_expander = pattern_expander;
        this.loader = loader;
    }

    public void load( BufferedReader reader, String prefix, TreeFlag flag) throws IOException {
        if( reader!=null )
        {
            loader.load(reader, pattern_expander, prefix, flag);
        }
    }

    /**
     *
     * Call scanBuffer() to see if a buffer contains any potential matches. Any
     * matches are given to the ScannerTarget specified. The count of matches is
     * returned.
     *
     * @param buf
     *            Buffer to scan
     * @param target
     *            ScannerTarget to notify when any matches are found
     * @return int count of matches
     */
    public int scanBuffer(String buf, ScannerTarget target) {
        int match_count = 0;
        for (int i = 0; i < buf.length(); ++i) {
            // do a test on first character or on any other character where the
            // previous
            // character is matched by the prefix tester object
            Boolean needs_testing = (i == 0);
            if (i != 0) {
                needs_testing = prefix_tester.isPrefix(buf.charAt(i - 1));
            }

            // TODO: prefix_tester's result is currently ignored
            
            if (true) {
                // find the longest matching word in a tree
                TreeNode n = tree.findLongest(buf, i);
                if (n != null) {
                    target.matchFound(this, n);
                    match_count++;
                }
            }
        }
        return match_count;
    }

    /**
     *
     * Get the tree that is used for Scanning.
     *
     * @return Tree
     */
    public Tree getTree() {
        return tree;
    }

    /**
     *
     * Set a new tree to use for scanning
     *
     * @param t
     *            Tree
     */
    public void setTree(Tree t) {
        tree = t;
    }

    public PatternExpander getPatternExpander() {
        return pattern_expander;
    }
    private Tree tree;
    private PrefixTester prefix_tester;
    private PatternExpander pattern_expander;
    private TreeLoader loader;
}
