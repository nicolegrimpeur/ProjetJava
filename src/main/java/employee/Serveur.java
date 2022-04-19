package employee;

import menuBoissons.EnumBoissons;
import menuPlats.EnumPlats;
import menuPlats.PlatsManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Serveur extends Employee {
    public int prixTotal; // prix total de la commande
    // liste des plats commandés
    public ArrayList<EnumPlats> listPlatsCommandes = new ArrayList<>();
    // liste des boissons commandées
    public ArrayList<EnumBoissons> listBoissonsCommandes = new ArrayList<>();
    // liste des plats commandés dans le cadre d'un menu Cent ans
    public ArrayList<ArrayList<EnumPlats>> listPlatsCommandes100Ans = new ArrayList<>();
    // liste des boissons commandées dans le cadre d'un menu Cent ans
    public ArrayList<ArrayList<EnumBoissons>> listBoissonsCommandes100Ans = new ArrayList<>();

    public Serveur(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_);
    }

    @Override
    public void preparerCommande() {
        boolean onContinue;

        Scanner scanner = new Scanner(System.in);
        int res;

        System.out.println("Voulez-vous faire un menu Cent ans ? (o ou n)");

        onContinue = estCeQuOnContinue(scanner.next());

        // on prend les commandes de menu Cent ans
        while (onContinue) {
            prixTotal += 100;

            listPlatsCommandes100Ans.add(new ArrayList<>());
            for (int index = 0; index < 7; index++) {
                res = takeCommandPlats();
                listPlatsCommandes100Ans.get(listPlatsCommandes100Ans.size() - 1).add(EnumPlats.values()[res]);

                res = takeCommandBoissons();
                listBoissonsCommandes100Ans.get(listBoissonsCommandes100Ans.size() - 1).add(EnumBoissons.values()[res]);
            }

            System.out.println("Voulez-vous prendre un autre menu Cent ans ?");
            System.out.println("(o ou n, a si vous souhaitez annuler toutes les commandes de la table)");

            onContinue = estCeQuOnContinue(scanner.next());
        }

        System.out.println("Voulez-vous prendre une autre commande ?");
        System.out.println("(o ou n, a si vous souhaitez annuler toutes les commandes de la table)");

        onContinue = estCeQuOnContinue(scanner.next());

        // commandes classiques
        while (onContinue) {
            // commande plat
            res = takeCommandPlats();
            prixTotal += EnumPlats.values()[res].getPrix();
            listPlatsCommandes.add(EnumPlats.values()[res]);
            PlatsManager.getInstance().preparation(EnumPlats.values()[res]);

            // commande boisson
            res = takeCommandBoissons();
            prixTotal += EnumBoissons.values()[res].getPrix();
            listBoissonsCommandes.add(EnumBoissons.values()[res]);

            System.out.println("Voulez-vous faire une autre commande ?");
            System.out.println("(o ou n, a si vous souhaitez annuler toutes les commandes de la table)");

            onContinue = estCeQuOnContinue(scanner.next());
        }
    }

    /**
     * Permet de savoir si l'on doit continuer la commande ou pas
     * @param res sortie du scanner
     * @return si l'on continue ou non
     */
    public boolean estCeQuOnContinue(String res) {
        boolean sortie;
        switch (res.toUpperCase()) {
            case "O", "OUI" -> sortie = true;
            case "A", "ANNULER", "CANCEL" -> {
                annulerCommande();
                sortie = false;
            }
            default -> sortie = false;
        }
        return sortie;
    }

    /**
     * Prend une commande de plat
     * @return l'indice correspondant au plat dans l'énumération des plats
     */
    public int takeCommandPlats() {
        Scanner scanner;
        int index;

        System.out.println("Plat du client : ");

        for (index = 0; index < EnumPlats.values().length; index++) {
            // on affiche le plat que si l'on a assez d'ingrédients
            if (PlatsManager.getInstance().hasEnoughIngredients(EnumPlats.values()[index])) {
                System.out.println(index + " - " + EnumPlats.values()[index].getName());
            }
        }

        scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    /**
     * Prend une commande de boisson
     * @return l'indice correspondant à la boisson dans l'énumération des boissons
     */
    public int takeCommandBoissons() {
        Scanner scanner;
        int index;

        System.out.println("Boisson du client : ");

        for (index = 0; index < EnumBoissons.values().length; index++) {
            System.out.println(index + " - " + EnumBoissons.values()[index]);
        }

        scanner = new Scanner(System.in);

        return scanner.nextInt();
    }

    public void annulerCommande() {
        for (ArrayList<EnumPlats> listPlats: listPlatsCommandes100Ans) {
            for (EnumPlats plat: listPlats) {
                PlatsManager.getInstance().suppressionPlat(plat);
            }
        }

        for (EnumPlats plat: listPlatsCommandes) {
            PlatsManager.getInstance().suppressionPlat(plat);
        }

        listBoissonsCommandes100Ans.clear();
        listBoissonsCommandes.clear();

        prixTotal = 0;
    }

    /**
     * Affiche l'addition
     */
    public void showAddition() {
        int index, index100Ans;
        for (index = 0; index < listPlatsCommandes100Ans.size(); index++) {
            System.out.println("Menu Cent ans : 100€");
            for (index100Ans = 0; index100Ans < 7; index100Ans++) {
                System.out.println("    " + listPlatsCommandes100Ans.get(index100Ans));
                System.out.println("    " + listBoissonsCommandes100Ans.get(index100Ans));
            }
        }

        for (index = 0; index < listPlatsCommandes.size(); index++) {
            System.out.println("Menu classique");
            System.out.println("    " + listPlatsCommandes.get(index).getName() + " " + listPlatsCommandes.get(index).getPrix() + "€");
            System.out.println("    " + listBoissonsCommandes.get(index).getName() + " " + listBoissonsCommandes.get(index).getPrix() + "€");
        }

        System.out.println("Total à régler : " + prixTotal);
    }

    /**
     * Enregistre la facture
     */
    public void sauvegardeFacture() throws IOException {
        int index, index100Ans;

        PrintWriter writer = new PrintWriter("./Factures/" + LocalDateTime.now() + ".txt", StandardCharsets.UTF_8);

        for (index = 0; index < listPlatsCommandes100Ans.size(); index++) {
            writer.println("Menu Cent ans : 100€");
            for (index100Ans = 0; index100Ans < 7; index100Ans++) {
                writer.println("    " + listPlatsCommandes100Ans.get(index100Ans));
                writer.println("    " + listBoissonsCommandes100Ans.get(index100Ans));
            }
        }

        for (index = 0; index < listPlatsCommandes.size(); index++) {
            writer.println("Menu classique");
            writer.println("    " + listPlatsCommandes.get(index).getName() + " " + listPlatsCommandes.get(index).getPrix() + "€");
            writer.println("    " + listBoissonsCommandes.get(index).getName() + " " + listBoissonsCommandes.get(index).getPrix() + "€");
        }

        writer.println("Total à régler : " + prixTotal);

        writer.close();
    }
}
