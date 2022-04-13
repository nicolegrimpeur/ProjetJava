package menuPlats;

import ingredients.EnumIngredients;
import ingredients.IngredientsManager;

public class PlatsManager {
    private static PlatsManager instance = null;

    public PlatsManager() {
    }

    public static PlatsManager getInstance() {
        if (instance == null) {
            instance = new PlatsManager();
        }
        return instance;
    }

    public void preparation(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet()) {
            IngredientsManager.getInstance().addIngredient(ingredient);
        }
    }

    public boolean hasEnoughIngredients(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet())
            if (IngredientsManager.getInstance().stocks.get(ingredient) - plat.getListeIngredients().get(ingredient) < 0)
                return false;

        return true;
    }
}
