package employee;

import ingredients.EnumIngredients;
import ingredients.IngredientsManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

public class Manager extends Employee {
    public Manager(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_);
    }

    @Override
    public void preparerCommande() {
    }

    public void listeDeCourses() throws IOException {
        PrintWriter writer = new PrintWriter("courses.txt", StandardCharsets.UTF_8);

        writer.println("A acheter : ");
        for (EnumIngredients ingredient: EnumIngredients.values()) {
            writer.println(ingredient.getName() + " : " + IngredientsManager.getInstance().ingredientsManquants(ingredient));
        }

        writer.close();
    }
}
