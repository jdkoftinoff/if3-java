package com.internetfilter.if2k.kernel;

import java.io.BufferedReader;
import java.io.IOException;

public class TreeLoader {

    public void load(BufferedReader reader, PatternExpander pattern_expander, String prefix, TreeFlag flag) throws IOException {
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.length() > 4) {
                pattern_expander.add(prefix, line, flag);
            }
        }
    }
}
