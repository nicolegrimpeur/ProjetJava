package ingredients;

public class Saucisse extends Ingredient {
    private Saucisse instance = null;

    public Saucisse(int nbInitial_) {
        super("Saucisses", nbInitial_);
    }

    public Saucisse getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Saucisse(nbInitial_);
        }
        return instance;
    }

    public Saucisse getInstance() {
        return instance;
    }
}
