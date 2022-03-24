package ingredients;

public abstract class Ingredient {
    public String name;
    public int nbUnite;
    public int nbInitial;

    public Ingredient(String name_, int nbInitial_) {
        name = name_;
        nbInitial = nbInitial_;
        nbUnite = nbInitial_;
    }

    public int ingredientsManquants() {
        return Math.max(nbInitial - nbUnite, 0);
    }

    public void ajoutDIngredients(int nbAjout) {
        nbUnite += nbAjout;
    }

    public void addIngredient() {
        nbUnite--;
    }
}
