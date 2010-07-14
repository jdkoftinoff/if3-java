package com.internetfilter.if2k.kernel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;

import org.apache.commons.logging.*;

/**
 * 
 * @author jeffk
 */
public class Kernel {    

    private Log log = LogFactory.getLog(Kernel.class);
    private Scanner bad_url_scanner;
    private Scanner postbad_url_scanner;
    private Scanner bad_phrase_scanner;
    private Scanner good_url_scanner;
    private FileReadManager file_manager;

    /**
     * Constructor for Kernel
     *
     * Create a kernel with the specified FileReadManager that will be used to read settings and
     * phrase files
     * 
     * @param file_requestor
     */
    public Kernel(FileReadManager file_requestor) {
        this.file_manager = file_requestor;
        this.bad_url_scanner = new ScannerPhrase(new TreeLoader());
        this.postbad_url_scanner = new ScannerPhrase(new TreeLoader());
        this.bad_phrase_scanner = new ScannerPhrase(new TreeLoader());
        this.good_url_scanner = new ScannerGoodUrl(new TreeLoader());
    }


    /**
     * Load a file into a scanner, make each entry have the specified flag
     *
     * @param scanner
     * @param fname
     * @param flag
     */
    private void loadScanner(Scanner scanner, String fname, TreeFlag flag) {
        if (file_manager != null) {
            try {
                BufferedReader br = file_manager.openFileNamed(fname);
                if (br != null) {
                    scanner.load(
                            br,
                            "",
                            flag);
                }
            } catch (FileNotFoundException e) {
                log.error("File not found for : " + fname);
            } catch (IOException e) {
                log.error("IO Exception for : " + fname, e);
            }
        }
    }


    /**
     * Initialize and load all the scanners from files
     * 
     */
    public void init() {
        
        for (int cat = 0; cat < 15; ++cat) {
            loadScanner(bad_url_scanner, "" + cat + "badurl.txt", new TreeFlag(1, cat, 1, 0, 0));
            loadScanner(postbad_url_scanner, "" + cat + "postbadurl.txt", new TreeFlag(2, cat, 0, 0, 1));
            loadScanner(good_url_scanner, "" + cat + "goodurl.txt", new TreeFlag(3, cat, 0, 1, 0));
            loadScanner(bad_phrase_scanner, "" + cat + "badphr.txt", new TreeFlag(4, cat, 1, 0, 0));
        }
    }

    public void addBadUrl( String url, int cat ) {
        bad_url_scanner.getPatternExpander().add("", url, new TreeFlag(1,cat,1,0,0) );
        log.info("Kernel added " + url + " to category " + cat + " bad url" );
    }

    public void addPostBadUrl( String url, int cat ) {
        postbad_url_scanner.getPatternExpander().add("",url, new TreeFlag(2,cat,0,0,1) );
        log.info("Kernel added " + url + " to category " + cat + " postbad url" );
    }

    public void addGoodUrl( String url, int cat ) {
        good_url_scanner.getPatternExpander().add("",url, new TreeFlag(3,cat,0,1,0) );
        log.info("Kernel added " + url + " to category " + cat + " good url" );
    }

    public void removeBadUrl( String url, int cat ) {
        bad_url_scanner.getPatternExpander().remove("", url );
        log.info("Kernel added " + url + " to category " + cat + " bad url" );
    }

    public void removePostBadUrl( String url, int cat ) {
        postbad_url_scanner.getPatternExpander().remove("",url);
        log.info("Kernel added " + url + " to category " + cat + " postbad url" );
    }

    public void removeGoodUrl( String url, int cat ) {
        good_url_scanner.getPatternExpander().remove("",url);
        log.info("Kernel added " + url + " to category " + cat + " good url" );
    }

