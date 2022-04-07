package menuPlats;

public class Salade extends Salades {
    public Salade() {
        super("Salade");
        listIngredients.add(ingredients.Salade.getInstance());
    }

    @Override
    public void initIngredients() {
        listIngredients.add(ingredients.Salade.getInstance());
    }
}
