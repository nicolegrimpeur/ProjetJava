package menu;

import status.EnumStatus;

import java.util.Objects;

public class Menu {
    private String plat = "";
    private String boisson = "";
    private String prix = "";
    private String statusPlat = EnumStatus.APREPARER.getAffichage();
    private String statusBoisson = EnumStatus.APREPARER.getAffichage();

    public Menu() {}

    public Menu(String plat_) {
        plat = plat_;
    }
    public Menu(String plat_, String boisson_) {
        plat = plat_;
        boisson = boisson_;
    }

    public Menu(String plat_, String boisson_, String prix_) {
        plat = plat_;
        boisson = boisson_;
        prix = prix_;
    }

    public String getPlat() {
        return plat;
    }

    public String getBoisson() {
        return boisson;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getStatusPlat() {
        return statusPlat;
    }

    public void setStatusPlat(String statusPlat) {
        this.statusPlat = statusPlat;
    }

    public String getStatusBoisson() {
        return statusBoisson;
    }

    public void setStatusBoisson(String statusBoisson) {
        this.statusBoisson = statusBoisson;
    }

    public void nextStatusPlat() {
        if (Objects.equals(statusPlat, EnumStatus.APREPARER.getAffichage())) statusPlat = EnumStatus.ENCOURS.getAffichage();
        else if (Objects.equals(statusPlat, EnumStatus.ENCOURS.getAffichage())) statusPlat = EnumStatus.ASERVIR.getAffichage();
        else if (Objects.equals(statusPlat, EnumStatus.ASERVIR.getAffichage())) statusPlat = EnumStatus.TERMINE.getAffichage();
    }

    public void nextStatusBoisson() {
        if (Objects.equals(statusBoisson, EnumStatus.APREPARER.getAffichage())) statusBoisson = EnumStatus.ENCOURS.getAffichage();
        else if (Objects.equals(statusBoisson, EnumStatus.ENCOURS.getAffichage())) statusBoisson = EnumStatus.ASERVIR.getAffichage();
        else if (Objects.equals(statusBoisson, EnumStatus.ASERVIR.getAffichage())) statusBoisson = EnumStatus.TERMINE.getAffichage();
    }
}
