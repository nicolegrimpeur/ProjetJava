package ingredients;

import java.util.EnumMap;
import java.util.Scanner;

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

    public static void initStocksInitial() {
        Scanner scanner;
        for (EnumIngredients ingredient: EnumIngredients.values()) {
            System.out.println(ingredient.getName() + " : ");
            scanner = new Scanner(System.in);
            ingredient.setStockInitial(scanner.nextInt());
        }
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
    public void addIngredient(EnumIngredients ingredient) {
        stocks.put(ingredient, stocks.get(ingredient) - 1);
    }
}
