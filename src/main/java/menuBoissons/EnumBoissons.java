package menuBoissons;

import java.util.Objects;

public enum EnumBoissons {
    BIERRESANSALCOOL("Bière sans alcool", 5, false),
    CIDREDOUX("Cidre doux", 5, true),
    JUSDEFRUIT("Jus de fruit", 1, false),
    LIMONADE("Limonade", 4, false),
    VERREDEAU("Verre d'eau", 0, false);

    private final String name;
    private final int prix;
    private final boolean isAlcoolise;

    EnumBoissons(String name_, int prix_, boolean isAlcoolise_) {
        name = name_;
        prix = prix_;
        isAlcoolise = isAlcoolise_;
    }

    public int getPrix() {
        return prix;
    }

    public String getName() {
        return name;
    }

    public boolean isAlcoolise() {
        return isAlcoolise;
    }

    public static EnumBoissons rechercheParNom(String nom) {
        for (EnumBoissons boisson: values())
            if (Objects.equals(boisson.getName(), nom))
                return boisson;
        return null;
    }
}
