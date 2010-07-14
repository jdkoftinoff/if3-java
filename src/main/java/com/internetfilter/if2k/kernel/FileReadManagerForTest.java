
package com.internetfilter.if2k.kernel;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.FileNotFoundException;

public class FileReadManagerForTest implements FileReadManager {

  private String goodurl1;
  private String badurl1;
  private String postbadurl1;
  private String badphr1;

  public FileReadManagerForTest(
      String goodurl1,
      String badurl1,
      String postbadurl1,
      String badphr1
      ) {
    this.goodurl1 = goodurl1;
    this.badurl1 = badurl1;
    this.postbadurl1 = postbadurl1;
    this.badphr1 = badphr1;
  }
  
  public FileReadManagerForTest() {
    this(
      "www.google.com\n",
      "www.badsite.com\n",
      "www.postbadsite.com\n",
      "[this is a ][,really][,fun][,stupid][bad phrase]\n"
      );
  }
  
  @Override
  public BufferedReader openFileNamed(String fname) throws FileNotFoundException {
    BufferedReader br = null;
    if( fname.equals("1goodurl.txt") ) {
      br = new BufferedReader( new StringReader( goodurl1 ) );
    }
    else if( fname.equals("1badurl.txt") ) {
      br = new BufferedReader( new StringReader( badurl1 ) );
    }
    else if( fname.equals("1postbadurl.txt") ) {
      br = new BufferedReader( new StringReader( postbadurl1 ) );
    }
    else if( fname.equals("1badphr.txt") ) {
      br = new BufferedReader( new StringReader( badphr1 ) );
    }

    return br;
  }
}
