package ingredients;

public class Tomate extends Ingredient {
    private Tomate instance = null;

    public Tomate(int nbInitial_) {
        super("Tomates", nbInitial_);
    }

    public Tomate getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Tomate(nbInitial_);
        }
        return instance;
    }

    public Tomate getInstance() {
        return instance;
    }
}
