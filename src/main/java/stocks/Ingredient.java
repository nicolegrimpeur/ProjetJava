package stocks;

public class Ingredient {
    public String name;
    public int nbUnite;
    public int nbInitial;

    public int ingredientsManquants() {
        return nbInitial - nbUnite;
    }
}
