package menuPlats;

import ingredients.EnumIngredients;
import ingredients.IngredientsManager;

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
            IngredientsManager.getInstance().addIngredient(ingredient, plat.getListeIngredients().get(ingredient));
        }
    }

    public void suppressionPlat(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet()) {
            IngredientsManager.getInstance().rajouterIngredient(ingredient, plat.getListeIngredients().get(ingredient));
        }
    }

    public boolean hasEnoughIngredients(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet())
            if (IngredientsManager.getInstance().stocks.get(ingredient) - plat.getListeIngredients().get(ingredient) < 0)
                return false;

        return true;
    }
}
