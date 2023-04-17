package org.example.datecalculator.service.impl;

import org.junit.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileWriterTest {

    @Test
    public void givenWritingStringToFile_whenUsingPrintWriter_thenCorrect()
            throws IOException {
        String fileName = "test.dat";

        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print("Some String");
        printWriter.printf("Product name is %s and its price is %d $\n", "iPhone", 1000);
        printWriter.close();
    }

    @Test
    public void createZipFile() throws  IOException {

        int BUFFER = 1024;

        String fileName = "test.dat";
        File file = new File(fileName);
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis, BUFFER);

        FileOutputStream fileOutputStream = new FileOutputStream("file.zip");

        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        ZipEntry zipEntry = new ZipEntry(file.getName());
        zipOutputStream.putNextEntry(zipEntry);

        byte data[] = new byte[BUFFER];
        int count;
        while((count = bis.read(data, 0, BUFFER)) != -1)
        {
            zipOutputStream.write(data, 0, count);
        }

        zipOutputStream.close();
    }
}
