package ingredients;

public class PainBurger extends Ingredient {
    private PainBurger instance = null;

    public PainBurger(int nbInitial_) {
        super("PainBurgers", nbInitial_);
    }

    public PainBurger getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new PainBurger(nbInitial_);
        }
        return instance;
    }

    public PainBurger getInstance() {
        return instance;
    }
}
