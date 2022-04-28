package html;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Collection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class HtmlManager {
    private static final String HTML_INPUT = "src/main/resources/htmlTemplate/factureTemplate.html";
    private static final String PDF_OUTPUT = "Factures/html2pdf.pdf";

    public void generateHtmlToPdf() throws IOException {
        File inputHTML = new File(HTML_INPUT);
        Document doc = createWellFormedHtml(inputHTML);
        xhtmlToPdf(doc, PDF_OUTPUT);
    }

    private Document createWellFormedHtml(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);

        Element numero = document.getElementById("numeroFacture");
        numero.appendText(" 555");

        Element table = document.getElementById("tableau");
        Element ligne = new Element("tr");
        Element colonne1 = new Element("td");
        colonne1.appendText("Je");
        Element colonne2 = new Element("td");
        colonne2.appendText("Suis");
        Element colonne3 = new Element("td");
        colonne3.appendText("Une");
        Element colonne4 = new Element("td");
        colonne4.appendText("Ligne");

        ArrayList<Node> tabLigne = new ArrayList<>();
        tabLigne.add(colonne1);
        tabLigne.add(colonne2);
        tabLigne.add(colonne3);
        tabLigne.add(colonne4);

        ligne.appendChildren(tabLigne);
        table.appendChild(ligne);

        return document;
    }

    private void xhtmlToPdf(Document doc, String outputPdf) throws IOException {
        try (OutputStream os = new FileOutputStream(outputPdf)) {
            String baseUri = FileSystems.getDefault()
                    .getPath("src/main/resources/")
                    .toUri()
                    .toString();
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.withUri(outputPdf);
            builder.toStream(os);
            builder.withW3cDocument(new W3CDom().fromJsoup(doc), baseUri);
            builder.run();
        }
    }
}
