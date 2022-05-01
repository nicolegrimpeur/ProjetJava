package journee;

import employee.Employee;
import employee.ManagEmployees;
import html.HtmlManager;
import ingredients.IngredientsManager;
import menu.Menu;
import status.EnumStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JourneeManager {
    private static JourneeManager instance = null;
    public ArrayList<Employee> listEmployes = new ArrayList<>();
    public boolean isJourneeEnCours = false;

    private final Map<String, ArrayList<Menu>> listService = new HashMap<>();
    private final Map<String, ArrayList<Menu>> menusVendus = new HashMap<>();

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

        IngredientsManager.getInstance().initStocksInitial();

        try {
            delete("Additions/");
        } catch (Exception ignored) {
        }
    }

    /**
     * Permet de supprimer le contenu d'un dossier
     *
     * @param path le path vers le dossier
     */
    public static void delete(String path) {
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
        int index = listService.get(employe).indexOf(menu);
        listService.get(employe).get(index).nextStatusPlat();

        // si le plat est affiché comme à servir, alors on l'ajoute aux plats réalisés du cuisinier
        if (listService.get(employe).get(index).getStatusPlat().equals(EnumStatus.ASERVIR.getAffichage()))
            if (employee != null)
                listEmployes.get(listEmployes.indexOf(employee)).addNbItemsVendus(1);
    }

    /**
     * Passe le plat à l'état suivant
     *
     * @param employe  le nom d'affichage du cuisinier
     * @param menu     le menu à modifier
     * @param employee le cuisinier en question
     */
    public void nextStatusBoisson(String employe, Menu menu, Employee employee) {
        int index = listService.get(employe).indexOf(menu);
        listService.get(employe).get(index).nextStatusBoisson();

        // si le plat est affiché comme à servir, alors on l'ajoute aux plats réalisés du barman
        if (listService.get(employe).get(index).getStatusBoisson().equals(EnumStatus.ASERVIR.getAffichage()))
            if (employee != null)
                listEmployes.get(listEmployes.indexOf(employee)).addNbItemsVendus(1);
    }

    /**
     * on précise que les plats de ce serveur sont terminés
     *
     * @param serveur le nom d'affichage du serveur
     */
    public void platTermine(String serveur) {
        menusVendus.computeIfAbsent(serveur, k -> new ArrayList<>());

        for (Menu menu : listService.get(serveur)) {
            menu.nextStatusPlat();
            menusVendus.get(serveur).add(menu);
        }

        ajoutMenusServeur(serveur);
    }

    /**
     * on précise que les boissons de ce serveur sont terminés
     *
     * @param serveur le nom d'affichage du serveur
     */
    public void boissonTermine(String serveur) {
        menusVendus.computeIfAbsent(serveur, k -> new ArrayList<>());

        for (Menu menu : listService.get(serveur)) {
            menu.nextStatusBoisson();
            menusVendus.get(serveur).add(menu);
        }

        ajoutMenusServeur(serveur);
    }

    private void ajoutMenusServeur(String serveur) {
        for (Employee employee : listEmployes)
            if (employee.getAffichage().equals(serveur))
                employee.addNbItemsVendus(listService.get(serveur).size());

        HtmlManager.getInstance().generateFacture(serveur);
        HtmlManager.getInstance().generateAddition(serveur);

        // on supprime les menus de ce serveur en cours
        listService.remove(serveur);
    }

    public void resetVentes() {
        menusVendus.clear();
    }

    public Map<String, ArrayList<Menu>> getMenusVendus() {
        return menusVendus;
    }
}
