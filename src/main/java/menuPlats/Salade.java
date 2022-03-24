package menuPlats;

import ingredients.Ingredient;

import java.util.ArrayList;

public class Salade extends Plat {
    public Salade() {
        super("Salade");
        listIngredients = new ArrayList<>();
        listIngredients.add(ingredients.Salade.getInstance());
    }
}
