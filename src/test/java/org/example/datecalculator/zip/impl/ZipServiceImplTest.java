package org.example.datecalculator.zip.impl;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class ZipServiceImplTest {

    ZipServiceImpl zipService;

    @Before
    public void setup () throws FileNotFoundException {

        FileOutputStream fileOutputStream = new FileOutputStream("images.zip");

        zipService = new ZipServiceImpl(fileOutputStream);
    }

    @Test
    public void testZip() throws IOException {
        zipService.archive("images", new String[] {"jpg"});
    }

}
