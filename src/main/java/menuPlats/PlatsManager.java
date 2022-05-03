package menuPlats;

import ingredients.EnumIngredients;

public class PlatsManager {
    public static void preparation(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet()) {
            ingredient.enleverIngredient(plat.getListeIngredients().get(ingredient));
        }
    }

    public static void suppressionPlat(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet()) {
            ingredient.rajouterIngredient(plat.getListeIngredients().get(ingredient));
        }
    }

    public static boolean hasEnoughIngredients(EnumPlats plat) {
        for (EnumIngredients ingredient : plat.getListeIngredients().keySet())
            if (ingredient.getStocks() - plat.getListeIngredients().get(ingredient) < 0)
                return false;

        return true;
    }
}
