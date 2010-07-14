package com.internetfilter.if2k.kernel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;

public interface FileReadManager {

    public BufferedReader openFileNamed(String fname) throws FileNotFoundException;
}
