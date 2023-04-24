package org.example.datecalculator.zip.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.datecalculator.zip.ZipService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
@Slf4j
public class ZipServiceImpl implements ZipService {
    private ZipOutputStream zipOutputStream;
    private File basePath;
    private int depth = 0;
    public ZipServiceImpl(OutputStream outputStream) throws FileNotFoundException  {
        zipOutputStream = new ZipOutputStream(outputStream);
    }
    @Override
    public void archive(String path, String[] extensions) throws IOException {
        archive(new File(path), extensions);
    }

    @Override
    public void archive(File directory, String[] extensions) throws IOException {

        if (depth == 0) {
            basePath = directory;
        }
        depth++;

        FilenameFilter filenameFilter = ((dir, name) -> dir.isDirectory() || checkName(name, extensions));

        File[] files = directory.listFiles(filenameFilter);
        for (File file : files) {
            log.info("file {}", file);

            if (file.isDirectory()) {
                archive(file, extensions);
            } else {
                addFile(file);
            }
        }

        depth--;

        if (depth == 0) {
            zipOutputStream.close();
        }
    }

    int BUFFER = 1024*1024;

    void addFile(File file) throws IOException {

        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis, BUFFER);

        String relativePath = file.getAbsolutePath().replace(basePath.getAbsolutePath(), "");

        log.info("relative path: {}", relativePath);
        ZipEntry zipEntry = new ZipEntry(relativePath);

        zipOutputStream.putNextEntry(zipEntry);

        byte data[] = new byte[BUFFER];
        int count;
        while((count = bis.read(data, 0, BUFFER)) != -1)
        {
            zipOutputStream.write(data, 0, count);
        }

        zipOutputStream.closeEntry();
    }

    boolean checkName(String name, String[] extensions) {
        if (extensions == null) {
            return true;
        }
        for (String extension : extensions) {
            if (name.endsWith(extension)) {
                return true;
            }
        }

        return false;
    }
}
