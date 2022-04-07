package menuPlats;

import ingredients.EnumIngredients;

public class PizzaTomateEmmental extends Pizzas {
    public PizzaTomateEmmental() {
        super("Pizza Tomate Emmental");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.MOZZARELLA);
        listIngredients.add(EnumIngredients.TOMATE);
        listIngredients.add(EnumIngredients.EMMENTAL);
    }
}
