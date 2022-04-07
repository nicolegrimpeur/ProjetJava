package ingredients;

public abstract class Ingredient {
    private static Ingredient instance = null;

    public String name;
    public int nbUnite;
    public int nbInitial;

    public Ingredient(String name_, int nbInitial_) {
        name = name_;
        nbInitial = nbInitial_;
        nbUnite = nbInitial_;
    }

    public abstract Ingredient getInstance();

    /**
     * @return nombre d'ingrédients manquants
     */
    public int ingredientsManquants() {
        return Math.max(nbInitial - nbUnite, 0);
    }

    /**
     * Ajoute des ingrédients au stock
     *
     * @param nbAjout nombre d'ingrédients ajoutés
     */
    public void ajoutDIngredients(int nbAjout) {
        nbUnite += nbAjout;
    }

    /**
     * Utilise un ingrédient
     */
    public void addIngredient() {
        nbUnite--;
    }
}
