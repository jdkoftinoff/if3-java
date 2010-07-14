package com.internetfilter.if2k.kernel;

import java.util.LinkedList;
import java.util.ListIterator;

public class PatternExpanderPhraseSection {

    LinkedList<String> strings;
    PatternExpanderPhraseSection next;

    public PatternExpanderPhraseSection() {
        super();
        strings = new LinkedList<String>();
        next = null;
    }

    public void clear() {
        strings.clear();
        next = null;
    }

    public PatternExpanderPhraseSection getNext() {
        return next;
    }

    public LinkedList<String> getStrings() {
        return strings;
    }

    public void addSection(PatternExpanderPhraseSection section) {
        if (next == null) {
            next = section;
        } else {
            next.addSection(section);
        }
    }

    public void addString(String s) {
        strings.add(s);
    }

    public void expandAllToTarget(String prefix, PatternExpanderTarget target,
            TreeFlag flag) {
        // do we have any strings to contribute to the phrase?
        if (strings.size() > 0) {
            // yes, iterate through them
            ListIterator<String> s_it = strings.listIterator();
            while (s_it.hasNext()) {
                // get the string but prefix it with prefix
                String v = prefix + s_it.next();
                // add the resultant string to the target
                addToTarget(v, target, flag);
            }
        } else {
            addToTarget(prefix, target, flag);
        }
    }

    private void addToTarget(String s, PatternExpanderTarget target, TreeFlag flag) {
        // no, do we have a section after us?
        if (next != null) {
            // yes, give the next section this request
            next.expandAllToTarget(s, target, flag);
        } else {
            // no, tell the target to add this string.
            target.add(s, flag);
        }

    }

    public boolean expandAllToTargetRemove(String prefix, PatternExpanderTarget target) {
        boolean r=false;
        // do we have any strings to contribute to the phrase?
        if (strings.size() > 0) {
            // yes, iterate through them
            ListIterator<String> s_it = strings.listIterator();
            while (s_it.hasNext()) {
                // get the string but prefix it with prefix
                String v = prefix + s_it.next();
                // add the resultant string to the target
                r |= removeFromTarget(v, target);
            }
        } else {
            r|=removeFromTarget(prefix, target);
        }
        return r;
    }

    private boolean removeFromTarget(String s, PatternExpanderTarget target) {
        boolean r = false;
        // no, do we have a section after us?
        if (next != null) {
            // yes, give the next section this request
            r|=next.expandAllToTargetRemove(s, target);
        } else {
            // no, tell the target to add this string.
            r|=target.remove(s);
        }
        return r;
    }

}
