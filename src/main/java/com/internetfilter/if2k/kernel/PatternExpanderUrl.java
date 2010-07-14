package com.internetfilter.if2k.kernel;

/**
 * 
 * @author jeffk
 */
public class PatternExpanderUrl extends PatternExpanderPhrase {

    Ignorer ignorer;
    String common_prefix;

    public PatternExpanderUrl(PatternExpanderTarget target) {
        super(target);
        ignorer = new IgnorerWhiteSpace();
        //common_prefix = "[,www.]";
        common_prefix = "";
    }

    public PatternExpanderUrl() {
        this(null);
    }

    @Override
    public void add(String prefix, String pattern, TreeFlag flags) {
        super.add(common_prefix + fixUrlString(prefix), fixUrlString(pattern), flags);
    }

    @Override
    public boolean remove(String prefix, String pattern ) {
        return super.remove(common_prefix + fixUrlString(prefix), fixUrlString(pattern) );
    }

    private String fixUrlString(String s) {
        String result = "";
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (!ignorer.isIgnored(s.charAt(i))) {
                result += c;
            }
        }
        return result;
    }
}
