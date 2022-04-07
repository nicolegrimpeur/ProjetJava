package menuPlats;

import ingredients.EnumIngredients;

public class SoupeChampignon extends Potages {
    public SoupeChampignon() {
        super("Soupe Champignon");
    }

    @Override
    public void initIngredients() {
        for (int i = 0; i < 3; i++) {
            listIngredients.add(EnumIngredients.CHAMPIGNON);
        }
    }
}
