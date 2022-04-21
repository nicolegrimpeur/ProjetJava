package status;


public enum EnumStatus {
    APREPARER("A préparer"),
    ENCOURS("En cours"),
    ASERVIR("A servir"),
    TERMINE("Terminé"),
    ENATTENTE("En attente");

    private final String affichage;

    EnumStatus(String affichage_) {
        affichage = affichage_;
    }

    public String getAffichage() {
        return affichage;
    }
}
