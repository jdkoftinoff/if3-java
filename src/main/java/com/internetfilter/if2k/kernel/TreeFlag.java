/**
 * 
 */
package com.internetfilter.if2k.kernel;

/**
 * @author jeffk
 * 
 */
public class TreeFlag {

    private int flag;
    private int category;
    private int badness;
    private int goodness;
    private int postbadness;
    private TreeAction action;

    /**
     * Create a simple TreeFlag object, with only an integer flag value
     *
     * @param flag
     *            int flag value
     */
    public TreeFlag(int flag) {
        this.setFlag(flag);
        this.setCategory(0);
        this.setBadness(0);
        this.setGoodness(0);
        this.setPostbadness(0);
        this.setAction(null);
    }

    /**
     *
     * @param flag
     * @param category
     * @param badness
     * @param goodness
     */
    public TreeFlag(int flag, int category, int badness, int goodness, int postbadness) {
        this.setFlag(flag);
        this.setCategory(category);
        this.setBadness(badness);
        this.setGoodness(goodness);
        this.setPostbadness(postbadness);
        this.setAction(null);
    }

    /**
     *
     * @param flag
     * @param category
     * @param badness
     * @param goodness
     * @param action
     */
    public TreeFlag(int flag, int category, int badness, int goodness, int postbadness, TreeAction action) {
        this.setFlag(flag);
        this.setCategory(category);
        this.setBadness(badness);
        this.setGoodness(goodness);
        this.setPostbadness(postbadness);
        this.setAction(action);
    }

    /**
     * Create a TreeFlag object with integer flag value and action to execute
     * upon match
     *
     * @param flag
     *            int flag value
     * @param action
     *            object which implements the TreeAction interface
     */
    public TreeFlag(int flag, TreeAction action) {
        this.setFlag(flag);
        this.setCategory(0);
        this.setBadness(0);
        this.setGoodness(0);
        this.setPostbadness(0);
        this.setAction(action);
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

    /**
     * @return the flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(int category) {
        this.category = category;
    }

    /**
     * @return the category
     */
    public int getCategory() {
        return category;
    }

    /**
     * @param badness the badness to set
     */
    public void setBadness(int badness) {
        this.badness = badness;
    }

    /**
     * @return the badness
     */
    public int getBadness() {
        return badness;
    }

    /**
     * @param goodness the goodness to set
     */
    public void setGoodness(int goodness) {
        this.goodness = goodness;
    }

    /**
     * @return the goodness
     */
    public int getGoodness() {
        return goodness;
    }

    /**
     * @param postbadness the postbadness to set
     */
    public void setPostbadness(int postbadness) {
        this.postbadness = postbadness;
    }

    /**
     * @return the postbadness
     */
    public int getPostbadness() {
        return postbadness;
    }

    /**
     * @param action the action to set
     */
    public void setAction(TreeAction action) {
        this.action = action;
    }

    /**
     * @return the action
     */
    public TreeAction getAction() {
        return action;
    }

    /**
     * Call the associated TreeAction's matchFound() method with the associated
     * Context, Tree, and TreeNode. Does nothing if there is no action set.
     *
     * @param c
     *            Context of the search
     * @param t
     *            Tree that the search was done with
     * @param n
     *            Node in the Tree that was the final match point
     */
    public void callAction(Context c, Tree t, TreeNode n) {
        if (getAction() != null) {
            getAction().matchFound(c, t, n);
        }
    }
}
