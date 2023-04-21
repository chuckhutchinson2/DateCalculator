package org.example.datecalculator.pdf;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

public interface PdfGenerator {

    void generatePDF(OutputStream outputStream, String templateName, Map<String, Object> templateModel);

    File generatePDF(String filename, String templateName, Map<String, Object> templateModel);
}
