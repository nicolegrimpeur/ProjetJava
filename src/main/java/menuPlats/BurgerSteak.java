package menuPlats;

public class BurgerSteak extends Burgers {
    public BurgerSteak() {
        super("Burger Steak");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.PainBurger.getInstance());
        listIngredients.add(ingredients.Steak.getInstance());
    }
}
