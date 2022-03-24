package ingredients;

public class Salade extends Ingredient {
    private static Salade instance = null;

    public Salade(int nbInitial_) {
        super("Salades", nbInitial_);
    }

    public static Salade getInstance(int nbInitial_) {
        if (instance == null) {
            instance = new Salade(nbInitial_);
        }
        return instance;
    }

    public static Salade getInstance() {
        return instance;
    }
}
