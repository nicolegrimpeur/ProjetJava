package menuPlats;

import ingredients.EnumIngredients;

import java.util.HashMap;
import java.util.Map;

public class PlatsManager {
    private static PlatsManager instance = null;
    public final Map<String, EnumPlats> listNoms = new HashMap<>();

    private PlatsManager() {
        for (EnumPlats plat: EnumPlats.values()) {
            listNoms.put(plat.getName(), plat);
        }
    }

    public static PlatsManager getInstance() {
        if (instance == null) {
            instance = new PlatsManager();
        }
        return instance;
    }

    public void preparation(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet()) {
            ingredient.enleverIngredient(plat.getListeIngredients().get(ingredient));
//            IngredientsManager.getInstance().addIngredient(ingredient, plat.getListeIngredients().get(ingredient));
        }
    }

    public void suppressionPlat(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet()) {
//            IngredientsManager.getInstance().rajouterIngredient(ingredient, plat.getListeIngredients().get(ingredient));
            ingredient.rajouterIngredient(plat.getListeIngredients().get(ingredient));
        }
    }

    public boolean hasEnoughIngredients(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet())
            if (ingredient.getStocks() - plat.getListeIngredients().get(ingredient) < 0)
                return false;

        return true;
    }
}
