package org.example.datecalculator.pdf.impl;

import com.lowagie.text.DocumentException;
import org.example.datecalculator.pdf.PdfGenerator;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Map;

@Component
public class PdfGeneratorImpl implements PdfGenerator {
    private final SpringTemplateEngine thymeleafTemplateEngine;

    public PdfGeneratorImpl(SpringTemplateEngine thymeleafTemplateEngine) {
        this.thymeleafTemplateEngine = thymeleafTemplateEngine;
    }

    @Override
    public void generatePDF(OutputStream outputStream, String templateName, Map<String, Object> templateModel) {
        try {
            String html = generateHtml(templateName, templateModel);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public File generatePDF(String filename, String templateName, Map<String, Object> templateModel) {
        try {
            File file = new File(filename);

            OutputStream outputStream = new FileOutputStream(file);

            generatePDF(outputStream, templateName, templateModel);

            outputStream.close();

            return file;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    String generateHtml(String templateName, Map<String, Object> templateModel) {
        Context thymeleafContext = new Context();
        thymeleafContext.setVariables(templateModel);
        return thymeleafTemplateEngine.process(templateName, thymeleafContext);
    }
}
