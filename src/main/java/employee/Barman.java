package employee;

import menuBoissons.EnumBoissons;

import java.util.ArrayList;

public class Barman extends Employee {
    private final ArrayList<EnumBoissons> cocktailsRealises = new ArrayList<>();

    public Barman(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_, "Barman");
    }
}
