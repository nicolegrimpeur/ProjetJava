package html;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import ingredients.EnumIngredients;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

public class HtmlManager {
    private static HtmlManager instance = null;
    private static final String FACTURE_INPUT = "resources/htmlTemplate/factureTemplate.html";
    private static final String ADDITION_INPUT = "resources/htmlTemplate/additionTemplate.html";
    private static final String COURSES_INPUT = "resources/htmlTemplate/coursesTemplate.html";
    private static final String FACTURE_OUTPUT = "Factures/";
    private static final String ADDITION_OUTPUT = "Additions/";
    private static final String COURSES_OUTPUT = "resources/";
    private static int nbFactures = 0;

    public static HtmlManager getInstance() {
        if (instance == null)
            instance = new HtmlManager();
        return instance;
    }

    public String getAdditionOutput() {
        return ADDITION_OUTPUT;
    }

    /**
     * Permet de créer dans lesquels seront les additions et les factures
     */
    public void createDirectories() {
        File dir = new File(ADDITION_OUTPUT);
        dir.mkdir();
        dir = new File(FACTURE_OUTPUT);
        dir.mkdir();
    }

    /**
     * Permet de générer une facture dans le dossier défini par FACTURE_OUTPUT
     *
     * @param serveur le nom d'affichage du serveur qui a servi la table
     */
    public void generateFacture(String serveur) {
        getLastNumberFacture();
        createDirectories();
        try {
            File inputHTML = new File(FACTURE_INPUT);
            Document doc = createWellFormedHtmlFactureAddition(inputHTML, true, serveur);
            xhtmlToPdf(doc, FACTURE_OUTPUT + "Facture " + nbFactures++ + ".pdf");
        } catch (Exception ignored) {
        }
    }

    /**
     * Permet de récupérer le dernier numéro dans le dossier des factures
     */
    private void getLastNumberFacture() {
        createDirectories();

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
     *
     * @param serveur le nom d'affichage du serveur qui a servi la table
     */
    public void generateAddition(String serveur, String date) {
        try {
            File inputHTML = new File(ADDITION_INPUT);
            Document doc = createWellFormedHtmlFactureAddition(inputHTML, false, serveur);
            xhtmlToPdf(doc, ADDITION_OUTPUT + date + serveur + ".pdf");
        } catch (Exception ignored) {
        }
    }

    /**
     * Permet de modifier le fichier HTML
     *
     * @param inputHTML le fichier HTML récupéré
     * @param isFacture true si c'est une facture, false si c'est une addition
     * @param serveur   nom d'affichage du serveur qui a servi
     * @return le document modifié
     */
    private Document createWellFormedHtmlFactureAddition(File inputHTML, Boolean isFacture, String serveur) throws IOException {
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

        // on ajoute la version de l'application
        // on récupère le fichier de propriétés du projet (src/main/resources)
        final Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("project.properties"));
        Element version = document.getElementById("version");
        version.appendText(properties.getProperty("version") + " (" + properties.getProperty("nomApplication") + ")");

        // on parcourt les menus gérés par
        int nbMenus100Ans = 0;
        Element table;
        table = document.getElementById("tableau");

        ArrayList<Menu> tabMenusServeurs = JourneeManager.getInstance().getListService().get(serveur);
        for (Menu menu : tabMenusServeurs) {
            // si l'on n'est pas un menu 100 ans
            if (menu.getPrix().equals("")) {
                // si c'est un multiple de 7, on rajoute une ligne pour préciser que c'est un menu 100 ans
                if (nbMenus100Ans % 7 == 0)
                    table.appendChild(generateLigne("Menu 100 Ans", "", "100", true));

                nbMenus100Ans++;
            }

            table.appendChild(generateLigne(menu.getPlat(), menu.getBoisson(), menu.getPrix(), false));
        }

        // on rajoute le prix total
        double prixTotal = JourneeManager.getInstance().calculPrixTotal(tabMenusServeurs);
        double prixTotalSansTva = JourneeManager.getInstance().calculTva(tabMenusServeurs);
        table.appendChild(generateLigne("", "", "", false));
        table.appendChild(generateLigne("", "Prix", Double.toString(prixTotal), true));
        table.appendChild(generateLigne("", "Prix hors taxe", Double.toString(prixTotalSansTva), true));

        return document;
    }

    private Element generateLigne(String plat, String boisson, String prix, boolean isStrong) {
        ArrayList<Node> tabLigne;
        Element ligne, colonnePlat, colonneBoisson, colonnePrix;

        // on rajoute le prix total
        colonnePlat = new Element("td");
        if (isStrong)
            colonnePlat.appendChild((new Element("strong")).appendText(plat));
        else
            colonnePlat.appendText(plat);
        colonneBoisson = new Element("td");
        colonneBoisson.appendText(boisson);
        colonnePrix = new Element("td");
        if (isStrong)
            colonnePrix.appendChild((new Element("strong")).appendText(prix + " €"));
        else
            colonnePrix.appendText(prix);

        tabLigne = new ArrayList<>();
        tabLigne.add(colonnePlat);
        tabLigne.add(colonneBoisson);
        tabLigne.add(colonnePrix);

        ligne = new Element("tr");
        ligne.appendChildren(tabLigne);

        return ligne;
    }

    /**
     * Permet de générer la liste de course dans le dossier défini par COURSES_OUTPUT
     */
    public void generateListeDeCourse() {
        try {
            File inputHTML = new File(COURSES_INPUT);
            Document doc = createWellFormedHtmlCourses(inputHTML);
            xhtmlToPdf(doc, COURSES_OUTPUT + "courses.pdf");
            ImpressionManager.imprimerFichier(COURSES_OUTPUT + "courses.pdf");
        } catch (Exception ignored) {
        }
    }

    /**
     * Permet de modifier le fichier HTML de la liste de courses
     *
     * @param inputHTML le fichier HTML récupéré
     * @return le document modifié
     */
    private Document createWellFormedHtmlCourses(File inputHTML) throws IOException {
        Document document = Jsoup.parse(inputHTML, "UTF-8");
        document.outputSettings()
                .syntax(Document.OutputSettings.Syntax.xml);

        ArrayList<Node> tabLigne;
        Element table, ligne, colonneIngredients, colonneNombre;
        table = document.getElementById("tableau");

        for (EnumIngredients ingredient : EnumIngredients.values()) {
            // on affiche le nom de l'ingrédient ainsi que le nombre à acheter
            ligne = new Element("tr");
            colonneIngredients = new Element("td");
            colonneIngredients.appendText(ingredient.getName());
            colonneNombre = new Element("td");
            colonneNombre.appendText(Integer.toString(ingredient.ingredientsManquants()));

            tabLigne = new ArrayList<>();
            tabLigne.add(colonneIngredients);
            tabLigne.add(colonneNombre);

            ligne.appendChildren(tabLigne);
            table.appendChild(ligne);
        }

        return document;
    }

    /**
     * Enregistre le fichier HTML obtenu en fichier PDF
     *
     * @param doc       document modifié
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
