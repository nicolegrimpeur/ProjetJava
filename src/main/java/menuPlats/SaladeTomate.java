package menuPlats;

import ingredients.EnumIngredients;

public class SaladeTomate extends Salades {
    public SaladeTomate() {
        super("Salade Tomate");
    }

    @Override
    public void initIngredients() {
        listIngredients.add(EnumIngredients.SALADE);
        listIngredients.add(EnumIngredients.TOMATE);
    }
}
