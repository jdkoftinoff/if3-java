
package com.internetfilter.if2k.kernel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileReadManagerFromWeb implements FileReadManager {
  
  private String base_url;
  private String auth_user;
  private String auth_pw;
  
  public FileReadManagerFromWeb(
    String base_url,
    String auth_user,
    String auth_pw
    ) {
    this.base_url = base_url;
    this.auth_user = auth_user;
    this.auth_pw = auth_pw;
  }
  
  public FileReadManagerFromWeb() {
    this(
      "http://db-test.internetfilter.com/db/read?f=",
      "testuser",
      "superduper"
      );
  }

  @Override
  public BufferedReader openFileNamed(String fname) throws FileNotFoundException {
    BufferedReader br = null;

    try {
      URL url = new URL( this.base_url + fname );
      URLConnection url_connection = url.openConnection();
      br = new BufferedReader( new InputStreamReader(url_connection.getInputStream()));
    } catch (MalformedURLException ex) {
      Logger.getLogger(FileReadManagerFromWeb.class.getName()).log(Level.SEVERE, null, ex );
    } catch (IOException ex) {
      Logger.getLogger(FileReadManagerFromWeb.class.getName()).log(Level.SEVERE, null, ex );
    }
    return br;
  }
}

