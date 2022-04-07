package menuPlats;

import ingredients.EnumIngredients;

public class BurgerSaladeTomateSteak extends Burgers {
    public BurgerSaladeTomateSteak() {
        super("Burger Salade Tomate Steak");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.PAINBURGER);
        listIngredients.add(EnumIngredients.SALADE);
        listIngredients.add(EnumIngredients.TOMATE);
        listIngredients.add(EnumIngredients.STEAK);
    }
}
