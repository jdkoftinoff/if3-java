package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class TreeNode {

    /**
     * Construct a TreeNode, containing no heritage, links, or values.
     */
    public TreeNode() {
        clear();
    }

    /**
     * toString() prints text version of TreeNode
     * 
     * @return String
     */
    public String toString() {
        String r =
                "sibling: " + sibling + "\n" +
                "child: " + child + "\n" +
                "flags: " + flags + "\n" +
                "val: " + val + "\n";
        return r;
    }

    /**
     * Clear the Node
     */
    public void clear() {
        parent = null;
        sibling = null;
        child = null;
        flags = null;
        val = '\0';
    }

    /** remove
     *
     *  remove self from the tree, update all siblings and parents
     * 
     */
    public void remove() {
        // do we have a parent?
        if (parent != null) {
            // ask the parent for the first child
            TreeNode cur = parent.getChild();
            // is the first child me?
            if (cur == this) {
                // yes, remove me and fix up parent's child link to my sibling
                parent.setChild(sibling);
                // and we are done
            } else {
                // we have to follow the parent's children and find the one that points to us
                while (cur != null) {
                    if (cur.getSibling() == this) {
                        // found my older sibling.  Tell him to connect to my next younger sibling
                        cur.setSibling(sibling);
                    }
                }
            }
        } else {
            // no parent, we are it, remove children/siblings.
            sibling = null;
            child = null;
            flags = null;
        }
    }

    /**
     * Add a sibling node to the node. Follow sibling chain until end of chain
     * is found to append to.
     *
     * @param cur
     *            Node to add a sibling to.
     */
    public void addSibling(TreeNode cur) {
        TreeNode last_sibling = this;
        while (last_sibling.sibling != null) {
            last_sibling = last_sibling.sibling;
        }
        last_sibling.sibling = cur;
        cur.sibling = null;
    }

    /**
     * The AddChild() method returns an existing matching child Node, or creates
     * one if it didn't previously exist
     *
     * @param c
     *            character to add as a child node
     * @param comp
     *            Comparator to use
     * @return Node that was created
     */
    public TreeNode addChild(char c, Comparator comp) {
        TreeNode cur = hasChild(c, comp);
        if (cur == null) {
            cur = new TreeNode();
            cur.parent = this;
            cur.val = c;
            if (child != null) {
                child.addSibling(cur);
            } else {
                child = cur;
            }
        }
        return cur;
    }

    /**
     * The HasSibling() method returns an existing Node in this sibling list
     * that matches the specified char. The sibling list includes the current
     * node as well.
     *
     * @param c
     *            character to search for
     * @param comp
     *            The Comparator to use
     * @return Node that matches, or null
     */
    public TreeNode hasSibling(char c, Comparator comp) {
        TreeNode cur = this;
        while (cur != null) {
            if (comp.isEqual(cur.val, c)) {
                return cur;
            }
            cur = cur.sibling;
        }
        return null;
    }

    /**
     * The HasChild() method returns an existing Node in this Node's child list
     * that matches the specified char if it exists.
     *
     * @param c
     *            character to search for
     * @param comp
     *            The Comparator to use
     * @return Node that matches, or null
     */
    public TreeNode hasChild(char c, Comparator comp) {
        if (child != null) {
            return child.hasSibling(c, comp);
        } else {
            return null;
        }
    }

    /**
     * <summary> The Add() method adds the string to the current node, creating
     * children nodes as needed. The specified word_flags is attached to the
     * final node. Any characters that the Ignorer says are to be ignored are
     * skipped. Any comparisons are done with the Comparator. The final Node of
     * the word is returned.
     *
     * @param word
     *            Word to add
     * @param word_flags
     *            flags to associate with word
     * @param comp
     *            Comparator to use
     * @param ig
     *            Ignorer to use
     * @return Node in the Tree of last character in the word
     */
    public TreeNode add(String word, TreeFlag word_flags, Comparator comp,
            Ignorer ig) {
        TreeNode cur = this;

        for (int p = 0; p < word.length(); ++p) {
            char c = word.charAt(p);
            if (!ig.isIgnored(c)) {
                cur = cur.addChild(c, comp);
            }
        }
        cur.flags = word_flags;
        return cur;
    }

    /**
     *
     * The FindLongest() method searches the node structure for a match,
     * skipping any characters that the Ignorer says to ignore and using the
     * Comparator to compare characters. If the word is found in the structure
     * then the final Node is returned, otherwise it returns null
     *
     * @param word
     *            word to find
     * @param pos
     *            position in word to start at
     * @param comp
     *            Comparator to use
     * @param ig
     *            Ignorer to use
     * @return Node in the Tree of last character of the matching word, or null
     *         if no match
     */
    public TreeNode findLongest(String word, int pos, Comparator comp,
            Ignorer ig) {
        TreeNode cur = null;
        char c;
        if (word.length() > pos) {
            cur = this;
            for (int i = pos; i < word.length(); ++i) {
                c = word.charAt(i);
                if (!ig.isIgnored(c)) {
                    cur = cur.hasChild(c, comp);

                    // No child matches, so we stop searching at this point
                    if (cur == null) {
                        break;
                    }

                    // if the child matches we may have a match. But if there
                    // are more
                    // chars in the string,
                    // we must continue down the children to see if there is a
                    // longer
                    // match that is better.
                    // If we do find a longer match, we return that.
                    // If we do not find a longer match, we return our match.

                    if ((cur.getFlags() != null) && (i < word.length() - 1)) {
                        TreeNode possible_match = cur.findLongest(word, i + 1,
                                comp, ig);
                        if (possible_match != null) {
                            cur = possible_match;
                        }
                        // at this point, break out of our loop. cur is either
                        // the match we
                        // found or
                        // is the longer match that the last child of the short
                        // match found.
                        break;
                    }
                }
            }
        }
        // Only return nodes that have a non-zero GetFlags() - this means it is
        // a
        // complete match
        if (cur != null) {
            if (cur.getFlags() == null) {
                cur = null;
            }
        }
        return cur;
    }

    /**
     *
     * The FindShortest() method searches the node structure for a match,
     * skipping any characters that the Ignorer says to ignore and using the
     * Comparator to compare characters. If the word is found in the structure
     * then the final Node is returned, otherwise it returns null
     *
     * @param word
     *            word to find
     * @param pos
     *            position in word to start at
     * @param comp
     *            Comparator to use
     * @param ig
     *            Ignorer to use
     * @return Node in the Tree of last character of the matching word, or null
     *         if no match
     */
    public TreeNode findShortest(String word, int pos, Comparator comp,
            Ignorer ig) {
        TreeNode cur = null;

        if (word.length() > pos) {
            cur = this;
            for (int p = pos; p < word.length(); ++p) {
                char c = word.charAt(p);
                if (!ig.isIgnored(c)) {
                    cur = cur.hasChild(c, comp);
                    if (cur == null) {
                        return null;
                    }
                    // abort the scan if we found a node which represents the
                    // end of a
                    // word.
                    if (cur.getFlags() != null) {
                        break;
                    }
                }
            }
        }

        // Only return nodes that have a non-zero GetFlags() - this means it is
        // a
        // complete match
        if (cur != null) {
            if (cur.getFlags() == null) {
                cur = null;
            }
        }

        return cur;
    }

    /**
     *
     * ExtractWord() goes up the parental hierarchy and records each value on
     * the way. This is the stored word that this Node represents. But because
     * of the traversal it is reversed, so we reverse it again so it is forward.
     *
     * @return string that the node represents
     */
    public String extractWord() {
        String reverse_result = "";
        TreeNode cur = this;
        while (cur != null) {
            reverse_result += cur.getValue();
            cur = cur.getParent();
        }

        String forward_result = "";
        for (int i = reverse_result.length() - 1; i >= 0; --i) {
            forward_result += reverse_result.charAt(i);
        }
        return forward_result;
    }

    /**
     *
     * Get the parent Node of this Node.
     *
     * @return Node of parent
     */
    public TreeNode getParent() {
        return parent;
    }

    /** getChild
     *
     * @return TreeNode the first child
     */
    public TreeNode getChild() {
        return child;
    }

    /**
     * setChild
     * @param n TreeNode to set as first child
     */
    public void setChild(TreeNode n) {
        child = n;
    }

    /**
     * getSibling returns the next sibling in line
     *
     * @return TreeNode
     */
    public TreeNode getSibling() {
        return sibling;
    }

    /**
     * setSibling() sets a new next sibling
     * 
     * @param s TreeNode of new next sibling
     */
    public void setSibling(TreeNode s) {
        sibling = s;
    }

    /**
     * hasSiblings
     * 
     * @return boolean true if this node has any older or younger siblings
     */
    public boolean hasSiblings() {
        // if there is a next sibling then this is easy
        if (this.sibling != null) {
            return true;
        }

        // otherwise we have to ask the parent for the first child
        // if the first child is not the node then there is a sibling
        if (this.parent != null && this.parent.child != this) {
            return true;
        }

        return false;
    }

    /**
     *
     * Set the character value of this Node
     *
     * @param v
     *            character value
     */
    public void setValue(char v) {
        val = v;
    }

    /**
     *
     * Get the character value of this Node
     *
     * @return char value
     */
    public char getValue() {
        return val;
    }

    /**
     *
     * Set the flags for this Node
     *
     * @param f
     *            TreeFlag flags
     */
    public void setFlags(TreeFlag f) {
        flags = f;
    }

    /**
     *
     * Get the flags for this Node
     *
     * @return TreeFlag flags
     */
    public TreeFlag getFlags() {
        return flags;
    }
    private TreeNode parent;
    private TreeNode sibling;
    private TreeNode child;
    private TreeFlag flags;
    private char val;
}
