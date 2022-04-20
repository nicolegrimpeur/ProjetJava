package menuBoissons;

import java.util.Objects;

public enum EnumBoissons {
    BIERRESANSALCOOL("Bière sans alcool", 5),
    CIDREDOUX("Cidre doux", 5),
    JUSDEFRUIT("Jus de fruit", 1),
    LIMONADE("Limonade", 4),
    VERREDEAU("Verre d'eau", 0);

    private final String name;
    private final int prix;

    EnumBoissons(String name_, int prix_) {
        name = name_;
        prix = prix_;
    }

    public int getPrix() {
        return prix;
    }

    public String getName() {
        return name;
    }

    public static EnumBoissons rechercheParNom(String nom) {
        for (EnumBoissons boisson: values())
            if (Objects.equals(boisson.getName(), nom))
                return boisson;
        return null;
    }
}
