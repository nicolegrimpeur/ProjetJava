package menuPlats;

import ingredients.EnumIngredients;

public class PizzaTomateEmmentalChampignon extends Pizzas{
    public PizzaTomateEmmentalChampignon() {
        super("Pizza Tomate Emmental Champignon");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.MOZZARELLA);
        listIngredients.add(EnumIngredients.TOMATE);
        listIngredients.add(EnumIngredients.EMMENTAL);
        listIngredients.add(EnumIngredients.CHAMPIGNON);
    }
}
