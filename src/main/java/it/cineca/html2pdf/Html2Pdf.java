package it.cineca.html2pdf;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class Html2Pdf {

    @Parameter(names = {"--input", "-i"}, description = "The html input file")
    private String input;

    @Parameter(names = {"output", "-o"}, description = "The pdf output file")
    private String output;

    @Parameter(names = {"--clean", "-c"}, description = "Clean html with jSoup")
    private boolean clean = false;

    @Parameter(names = {"--encoding", "-e"}, description = "Clean html with jSoup")
    private String encoding = "utf-8";

    public static void main(String... argv) {
        Html2Pdf html2pdf = new Html2Pdf();
        JCommander.newBuilder()
                .addObject(html2pdf)
                .build()
                .parse(argv);
        try {
            html2pdf.run();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void run() throws IOException {
        if (null == input) {
            throw new IOException("Input file is not specified");
        }
        if (null == output) {
            throw new IOException("Output file is not specified");
        }

        Document jsoupDocument = null;
        if (input.startsWith("http://") || input.startsWith("https://")) {
            try {
                jsoupDocument = Jsoup.connect(input).get();
            } catch (Exception e) {
                throw new IOException(String.format("ERROR retrieving url %s (%s): %s",
                        input, e.getClass().getSimpleName(), e.getMessage()));
            }
        } else {
            File file = new File(input);
            if (!file.exists()) {
                throw new IOException("Input file not found: " + input);
            }
            if (clean) {
                if (!Charset.isSupported(encoding)) {
                    encoding = "UTF-8";
                }
                try {
                    jsoupDocument = Jsoup.parse(Files.readString(file.toPath(), Charset.forName(encoding)));
                } catch (Exception e) {
                    throw new IOException(String.format("ERROR parsing file %s (%s): %s",
                            input, e.getClass().getSimpleName(), e.getMessage()));
                }
            }
        }

        try (OutputStream os = new FileOutputStream(output)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            if (null != jsoupDocument) {
                builder.withUri(output);
                builder.withW3cDocument(new W3CDom().fromJsoup(jsoupDocument), "/");
            } else {
                builder.withFile(new File(input));
            }
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            throw new IOException("Parsing error: " + e.getMessage());
        }
    }
}
