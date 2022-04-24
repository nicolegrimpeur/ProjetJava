package employee;

import menuPlats.EnumPlats;

import java.util.ArrayList;

public class Cuisinier extends Employee {
    private final ArrayList<EnumPlats> platsRealises = new ArrayList<>();

    public Cuisinier(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_, "Cuisinier");
    }
// truc simultané poue cuisiner barman? je sais plus
//    @Override
    public void preparerCommande() {

    }

    public ArrayList<EnumPlats> getPlatsRealises() {
        return platsRealises;
    }

    public int nombrePlatsRealises() {
        return platsRealises.size();
    }

    public void addPlatRealise(EnumPlats plat) {
        platsRealises.add(plat);
    }

}
