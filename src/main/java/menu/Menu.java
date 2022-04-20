package menu;

public class Menu {
    private String plat = "";
    private String boisson = "";
    private String prix = "";

    public Menu() {}

    public Menu(String plat_) {
        plat = plat_;
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
}
