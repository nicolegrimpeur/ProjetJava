package ingredients;

public class Steak extends Ingredient {
    private static Steak instance = null;

    public Steak(int nbInitial_) {
        super("Steaks", nbInitial_);
    }

    public Steak getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Steak(nbInitial_);
        }
        return instance;
    }

    public Steak getInstance() {
        return instance;
    }
}
