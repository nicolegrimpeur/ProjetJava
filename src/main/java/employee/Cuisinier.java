package employee;

import menuPlats.EnumPlats;

import java.util.ArrayList;

public class Cuisinier extends Employee {
    private final ArrayList<EnumPlats> platsRealises = new ArrayList<>();

    public Cuisinier(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_, "Cuisinier");
    }
}
