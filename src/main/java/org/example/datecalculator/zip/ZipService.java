package org.example.datecalculator.zip;

import java.io.File;
import java.io.IOException;

public interface ZipService {

    void archive(String path, String[] extensions) throws IOException;
    void archive(File directory, String[] extensions) throws IOException;
}