    public int quantifyResultsForUrlData(KernelResults text_results, KernelResults link_results) {
        
        int result = 0;
        Boolean is_good = false;
        Boolean is_bad = false;
        Boolean is_unknown = true;

        if (link_results.getTotalBadness() > 0) {
            is_bad = true;
            is_good = false;
            is_unknown = false;
        }

        if (link_results.getTotalPostbad() > 0 || link_results.getTotalPostbad() > 0) {
            is_bad = true;
            is_good = false;
            is_unknown = false;
        }

        if (text_results.getTotalBadness() > 0) {
            is_bad = true;
            is_good = false;
            is_unknown = false;
        }

        if (text_results.getTotalPostbad() > 0) {
            is_bad = true;
            is_good = false;
            is_unknown = false;
        }


        if (is_bad) {
            result = -1;
        }
        if (is_good) {
            result = 1;
        }

        return result;

    }

    class MyTarget implements ScannerTarget {

        LinkedList<TreeNode> matches;

        MyTarget() {
            matches = new LinkedList<TreeNode>();
        }

        LinkedList<TreeNode> getMatches() {
            return matches;
        }

        @Override
        public void matchFound(Scanner s, TreeNode match_item) {
            matches.add(match_item);
        }
    }

    public KernelResults searchAll(String buf) {
        KernelResults target = new KernelResults();

        bad_url_scanner.scanBuffer(buf, target);
        good_url_scanner.scanBuffer(buf, target);
        postbad_url_scanner.scanBuffer(buf, target);
        bad_phrase_scanner.scanBuffer(buf, target);

        return target;
    }

    public KernelResults searchHostNameOfUrl(String url_string) throws MalformedURLException {
        KernelResults target = new KernelResults();

        URL url = null;
        String host = null;

        try {
            url = new URL(url_string);
        } catch (MalformedURLException e) {
            url = new URL("http://" + url_string);
        }

        host = url.getHost();

        bad_url_scanner.scanBuffer(host, target);
        //good_url_scanner.scanBuffer(host, target);
        postbad_url_scanner.scanBuffer(host, target);
        bad_phrase_scanner.scanBuffer(host, target);

        return target;
    }

    public int quantifyResultsForUrl(String url_to_test) throws MalformedURLException {
        KernelResults url_results = searchAll(url_to_test);

        KernelResults hostname_results = searchHostNameOfUrl(url_to_test);

        return quantifyResultsForUrl(url_to_test,hostname_results, url_results);
    }

    public int quantifyResultsForUrl(String url, KernelResults hostname_results,
            KernelResults url_results) {
        int result = 0;
        Boolean is_good = false;
        Boolean is_bad = false;
        Boolean is_unknown = true;

        // if the hostname is known bad then we are bad
        if (hostname_results.getTotalBadness() > 0) {
            is_bad = true;
            is_good = false;
            is_unknown = false;
        }

        // if the hostname is known good then we are good
        if (hostname_results.getTotalGoodness() > 0) {
            is_good = true;
            is_bad = false;
            is_unknown = false;
        }

        // if the hostname or the full url is known postbad then we are bad
        if (hostname_results.getTotalPostbad() > 0 || url_results.getTotalPostbad() > 0) {
            is_bad = true;
            is_good = false;
            is_unknown = false;
        }

        // if the site is not yet known then check the rest of the url
        if (is_unknown) {
            // if the rest of the url is known bad then we are bad
            if (url_results.getTotalBadness() > 0) {
                is_bad = true;
                is_good = false;
                is_unknown = false;
            }

            // if the rest of the url is known postbad then we are bad
            if (url_results.getTotalPostbad() > 0) {
                is_bad = true;
                is_good = false;
                is_unknown = false;
            }

            if (url_results.getTotalGoodness() > 0) {
                for( TreeNode i : url_results.getMatches() ) {
                    TreeFlag f = i.getFlags();

                    if( f.getGoodness()>0 )
                    {
                        String extracted = i.extractWord();

                        if (
                                url.equals(extracted) ||
                                url.equals("http://" + extracted) ||
                                url.equals("http://www." + extracted) ) {
                            is_bad = false;
                            is_good = true;
                            is_unknown = false;
                            break;
                        }
                    }
                }
            }                        
        }

        if (is_bad) {
            result = -1;
        }
        if (is_good) {
            result = 1;
        }

        return result;
    }
}
