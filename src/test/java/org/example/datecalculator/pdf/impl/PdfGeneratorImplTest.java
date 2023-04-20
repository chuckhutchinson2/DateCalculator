package org.example.datecalculator.pdf.impl;


import lombok.extern.slf4j.Slf4j;
import org.example.calendar.service.impl.CalendarServiceImpl;
import org.example.datecalculator.config.ThymeLeafConfig;
import org.example.calendar.model.Month;
import org.example.datecalculator.pdf.impl.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.io.File;
import java.time.DayOfWeek;
import java.util.*;


@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@Slf4j
public class PdfGeneratorImplTest {

    private PdfGeneratorImpl pdfGenerator;

    @Before
    public void before() {
        ThymeLeafConfig thymeLeafConfig = new ThymeLeafConfig();
        ITemplateResolver iTemplateResolver = thymeLeafConfig.thymeleafTemplateResolver();
        pdfGenerator = new PdfGeneratorImpl(thymeLeafConfig.thymeleafTemplateEngine(iTemplateResolver));
    }

    @Test
    public void testGeneratePdf() {
        List<Product> products = new ArrayList<>();
        products.add(createProduct("INTC", "8088", "8088 processor"));
        products.add(createProduct("INTC", "80286", "80286 processor"));
        products.add(createProduct("INTC", "80386", "80386 processor"));
        products.add(createProduct("INTC", "80486", "80486 processor"));
        Map<String, Object> model = new HashMap<>();
        model.put("products" ,products);
        File file = pdfGenerator.generatePDF("pdf/products.pdf", "products.html", model);

        log.info("File {}", file.getAbsolutePath());
    }

    private Product createProduct(String vendor, String name, String description) {
        Product product = new Product();
        product.setVendor(vendor);
        product.setName(name);
        product.setDescription(description);
        return product;
    }
}
