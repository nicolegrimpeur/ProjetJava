package journee;

import employee.Barman;
import employee.Cuisinier;
import employee.Employee;
import employee.ManagEmployees;
import menu.Menu;
import menuBoissons.EnumBoissons;
import menuPlats.EnumPlats;
import status.EnumStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JourneeManager {
    private static JourneeManager instance = null;
    public ArrayList<Employee> listEmployes = new ArrayList<>();
    public boolean isJourneeEnCours = false;

    private final Map<String, ArrayList<Menu>> listService = new HashMap<>();
    private final Map<String, ArrayList<Menu>> menusVendus = new HashMap<>();

    private final Map<String, ArrayList<EnumPlats>> platsVendus = new HashMap<>();
    private final Map<String, ArrayList<EnumBoissons>> boissonsVendus = new HashMap<>();



    private JourneeManager() {
    }

    public static JourneeManager getInstance() {
        if (instance == null)
            instance = new JourneeManager();
        return instance;
    }

    public void debutJournee() {
        ManagEmployees.getInstance().addJourConsecutif(listEmployes);
        isJourneeEnCours = true;

        JourneeManager.getInstance().resetVentes();
    }

    public void addEmployee(Employee employee) {
        listEmployes.add(employee);
    }

    public void removeEmployee(Employee employee) {
        listEmployes.remove(employee);
    }

    public void finJournee() {
        isJourneeEnCours = false;
        listEmployes.clear();
    }


    public Map<String, ArrayList<Menu>> getListService() {
        return listService;
    }

    public void addMenuService(String employe, Menu menu) {
        listService.computeIfAbsent(employe, k -> new ArrayList<>());
        listService.get(employe).add(menu);
    }

    public void nextStatusPlat(String employe, Menu menu, Cuisinier cuisinier) {
        int index = listService.get(employe).indexOf(menu);
        listService.get(employe).get(index).nextStatusPlat();

        if (listService.get(employe).get(index).getStatusPlat().equals(EnumStatus.ASERVIR.getAffichage()))
            if (cuisinier != null)
                cuisinier.addPlatRealise(EnumPlats.rechercheParNom(menu.getPlat()));
//
//
//        Menu menu = listService.get(employe).get(index);
//        menu.nextStatusPlat();
//
//        if (listService.get(employe).get(index).getStatusPlat().equals(EnumStatus.ASERVIR.getAffichage()))
//                if (cuisinier != null)
//                    cuisinier.addPlatRealise(EnumPlats.rechercheParNom(menu.getPlat()));
    }

    public void nextStatusBoisson(String employe, Menu menu, Barman barman) {
        int index = listService.get(employe).indexOf(menu);
        listService.get(employe).get(index).nextStatusBoisson();

        if (listService.get(employe).get(index).getStatusBoisson().equals(EnumStatus.ASERVIR.getAffichage()))
                if (barman != null)
                    barman.addCocktailRealise(EnumBoissons.rechercheParNom(menu.getBoisson()));
    }

    public void platTermine(String serveur) {
//        platsVendus.computeIfAbsent(serveur, k -> new ArrayList<>());
        menusVendus.computeIfAbsent(serveur, k -> new ArrayList<>());

        for (Menu menu : listService.get(serveur)) {
            menu.nextStatusPlat();
            menusVendus.get(serveur).add(menu);
//            platsVendus.get(serveur).add(EnumPlats.rechercheParNom(menu.getPlat()));
        }

        if (Objects.equals(listService.get(serveur).get(0).getStatusBoisson(), EnumStatus.ASERVIR.getAffichage()))
            listService.remove(serveur);
    }

    public void boissonTermine(String serveur) {
//        boissonsVendus.computeIfAbsent(serveur, k -> new ArrayList<>());
        menusVendus.computeIfAbsent(serveur, k -> new ArrayList<>());

        for (Menu menu : listService.get(serveur)) {
            menu.nextStatusBoisson();
            menusVendus.get(serveur).add(menu);
//            boissonsVendus.get(serveur).add(EnumBoissons.rechercheParNom(menu.getBoisson()));
        }

        if (Objects.equals(listService.get(serveur).get(0).getStatusPlat(), EnumStatus.ASERVIR.getAffichage())) {
            listService.remove(serveur);
        }
    }

    public void resetVentes() {
//        platsVendus.clear();
//        boissonsVendus.clear();
        menusVendus.clear();
    }

    public Map<String, ArrayList<Menu>> getMenusVendus() {
        return menusVendus;
    }
}
