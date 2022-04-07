package menuPlats;

import ingredients.EnumIngredients;
import ingredients.IngredientsManager;

public abstract class Potages extends Plat {
    public Potages(String name_) {
        super(name_, 8);
    }

    @Override
    public boolean hasEnoughIngredients() {
        for (EnumIngredients ingredient: listIngredients) {
            if (IngredientsManager.getInstance().stocks.get(ingredient) <= 0) return false;
        }
        return true;
    }
}