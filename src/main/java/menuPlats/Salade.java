package menuPlats;

import ingredients.EnumIngredients;

public class Salade extends Salades {
    public Salade() {
        super("Salade");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.SALADE);
    }
}
