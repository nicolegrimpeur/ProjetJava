package files;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

public abstract class ImpressionManager {
    /**
     * Permet d'imprimer un fichier pdf
     * @param path le chemin vers le fichier pdf
     */
    public static void imprimerFichier(String path) {
        try {
            PDDocument document = PDDocument.load(new File(path));
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));

            if (job.printDialog()) {
                job.print();
            }
        } catch (IOException | PrinterException e) {
            throw new RuntimeException(e);
        }
    }
}
