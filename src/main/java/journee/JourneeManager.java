package journee;

import employee.Employee;
import employee.ManagEmployees;
import files.HtmlManager;
import ingredients.EnumIngredients;
import menu.Menu;
import menuBoissons.EnumBoissons;
import menuPlats.EnumPlats;
import status.EnumStatus;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JourneeManager {
    private static JourneeManager instance = null;
    public ArrayList<Employee> listEmployes = new ArrayList<>();
    public boolean isJourneeEnCours = false;

    private final Map<String, ArrayList<Menu>> listService = new HashMap<>();
    private final Map<String, ArrayList<Menu>> menusVendus = new HashMap<>();
    private final Map<String, ArrayList<Menu>> mapAdditions = new HashMap<>();
    private final Map<String, Boolean> isPaye = new HashMap<>(); // permet de dire si une addition a déjà été payé ou pas

    private JourneeManager() {
    }

    public static JourneeManager getInstance() {
        if (instance == null)
            instance = new JourneeManager();
        return instance;
    }

    /**
     * Fonction lancé au début de la journée
     */
    public void debutJournee() {
        // on rajoute un jour consécutif pour chaque employé présent dans la journée
        ManagEmployees.getInstance().addJourConsecutif(listEmployes);
        // on lance la journée
        isJourneeEnCours = true;

        // on supprime toutes les ventes précédentes
        JourneeManager.getInstance().resetVentes();

        // permet au lancement de la journée de remettre à 0 le stock initial avec le stock présent en début de journée
        for (EnumIngredients ingredient : EnumIngredients.values())
            ingredient.setStocksInitial(ingredient.getStocks());

        delete("Additions/");
    }

    /**
     * Permet de supprimer le contenu d'un dossier
     *
     * @param path le path vers le dossier
     */
    public void delete(String path) {
        File f = new File(path);
        if (f.isDirectory()) {
            // on liste le contenu du répertoire
            String[] files = f.list();

            if (files != null)
                for (String tmp : files) {
                    File file = new File(f, tmp);
                    // suppression du fichier
                    file.delete();
                }
        }
    }

    public void addEmployee(Employee employee) {
        listEmployes.add(employee);
    }

    public void removeEmployee(Employee employee) {
        listEmployes.remove(employee);
    }

    /**
     * Termine la journée
     */
    public void finJournee() {
        isJourneeEnCours = false;
        listEmployes.clear();
    }


    public Map<String, ArrayList<Menu>> getListService() {
        return listService;
    }

    /**
     * Ajoute un item à la liste des menus à préparer
     *
     * @param employe le nom d'affichage du serveur
     * @param menu    le menu qui lui correspond
     */
    public void addMenuService(String employe, Menu menu) {
        listService.computeIfAbsent(employe, k -> new ArrayList<>());
        listService.get(employe).add(menu);
    }

    /**
     * Passe le plat à l'état suivant
     *
     * @param employe  le nom d'affichage du cuisinier
     * @param menu     le menu à modifier
     * @param employee le cuisinier en question
     */
    public void nextStatusPlat(String employe, Menu menu, Employee employee) {
        if (listService.get(employe) != null) {
            int index = listService.get(employe).indexOf(menu);
            listService.get(employe).get(index).nextStatusPlat();

            // si le plat est affiché comme à servir, alors on l'ajoute aux plats réalisés du cuisinier
            if (listService.get(employe).get(index).getStatusPlat().equals(EnumStatus.ASERVIR.getAffichage()))
                if (employee != null)
                    listEmployes.get(listEmployes.indexOf(employee)).addNbItemsVendus(1);
        }
    }

    /**
     * Passe le plat à l'état suivant
     *
     * @param employe  le nom d'affichage du cuisinier
     * @param menu     le menu à modifier
     * @param employee le cuisinier en question
     */
    public void nextStatusBoisson(String employe, Menu menu, Employee employee) {
        if (listService.get(employe) != null) {
            int index = listService.get(employe).indexOf(menu);
            listService.get(employe).get(index).nextStatusBoisson();

            // si le plat est affiché comme à servir, alors on l'ajoute aux plats réalisés du barman
            if (listService.get(employe).get(index).getStatusBoisson().equals(EnumStatus.ASERVIR.getAffichage()))
                if (employee != null)
                    listEmployes.get(listEmployes.indexOf(employee)).addNbItemsVendus(1);
        }
    }

    /**
     * on précise que les plats de ce serveur sont terminés
     *
     * @param serveur le nom d'affichage du serveur
     */
    public void platTermine(String serveur) {
        menusVendus.computeIfAbsent(serveur, k -> new ArrayList<>());

        for (Menu menu : listService.get(serveur))
            menu.nextStatusPlat();

        ajoutMenusServeur(serveur);
    }

    /**
     * on précise que les boissons de ce serveur sont terminés
     *
     * @param serveur le nom d'affichage du serveur
     */
    public void boissonTermine(String serveur) {
        menusVendus.computeIfAbsent(serveur, k -> new ArrayList<>());

        for (Menu menu : listService.get(serveur))
            menu.nextStatusBoisson();

        ajoutMenusServeur(serveur);
    }

    private void ajoutMenusServeur(String serveur) {
        for (Employee employee : listEmployes)
            if (employee.getAffichage().equals(serveur))
                employee.addNbItemsVendus(listService.get(serveur).size());

        // génère la facture et l'addition
        HtmlManager.generateFacture(serveur);

        Date aujourdhui = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("HH'h'mm'm'ss's' ");
        String date = formater.format(aujourdhui);
        HtmlManager.generateAddition(serveur, date);

        // on enregistre l'addition
        mapAdditions.put(date + serveur, new ArrayList<>());
        for (Menu menu : listService.get(serveur)) {
            menusVendus.get(serveur).add(menu);
            mapAdditions.get(date + serveur).add(menu);
        }

        // on supprime les menus de ce serveur en cours
        listService.remove(serveur);
    }

    /**
     * Calcul le prix total, le prix total sans la TVA, la TVA payé à 10% et la TVA payé à 20%, d'une liste de menus
     * @param listMenu liste de menu
     * @return une map avec comme clés "Prix total", "Prix hors taxes", "Total TVA 10%", "Total TVA 20%"
     */
    public Map<String, Double> calculTva(ArrayList<Menu> listMenu) {
        double tvaHorsBoissonsAlcoolisees, tvaBoissonsAlcoolisees;
        try {
            // on récupère le fichier de propriétés du projet (src/main/resources)
            final Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("project.properties"));

            tvaHorsBoissonsAlcoolisees = Integer.parseInt(properties.getProperty("tvaHorsBoissonsAlcoolisees"));
            tvaBoissonsAlcoolisees = Integer.parseInt(properties.getProperty("tvaBoissonsAlcoolisees"));
        } catch (IOException e) {
            tvaHorsBoissonsAlcoolisees = 10;
            tvaBoissonsAlcoolisees = 20;
        }

        double prixSansTva = 0, nbMenus100Ans = 0, totalPrixBoissonsAlcoolisesMenus100Ans = 0, prixTVA20 = 0, prixTVA10 = 0, prixTotal = 0;
        EnumBoissons boisson;
        EnumPlats plat;
        double prixBoisson, prixPlat, prixBoissonSansTva, prixPlatSansTva;

        for (Menu menu : listMenu) {
            if (!menu.getPrix().equals("")) {
                boisson = EnumBoissons.rechercheParNom(menu.getBoisson());
                plat = EnumPlats.rechercheParNom(menu.getPlat());
                prixBoisson = boisson.getPrix();
                prixPlat = plat.getPrix();
                // on enlève la TVA
                prixBoissonSansTva = prixBoisson * (100 - ((boisson.isAlcoolise()) ? tvaBoissonsAlcoolisees : tvaHorsBoissonsAlcoolisees)) / 100;
                prixPlatSansTva = prixPlat * (100 - tvaHorsBoissonsAlcoolisees) / 100;

                prixTVA20 += prixBoisson * ((boisson.isAlcoolise()) ? tvaBoissonsAlcoolisees : 0) / 100;
                prixTVA10 += prixBoisson * ((boisson.isAlcoolise()) ? 0 : tvaHorsBoissonsAlcoolisees) / 100;
                prixTVA10 += prixPlat * tvaHorsBoissonsAlcoolisees / 100;

                prixSansTva += prixBoissonSansTva + prixPlatSansTva;
                prixTotal += Double.parseDouble(menu.getPrix());
            } else {
                nbMenus100Ans++;

                boisson = EnumBoissons.rechercheParNom(menu.getBoisson());
                if (boisson.isAlcoolise())
                    totalPrixBoissonsAlcoolisesMenus100Ans += boisson.getPrix();
            }
        }

        double prixMenusCentsAns;
        if ((nbMenus100Ans * 10) / 7 != (nbMenus100Ans / 7) * 10)
            prixMenusCentsAns = 100 * ((nbMenus100Ans / 7) + 1);
        else
            prixMenusCentsAns = 100 * (nbMenus100Ans / 7);

        prixSansTva += (prixMenusCentsAns - totalPrixBoissonsAlcoolisesMenus100Ans) * (100 - tvaHorsBoissonsAlcoolisees) / 100
                + totalPrixBoissonsAlcoolisesMenus100Ans * (100 - tvaBoissonsAlcoolisees) / 100;

        prixTVA20 += totalPrixBoissonsAlcoolisesMenus100Ans * tvaBoissonsAlcoolisees / 100;
        prixTVA10 += (prixMenusCentsAns - totalPrixBoissonsAlcoolisesMenus100Ans) * tvaHorsBoissonsAlcoolisees / 100;
        prixTotal += prixMenusCentsAns;

        Map<String, Double> mapSorti = new HashMap<>();
        mapSorti.put("Prix total", (double) Math.round(prixTotal * 100) / 100);
        mapSorti.put("Prix hors taxes", (double) Math.round(prixSansTva * 100) / 100);
        mapSorti.put("Total TVA 10%", (double) Math.round(prixTVA10 * 100) / 100);
        mapSorti.put("Total TVA 20%", (double) Math.round(prixTVA20 * 100) / 100);
        return mapSorti;
    }

    public void resetVentes() {
        menusVendus.clear();
    }

    public Map<String, ArrayList<Menu>> getMenusVendus() {
        return menusVendus;
    }

    public Map<String, ArrayList<Menu>> getMapAdditions() {
        return mapAdditions;
    }

    public Boolean getIsPaye(String nomAddition) {
        isPaye.putIfAbsent(nomAddition, false);
        return isPaye.get(nomAddition);
    }

    public void setIsPaye(String nomAddition) {
        isPaye.put(nomAddition, true);
    }
}
