package menuPlats;

public class BurgerSaladeTomateSteak extends Burgers {
    public BurgerSaladeTomateSteak() {
        super("Burger Salade Tomate Steak");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.Salade.getInstance());
        listIngredients.add(ingredients.PainBurger.getInstance());
        listIngredients.add(ingredients.Tomate.getInstance());
        listIngredients.add(ingredients.Steak.getInstance());
    }
}
