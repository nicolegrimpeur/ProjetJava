package menuPlats;

public class BurgerSaladeSteak extends Burgers{
    public BurgerSaladeSteak() {
        super("Burger Salade Steak");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.Salade.getInstance());
        listIngredients.add(ingredients.Steak.getInstance());
        listIngredients.add(ingredients.PainBurger.getInstance());
    }
}
