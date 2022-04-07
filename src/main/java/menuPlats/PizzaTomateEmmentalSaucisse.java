package menuPlats;

import ingredients.Ingredient;

public class PizzaTomateEmmentalSaucisse extends Pizzas {
    public PizzaTomateEmmentalSaucisse() {
        super("Pizza Tomate Emmental Saucisse");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.Mozzarella.getInstance());
        listIngredients.add(ingredients.Tomate.getInstance());
        listIngredients.add(Ingredient.getInstance());
        listIngredients.add(ingredients.Saucisse.getInstance());
    }
}
