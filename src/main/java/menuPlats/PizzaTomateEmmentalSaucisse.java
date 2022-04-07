package menuPlats;

import ingredients.EnumIngredients;

public class PizzaTomateEmmentalSaucisse extends Pizzas {
    public PizzaTomateEmmentalSaucisse() {
        super("Pizza Tomate Emmental Saucisse");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.MOZZARELLA);
        listIngredients.add(EnumIngredients.TOMATE);
        listIngredients.add(EnumIngredients.EMMENTAL);
        listIngredients.add(EnumIngredients.SAUCISSE);
    }
}
