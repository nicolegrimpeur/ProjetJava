package ingredients;

import java.util.EnumMap;

public class IngredientsManager {
    private static IngredientsManager instance = null;

    public final EnumMap<EnumIngredients, Integer> stocks = new EnumMap<>(EnumIngredients.class);

    public IngredientsManager() {
        for (EnumIngredients ingredient: EnumIngredients.values()) {
            stocks.put(ingredient, ingredient.getStockInitial());
        }
    }

    public static IngredientsManager getInstance() {
        if (instance == null) {
            instance = new IngredientsManager();
        }
        return instance;
    }

    /**
     * Permet au lancement de la journée de remettre à 0 le stock initial avec le stock présent en début de journée
     */
    public void initStocksInitial() {
        for (EnumIngredients ingredients: stocks.keySet())
            ingredients.setStockInitial(stocks.get(ingredients));
    }

    /**
     * @return nombre d'ingrédients manquants
     */
    public int ingredientsManquants(EnumIngredients ingredient) {
        return Math.max(stocks.get(ingredient) - ingredient.getStockInitial(), 0);
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
    public void addIngredient(EnumIngredients ingredient, int nbIngredientAEnlever) {
        stocks.put(ingredient, stocks.get(ingredient) - nbIngredientAEnlever);
    }

    /**
     * Permet de rajouter un ingrédient
     */
    public void rajouterIngredient(EnumIngredients ingredient, int nbIngredientARajouter) {
        stocks.put(ingredient, stocks.get(ingredient) + nbIngredientARajouter);
    }
}
