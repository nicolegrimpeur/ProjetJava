package employee;

import menuBoissons.EnumBoissons;

import java.util.ArrayList;

public class Barman extends Employee {
    private final ArrayList<EnumBoissons> cocktailsRealises = new ArrayList<>();

    public Barman(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_, "Barman");
    }
    // truc simultané poue cuisiner barman? je sais plus
//    @Override
    public void preparerCommande() {

    }

    public ArrayList<EnumBoissons> getCocktailsRealises() {
        return cocktailsRealises;
    }

    public int nombreCocktailsRealises() {
        return cocktailsRealises.size();
    }

    public void addCocktailRealise(EnumBoissons boisson) {
        cocktailsRealises.add(boisson);
    }
}
