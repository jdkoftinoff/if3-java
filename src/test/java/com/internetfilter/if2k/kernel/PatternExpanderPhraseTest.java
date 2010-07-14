/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.internetfilter.if2k.kernel;

import com.internetfilter.if2k.kernel.TreeFlag;
import com.internetfilter.if2k.kernel.PatternExpanderPhrase;
import com.internetfilter.if2k.kernel.PatternExpanderTargetDumper;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author jeffk
 */
public class PatternExpanderPhraseTest {

    PatternExpanderPhrase pe;

    @BeforeClass
    public void setUp() {
        pe = new PatternExpanderPhrase(
                new PatternExpanderTargetDumper());
    }

    @AfterClass
    public void cleanUp() {
        pe = null;
    }

    /**
     * Test method
     */
    @Test
    public void testAddStringStringFlag() {
        pe.add("[,JEFF:]", "TEST", new TreeFlag(98));
        pe.add("[JEFF:]", "[1,2,3][a,b,c][X,Y,Z]", new TreeFlag(99));
        pe.add("[,www.]", "[jdkoftinoff,statusbar][.com][/simon,/isabelle,/christina,/ada]", new TreeFlag(100));
    }
}
