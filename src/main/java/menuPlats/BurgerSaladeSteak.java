package menuPlats;

import ingredients.EnumIngredients;

public class BurgerSaladeSteak extends Burgers{
    public BurgerSaladeSteak() {
        super("Burger Salade Steak");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.PAINBURGER);
        listIngredients.add(EnumIngredients.SALADE);
        listIngredients.add(EnumIngredients.STEAK);
    }
}
