package menuPlats;

import ingredients.Ingredient;

public class PizzaTomateEmmentalChampignon extends Pizzas{
    public PizzaTomateEmmentalChampignon() {
        super("Pizza Tomate Emmental Champignon");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.Mozzarella.getInstance());
        listIngredients.add(ingredients.Tomate.getInstance());
        listIngredients.add(Ingredient.getInstance());
        listIngredients.add(ingredients.Champignon.getInstance());
    }
}
