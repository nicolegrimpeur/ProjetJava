package menuPlats;

import ingredients.EnumIngredients;

public class BurgerSteak extends Burgers {
    public BurgerSteak() {
        super("Burger Steak");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.PAINBURGER);
        listIngredients.add(EnumIngredients.STEAK);
    }
}
