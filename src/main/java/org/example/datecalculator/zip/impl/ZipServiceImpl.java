package org.example.datecalculator.zip.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.OrFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.example.datecalculator.zip.ZipService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Slf4j
public class ZipServiceImpl implements ZipService {
    int BUFFER = 1024*1024;

    private ZipOutputStream zipOutputStream;
    private File basePath;
    private int depth = 0;
    public ZipServiceImpl(OutputStream outputStream) {
        zipOutputStream = new ZipOutputStream(outputStream);
    }
    public ZipServiceImpl() {
    }

    @Override
    public void archive(String archiveName, String path, List wildcards) throws IOException {
        depth = 0;
        FileOutputStream fileOutputStream = new FileOutputStream(archiveName);
        zipOutputStream = new ZipOutputStream(fileOutputStream);
        archive(path, wildcards);
        close();
    }

    @Override
    public void archive(String path, List wildcards) throws IOException {
        archive(new File(path), wildcards);
    }

    @Override
    public void archive(File directory, List wildcards) throws IOException {

        if (depth == 0) {
            basePath = directory;
        }
        depth++;

        FileFilter fileFilter = FileFilterUtils.orFileFilter(
                DirectoryFileFilter.DIRECTORY,
                new WildcardFileFilter(wildcards, IOCase.INSENSITIVE)
        );

        File[] files = directory.listFiles(fileFilter);
        for (File file : files) {
            log.info("file {}", file);

            if (file.isDirectory()) {
                archive(file, wildcards);
            } else {
                addFile(file);
            }
        }

        depth--;
    }

    public void close() throws IOException {
        zipOutputStream.close();
    }

    void addFile(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis, BUFFER);

        String pathToIgnore = basePath.getAbsolutePath();
        if (!pathToIgnore.endsWith("/")) {
            pathToIgnore = pathToIgnore + "/";
        }

        String relativePath = file.getAbsolutePath().replace(pathToIgnore, "");

        String path = String.format("%s/%s", basePath.getName(), relativePath);

        log.info("path {} relative path: {}", path, relativePath);
        ZipEntry zipEntry = new ZipEntry(path);

        zipOutputStream.putNextEntry(zipEntry);

        byte data[] = new byte[BUFFER];
        int count;
        while((count = bis.read(data, 0, BUFFER)) != -1)
        {
            zipOutputStream.write(data, 0, count);
        }

        zipOutputStream.closeEntry();
    }
}
