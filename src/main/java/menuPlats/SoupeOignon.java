package menuPlats;

public class SoupeOignon extends Potages {
    public SoupeOignon() {
        super("Soupe Oignon");
    }

    @Override
    public void initIngredients() {
        for (int i = 0; i < 3; i++) {
            listIngredients.add(ingredients.Oignon.getInstance());
        }
    }
}
