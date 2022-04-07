package menuPlats;

import ingredients.EnumIngredients;

public class SoupeTomate extends Potages {
    public SoupeTomate() {
        super("Soupe Tomate");
    }

    @Override
    public void initIngredients() {
        for (int i = 0; i < 3; i++) {
            listIngredients.add(EnumIngredients.TOMATE);
        }
    }
}
