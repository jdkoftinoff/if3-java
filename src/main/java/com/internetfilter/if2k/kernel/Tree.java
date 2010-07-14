package com.internetfilter.if2k.kernel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Tree class is the special top node. It is organized to be very efficient
 * to find the first character of a word. It does this by having an array of
 * Node's, one for each potential starting character in ascii. The Tree class is
 * also a PatternExpanderTarget, so that a PatternExpander can populate a Tree
 * directly.
 * 
 * @author Jeff Koftinoff - jeffk@internetfilter.com
 */
public class Tree implements PatternExpanderTarget {

    private Log log = LogFactory.getLog(PatternExpanderTarget.class);

    /**
     *
     * Construct a Tree object which utilizes the provided Comparator and
     * Ignorer
     *
     * @param comp
     *            Comparator to use with this Tree
     * @param ig
     *            Ignorer to use with this Tree
     */
    public Tree(Comparator comp, Ignorer ig) {
        log.debug("Creating Tree " + this);
        comparator = comp;
        ignorer = ig;

        // create the 128 top Node's.
        top = new TreeNode[128];

        // populate the top nodes with their default values, which match their
        // index
        // number.
        for (int i = 0; i < 128; ++i) {
            top[i] = new TreeNode();
            top[i].clear();
            top[i].setValue((char) i);
        }
    }

    /**
     *
     * Clear the tree.
     *
     */
    public void clear() {
        log.debug("Clearing Tree " + this);

        for (int i = 0; i < 128; ++i) {
            top[i].clear();
            top[i].setValue((char) i);
        }
    }

    /**
     * Add a word with the specified flags to the Tree.
     *
     * @param word
     *            string word to add
     * @param flags
     *            int flags to associate with word
     */
    @Override
    public void add(String word, TreeFlag flags) {
        if (word.length() > 0) {
            int pos = 0;
            TreeNode cur = top[(int) word.charAt(pos) & 0x7f];
            cur.add(word.substring(pos + 1), flags, comparator, ignorer);
        }
    }

    /**
     * Remove word form tree
     * 
     * @param word
     *             String word to remove
     */
    @Override
    public boolean remove(String word ) {
        boolean r = false;

        // first find the word in the tree
        TreeNode cur = findLongest(word);
        // the word is in the tree. follow up the tree to find the
        // last branch and then nullify at that point.
        while( cur!=null ) {
            if( cur.hasSiblings() || cur.getParent()==null ) {
                // this node has siblings or parent, so this is where we delete the branch
                cur.remove();
                // we are done
                r=true;
                break;
            }
            // not done yet, go up the tree by finding the parent
            cur = cur.getParent();
        }
        return r;
    }


    /**
     * Search the tree for the longest matching word
     *
     * @param word
     *            string word to search for
     * @param cur_offset
     *            offset within string to start search at
     * @return Node of final matching character in tree, or null if no match
     */
    public TreeNode findLongest(String word, int cur_offset) {
        int pos = cur_offset;
        TreeNode cur = top[(int) word.charAt(pos) & 0x7f];
        cur = cur.findLongest(word, pos + 1, comparator, ignorer);
        return cur;
    }

    /**
     * Search the tree for the longest matching word
     *
     * @param word
     *            string word to search for
     * @return Node of final matching character in tree, or null if no match
     */
    public TreeNode findLongest(String word) {
        return findLongest(word, 0);
    }

    /**
     *
     * Search the tree for the shortest matching word
     *
     * @param word
     *            string word to search for
     * @param cur_offset
     *            offset within string to start search at
     *
     * @return Node of final matching character in tree, or null if no match
     */
    public TreeNode findShortest(String word, int cur_offset) {
        int pos = cur_offset;
        TreeNode cur = top[(int) word.charAt(pos) & 0x7f];
        cur = cur.findShortest(word, pos + 1, comparator, ignorer);
        return cur;
    }

    /**
     * Search the tree for the shortest matching word
     *
     * @param word
     *            string word to search for
     *
     * @return Node of final matching character in tree, or null if no match
     */
    public TreeNode findShortest(String word) {
        return findShortest(word, 0);
    }
    private Comparator comparator;
    private Ignorer ignorer;
    private TreeNode[] top;
}
