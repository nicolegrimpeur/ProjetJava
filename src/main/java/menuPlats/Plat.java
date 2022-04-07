package menuPlats;

import ingredients.Ingredient;

import java.util.ArrayList;

public abstract class Plat {
    public String name;
    public int prix;
    public static final ArrayList<Ingredient> listIngredients = new ArrayList<>();

    public Plat(String name_, int prix_) {
        name = name_;
        prix = prix_;
        preparation();
    }

    public void initIngredients() {}

    public void preparation() {
        for (Ingredient ingredient: listIngredients) {
            ingredient.getInstance().addIngredient();
        }
    }

    public boolean hasEnoughIngredients() {
        for (Ingredient ingredient: listIngredients) {
            if (ingredient.getInstance().nbUnite <= 0) return false;
        }
        return true;
    }
}
