package ingredients;

public class Emmental extends Ingredient {
    private Emmental instance = null;

    public Emmental(int nbInitial_) {
        super("Emmental", nbInitial_);
    }

    public Emmental getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Emmental(nbInitial_);
        }
        return instance;
    }

    public Emmental getInstance() {
        return instance;
    }
}
