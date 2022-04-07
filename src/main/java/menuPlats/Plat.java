package menuPlats;

import ingredients.EnumIngredients;
import ingredients.IngredientsManager;

import java.util.ArrayList;

public abstract class Plat {
    public String name;
    public int prix;
    public static final ArrayList<EnumIngredients> listIngredients = new ArrayList<>();

    public Plat(String name_, int prix_) {
        name = name_;
        prix = prix_;
        preparation();
    }

    public void initIngredients() {}

    public void preparation() {
        for (EnumIngredients ingredient: listIngredients) {
            IngredientsManager.getInstance().addIngredient(ingredient);
        }
    }

    public boolean hasEnoughIngredients() {
        for (EnumIngredients ingredient: listIngredients) {
            if (IngredientsManager.getInstance().stocks.get(ingredient) <= 0) return false;
        }
        return true;
    }
}
