package html;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import journee.JourneeManager;
import menu.Menu;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class HtmlManager {
    private static HtmlManager instance = null;
    private static final String FACTURE_INPUT = "resources/htmlTemplate/factureTemplate.html";
    private static final String ADDITION_INPUT = "resources/htmlTemplate/additionTemplate.html";
    private static final String FACTURE_OUTPUT = "Factures/";
    private static final String ADDITION_OUTPUT = "Additions/";
    private static int nbFactures = 0;

    public static HtmlManager getInstance() {
        if (instance == null)
            instance = new HtmlManager();
        return instance;
    }

    /**
     * Permet de générer une facture dans le dossier défini par FACTURE_OUTPUT
     * @param serveur le nom d'affichage du serveur qui a servi la table
     */
    public void generateFacture(String serveur) {
        getLastNumberFacture();
        try {
            File inputHTML = new File(FACTURE_INPUT);
            Document doc = createWellFormedHtml(inputHTML, true, serveur);
            xhtmlToPdf(doc, FACTURE_OUTPUT + "Facture " + nbFactures++ + ".pdf");
        } catch (Exception ignored) {
        }
    }

    /**
     * Permet de récupérer le dernier numéro dans le dossier des factures
     */
    private void getLastNumberFacture() {
        File dossierFacture = new File("Factures/");
        String[] files = dossierFacture.list();

        int max = -1;
        String tmp;
        if (files != null)
            for (String file : files) {
                tmp = file.replace("Facture ", "").replace(".pdf", "");
                if (Integer.parseInt(tmp) > max)
                    max = Integer.parseInt(tmp);
            }

        nbFactures = ++max;
    }

    /**
     * Permet de générer l'addition d'une table dans le dossier défini par ADDITION_OUTPUT
     * @param serveur le nom d'affichage du serveur qui a servi la table
     */
    public void generateAddition(String serveur) {
        try {
            Date aujourdhui = new Date();

            SimpleDateFormat formater = new SimpleDateFormat(" HH'h'mm'm'ss's'");

            File inputHTML = new File(ADDITION_INPUT);
            Document doc = createWellFormedHtml(inputHTML, false, serveur);
            xhtmlToPdf(doc, ADDITION_OUTPUT + serveur + formater.format(aujourdhui) + ".pdf");
        } catch (Exception ignored) {
        }
    }

    /**
     * Permet de modifier le fichier HTML
     * @param inputHTML le fichier HTML récupéré
     * @param isFacture true si c'est une facture, false si c'est une addition
     * @param serveur nom d'affichage du serveur qui a servi
     * @return le document modifié
     */
    private Document createWellFormedHtml(File inputHTML, Boolean isFacture, String serveur) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);

        // si c'est une facture, on modifie le numéro de facture
        if (isFacture) {
            Element numero = document.getElementById("numeroFacture");
            numero.appendText(Integer.toString(nbFactures));
        }

        // on ajoute la date du jour
        DateFormat dateFormat = DateFormat.getDateTimeInstance(
                DateFormat.LONG,
                DateFormat.LONG, new Locale("FR", "fr"));
        Element date = document.getElementById("date");
        date.appendText(dateFormat.format(new Date()).replace("CEST", ""));

        // on ajoute le nom du serveur
        Element service = document.getElementById("service");
        service.appendText(serveur);

        // on parcourt les menus gérés par
        int nbMenus100Ans = 0, prixTotal = 0;
        ArrayList<Node> tabLigne;
        Element table, ligne, colonnePlat, colonneBoisson, colonnePrix;
        table = document.getElementById("tableau");
        for (Menu menu : JourneeManager.getInstance().getListService().get(serveur)) {
            // si l'on n'est pas un menu 100 ans
            if (!menu.getPrix().equals("")) {
                prixTotal += Integer.parseInt(menu.getPrix());
            } else {
                // si c'est un multiple de 7, on rajoute une ligne pour préciser que c'est un menu 100 ans
                if (nbMenus100Ans % 7 == 0) {
                    ligne = new Element("tr");
                    colonnePlat = new Element("td");
                    colonnePlat.appendChild((new Element("strong")).appendText("Menu 100 Ans"));
                    colonneBoisson = new Element("td");
                    colonneBoisson.appendText("");
                    colonnePrix = new Element("td");
                    colonnePrix.appendChild((new Element("strong")).appendText("100 €"));

                    tabLigne = new ArrayList<>();
                    tabLigne.add(colonnePlat);
                    tabLigne.add(colonneBoisson);
                    tabLigne.add(colonnePrix);

                    ligne.appendChildren(tabLigne);
                    table.appendChild(ligne);
                }

                nbMenus100Ans++;
            }

            // on affiche les plats / boissons et prix
            ligne = new Element("tr");
            colonnePlat = new Element("td");
            colonnePlat.appendText(menu.getPlat());
            colonneBoisson = new Element("td");
            colonneBoisson.appendText(menu.getBoisson());
            colonnePrix = new Element("td");
            colonnePrix.appendText(menu.getPrix());

            tabLigne = new ArrayList<>();
            tabLigne.add(colonnePlat);
            tabLigne.add(colonneBoisson);
            tabLigne.add(colonnePrix);

            ligne.appendChildren(tabLigne);
            table.appendChild(ligne);
        }

        // calcul du prix total en fonction du nombre de menus 100 ans
        if ((nbMenus100Ans * 10) / 7 != (nbMenus100Ans / 7) * 10)
            prixTotal += 100 * ((nbMenus100Ans / 7) + 1);
        else
            prixTotal += 100 * (nbMenus100Ans / 7);

        // on rajoute le prix total
        colonnePlat = new Element("td");
        colonneBoisson = new Element("td");
        colonnePrix = new Element("td");
        colonnePrix.appendChild((new Element("strong")).appendText(prixTotal + " €"));

        tabLigne = new ArrayList<>();
        tabLigne.add(colonnePlat);
        tabLigne.add(colonneBoisson);
        tabLigne.add(colonnePrix);

        ligne = new Element("tr");
        ligne.appendChildren(tabLigne);
        table.appendChild(ligne);

        return document;
    }

    /**
     * Enregistre le fichier HTML obtenu en fichier PDF
     * @param doc document modifié
     * @param outputPdf sortie du fichier
     */
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
