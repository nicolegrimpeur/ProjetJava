package ingredients;

public class Oignon extends Ingredient {
    private static Oignon instance = null;

    public Oignon(int nbInitial_) {
        super("Oignons", nbInitial_);
    }

    public Oignon getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Oignon(nbInitial_);
        }
        return instance;
    }

    public Oignon getInstance() {
        return instance;
    }
}
