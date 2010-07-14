/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.internetfilter.if2k.kernel;

import com.internetfilter.if2k.kernel.TreeNode;
import com.internetfilter.if2k.kernel.TreeFlag;
import com.internetfilter.if2k.kernel.TreeUrl;
import com.internetfilter.if2k.kernel.PatternExpanderUrl;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


/**
 *
 * @author jeffk
 */
public class PatternExpanderUrlTest {


    /**
     * Test of add method, of class PatternExpanderUrl.
     */
    @Test
    public void testAddRemove() {
        System.out.println("addRemove");

        PatternExpanderUrl pe;
        TreeUrl tree = new TreeUrl();
        pe = new PatternExpanderUrl(tree);

        String prefix = "";
        TreeFlag flags = new TreeFlag(1);

        // add some items
        
        String items[] = {
            "http://www.cnn.com/",
            "http://www.internetfilter.com/",
            "http://www.jdkoftinoff.com/"
        };

        for( String u : items ) {
            pe.add(u,flags);
        }

        // find the items
        TreeNode r;

        for( String u : items ) {
            r = tree.findLongest(u);
            if( r==null )
                throw new Error("error finding " + u);
            System.out.println( "Did find " + u );

        }

        // remove the items
        for( String u : items ) {
            System.out.print("Removing " + u );

            if( pe.remove(u) ) {
                System.out.println(" Success" );
            }
            else {
                System.out.println( "Failed" );
            }
        }

        // make sure the items are gone
        for( String u : items ) {
            r = tree.findLongest(u);
            if( r!=null )
                throw new Error("error not finding " + u);
            System.out.println( "Did not find " + u );
        }
    }


}
