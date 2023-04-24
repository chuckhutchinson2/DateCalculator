package org.example.datecalculator.zip;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ZipService {

    void archive(String path, List wildcards) throws IOException;
    void archive(File directory, List wildcards) throws IOException;
}
