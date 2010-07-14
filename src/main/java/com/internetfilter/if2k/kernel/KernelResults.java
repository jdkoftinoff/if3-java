package com.internetfilter.if2k.kernel;

import java.util.LinkedList;

public class KernelResults implements ScannerTarget {

    private LinkedList<TreeNode> matches;
    private LinkedList<Integer> badCategories;
    private LinkedList<Integer> goodCategories;
    private LinkedList<Integer> postbadCategories;
    private int totalBadness;
    private int totalGoodness;
    private int totalPostbad;

    public KernelResults() {
        this.matches = new LinkedList<TreeNode>();
        this.badCategories = new LinkedList<Integer>();
        this.goodCategories = new LinkedList<Integer>();
        this.postbadCategories = new LinkedList<Integer>();
        this.totalBadness = 0;
        this.totalGoodness = 0;
        this.totalPostbad = 0;
    }

    /**
     * @param bad_categories the bad_categories to set
     */
    public void setBadCategories(LinkedList<Integer> badCategories) {
        this.badCategories = badCategories;
    }

    /**
     * @return the badCategories
     */
    public LinkedList<Integer> getBadCategories() {
        return badCategories;
    }

    /**
     * @param goodCategories the goodCategories to set
     */
    public void setGoodCategories(LinkedList<Integer> goodCategories) {
        this.goodCategories = goodCategories;
    }

    /**
     * @return the goodCategories
     */
    public LinkedList<Integer> getGoodCategories() {
        return goodCategories;
    }

    /**
     * @param postbadCategories the postbadCategories to set
     */
    public void setPostbadCategories(LinkedList<Integer> postbadCategories) {
        this.postbadCategories = postbadCategories;
    }

    /**
     * @return the postbadCategories
     */
    public LinkedList<Integer> getPostbadCategories() {
        return postbadCategories;
    }

    /**
     * @param totalBadness the totalBadness to set
     */
    public void setTotalBadness(int totalBadness) {
        this.totalBadness = totalBadness;
    }

    /**
     * @return the totalBadness
     */
    public int getTotalBadness() {
        return totalBadness;
    }

    /**
     * @param totalGoodness the totalGoodness to set
     */
    public void setTotalGoodness(int totalGoodness) {
        this.totalGoodness = totalGoodness;
    }

    /**
     * @return the totalGoodness
     */
    public int getTotalGoodness() {
        return totalGoodness;
    }

    @Override
    public void matchFound(Scanner s, TreeNode match_item) {

        // add the match item to our matches list
        this.matches.add(match_item);

        TreeFlag f = match_item.getFlags();

        this.totalBadness += f.getBadness();
        this.totalGoodness += f.getGoodness();
        this.totalPostbad += f.getPostbadness();

        // if the match is marked bad, add the category to our bad category list
        if (f.getBadness() > 0) {
            this.badCategories.add(f.getCategory());
        }

        // if the match is marked good, add the category to our good category list
        if (f.getGoodness() > 0) {
            this.goodCategories.add(f.getCategory());
        }

        // if the match is marked postbad, add the category to our postbad category list
        if (f.getPostbadness() > 0) {
            this.postbadCategories.add(f.getCategory());
        }

    }

    /**
     * @param matches the matches to set
     */
    public void setMatches(LinkedList<TreeNode> matches) {
        this.matches = matches;
    }

    /**
     * @return the matches
     */
    public LinkedList<TreeNode> getMatches() {
        return matches;
    }

    /**
     * @param totalPostbad the totalPostbad to set
     */
    public void setTotalPostbad(int totalPostbad) {
        this.totalPostbad = totalPostbad;
    }

    /**
     * @return the totalPostbad
     */
    public int getTotalPostbad() {
        return totalPostbad;
    }
}
