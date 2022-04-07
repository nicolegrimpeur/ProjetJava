package menuPlats;

public class SaladeTomate extends Salades {
    public SaladeTomate() {
        super("Salade Tomate");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.Salade.getInstance());
        listIngredients.add(ingredients.Tomate.getInstance());
    }
}
