package ingredients;

import java.util.EnumMap;

public class IngredientsManager {
    private static IngredientsManager instance = null;

    public final EnumMap<EnumIngredients, Integer> stocks;
    private final EnumMap<EnumIngredients, Integer> stocksInitial = new EnumMap<>(EnumIngredients.class);

    public IngredientsManager() {
        for (EnumIngredients ingredients: EnumIngredients.values()) {
            stocksInitial.put(ingredients, 0);
        }

        stocks = stocksInitial;
    }

    public static IngredientsManager getInstance() {
        if (instance == null) {
            instance = new IngredientsManager();
        }
        return instance;
    }

    /**
     * @return nombre d'ingrédients manquants
     */
    public int ingredientsManquants(EnumIngredients ingredient) {
        return Math.max(stocks.get(ingredient) - stocksInitial.get(ingredient), 0);
    }

    /**
     * Ajoute des ingrédients au stock
     *
     * @param nbAjout nombre d'ingrédients ajoutés
     */
    public void ajoutDIngredients(EnumIngredients ingredient, int nbAjout) {
        stocks.put(ingredient, stocks.get(ingredient) + nbAjout);
    }

    /**
     * Utilise un ingrédient
     */
    public void addIngredient(EnumIngredients ingredient) {
        stocks.put(ingredient, stocks.get(ingredient) - 1);
    }
}
