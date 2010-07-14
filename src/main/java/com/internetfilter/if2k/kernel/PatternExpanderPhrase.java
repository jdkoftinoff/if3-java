package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class PatternExpanderPhrase extends PatternExpander {

    PatternExpanderPhraseSection sections;

    public PatternExpanderPhrase(PatternExpanderTarget target) {
        super(target);
        sections = new PatternExpanderPhraseSection();
    }

    public PatternExpanderPhrase() {
        this(null);
    }

    @Override
    public void add(String prefix, String pattern, TreeFlag flags) {

        sections.clear();

        String s = prefix + pattern;
        if (s.length() > 0) {
            if (s.charAt(0) == '[') {

                addSections(s);

                // now iterate through all combinations of the section list
                // and call target().add() with them.

                iterateAllCombinations(flags);

            } else {
                // no optional sections here, just add the string
                getTarget().add(s, flags);
            }
        }
    }

    @Override
    public boolean remove(String prefix, String pattern ) {

        sections.clear();

        String s = prefix + pattern;
        if (s.length() > 0) {
            if (s.charAt(0) == '[') {

                addSections(s);

                // now iterate through all combinations of the section list
                // and call target().add() with them.

                return iterateAllCombinationsRemove();
            } else {
                // no optional sections here, just remove the string
                return getTarget().remove(s);
            }
        }
        return true;
    }

    private void iterateAllCombinations(TreeFlag flags) {
        sections.expandAllToTarget("", getTarget(), flags);
    }

    private boolean iterateAllCombinationsRemove() {
        return sections.expandAllToTargetRemove("", getTarget() );
    }

    private void addSections(String s) {
        // if the phrase starts with an open bracket, then there are
        // optional sections separated by commas. Get the whole bracketed text and
        // pass it to addSection and repeat.
        String cur = "";
        Boolean in_section = false;
        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == '[') {
                // '[' found, this is the beginning of a section
                in_section = true;
            } else if (in_section == true && s.charAt(i) == ']') {
                // ']' found while in a section, so submit sections to addSection()
                addSectionEntries(cur);
                // clear cur string
                cur = "";
                // no longer in brackets
                in_section = false;
            } else {
                cur += s.charAt(i);
            }
        }
        addSectionEntries(cur);
    }

    private void addSectionEntries(String s) {

        // create a new section
        PatternExpanderPhraseSection section = new PatternExpanderPhraseSection();

        // create a new string to put in the section
        String cur = "";

        // go through the text, grabbing characters and adding strings to
        // the section when ',' is found

        for (int i = 0; i < s.length(); ++i) {
            if (s.charAt(i) == ',') {
                // found a ',' so submit the built string to the section.
                section.addString(cur);
                cur = "";
            } else {
                // no ',' yet, so we are building a string
                cur += s.charAt(i);
            }
        }
        // cur contains the last string built without ending with ',' so
        // we need to add it here

        section.addString(cur);

        // append this entire section to the end of our section list
        sections.addSection(section);
    }
}
