package employee;

import menu.Menu;

import java.util.ArrayList;

public class Serveur extends Employee {
    public ArrayList<Menu> listCommandes = new ArrayList<>();
    public ArrayList<ArrayList<Menu>> listCommandes100Ans = new ArrayList<>();

    public Serveur(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_, "Serveur");
    }
}
