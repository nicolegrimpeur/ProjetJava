package menuPlats;

import ingredients.Ingredient;

import java.util.ArrayList;

public abstract class Plat {
    public String name;
    public int prix;
    public ArrayList<Ingredient> listIngredients = new ArrayList<>();

    public Plat(String name_) {
        name = name_;
    }
}
