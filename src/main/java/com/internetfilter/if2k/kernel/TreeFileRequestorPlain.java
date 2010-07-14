package com.internetfilter.if2k.kernel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class TreeFileRequestorPlain implements FileReadManager {

    String fname_prefix;

    public TreeFileRequestorPlain(String fname_prefix) {
        this.fname_prefix = fname_prefix;
    }

    public TreeFileRequestorPlain() {
        this("");
    }

    @Override
    public BufferedReader openFileNamed(String fname) throws FileNotFoundException {
        return new BufferedReader(new FileReader(fname_prefix + fname));
    }
}
