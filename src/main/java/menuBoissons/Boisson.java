package menuBoissons;

public abstract class Boisson {
    public String name;
    public int prix;

    public Boisson(String name_, int prix_) {
        name = name_;
        prix = prix_;
    }
}
