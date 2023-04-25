package org.example.datecalculator.zip.impl;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class ZipServiceImplTest {

    ZipServiceImpl zipService;

    @Test
    public void testZipDirectory() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("zip/html.zip");

        zipService = new ZipServiceImpl(fileOutputStream);
        zipService.archive("html", Arrays.asList("*.html"));
        zipService.archive("images", Arrays.asList("*.jpg"));
        zipService.close();
    }


    @Test
    public void testZipDirectory1() throws IOException {
        zipService = new ZipServiceImpl();
        zipService.archive("zip/html2.zip", "html", Arrays.asList("*.html"));
        zipService.archive("zip/images.zip", "images", Arrays.asList("*.jpg"));
    }
}
