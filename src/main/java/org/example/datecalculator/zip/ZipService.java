package org.example.datecalculator.zip;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ZipService {

    void archive(String path, List wildcards) throws IOException;
    void archive(File directory, List wildcards) throws IOException;

    void archive(String archiveName, String directory, List wildcards) throws IOException;
    public void close() throws IOException;
}
