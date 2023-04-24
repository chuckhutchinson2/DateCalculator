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
        FileOutputStream fileOutputStream = new FileOutputStream("images.zip");

        zipService = new ZipServiceImpl(fileOutputStream);
        zipService.archive("images", Arrays.asList("*.jpg"));
    }
}
