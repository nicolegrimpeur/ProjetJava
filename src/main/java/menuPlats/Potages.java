package menuPlats;

import ingredients.Ingredient;

public abstract class Potages extends Plat {
    public Potages(String name_) {
        super(name_, 8);
    }

    @Override
    public boolean hasEnoughIngredients() {
        for (Ingredient ingredient: listIngredients) {
            if (ingredient.getInstance().nbUnite <= 2) return false;
        }
        return true;
    }
}