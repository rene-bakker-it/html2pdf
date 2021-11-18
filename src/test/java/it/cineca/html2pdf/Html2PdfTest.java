package it.cineca.html2pdf;

import java.io.IOException;

public class Html2PdfTest extends Html2Pdf {

    public static void main(String... argv) {
        Html2PdfTest html2pdf = new Html2PdfTest();
        html2pdf.clean = true;
        html2pdf.input = "https://news.bbc.co.uk";
        html2pdf.output = "html2pdf_test.pdf";
        html2pdf.encoding = "utf-8";
        try {
            html2pdf.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
