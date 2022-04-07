package ingredients;

public class Mozzarella extends Ingredient {
    private static Mozzarella instance = null;

    public Mozzarella(int nbInitial_) {
        super("Mozzarella", nbInitial_);
    }

    public Mozzarella getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Mozzarella(nbInitial_);
        }
        return instance;
    }

    public Mozzarella getInstance() {
        return instance;
    }
}
