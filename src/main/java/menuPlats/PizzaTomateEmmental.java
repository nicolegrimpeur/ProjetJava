package menuPlats;

import ingredients.Ingredient;

public class PizzaTomateEmmental extends Pizzas {
    public PizzaTomateEmmental() {
        super("Pizza Tomate Emmental");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.Tomate.getInstance());
        listIngredients.add(Ingredient.getInstance());
        listIngredients.add(ingredients.Mozzarella.getInstance());
    }
}
