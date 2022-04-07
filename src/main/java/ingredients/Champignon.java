package ingredients;

public class Champignon extends Ingredient {
    private static Champignon instance = null;

    public Champignon(int nbInitial_) {
        super("Champignons", nbInitial_);
    }

    public Champignon getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Champignon(nbInitial_);
        }
        return instance;
    }

    public Champignon getInstance() {
        return instance;
    }
}
