package ingredients;

public enum EnumIngredients {
    CHAMPIGNON("Champignon"),
    EMMENTAL("Emmental"),
    MOZZARELLA("Mozzarella"),
    OIGNON("Oignon"),
    PAINBURGER("Pain Burger"),
    SALADE("Salade"),
    SAUCISSE("Saucisse"),
    STEAK("Steak"),
    TOMATE("Tomate"),
    GALETTE("Galette"),
    RIZ("Riz"),
    POULET("Poulet");

    private final String name;
    private int stocks;
    private int stocksInitial;
    private int stocksFinDeJournee;

    EnumIngredients(String name_) {
        name = name_;
        stocksInitial = 10;

        stocks = stocksInitial;
        stocksFinDeJournee = stocksInitial;
    }

    public String getName() {
        return name;
    }

    public int getStocksInitial() {
        return stocksInitial;
    }

    public void setStocksInitial(int stockInitial_) {
        stocksInitial = stockInitial_;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }

    public int getStocksFinDeJournee() {
        return stocksFinDeJournee;
    }

    public void setStocksFinDeJournee(int stockFinDeJournee) {
        this.stocksFinDeJournee = stockFinDeJournee;
    }


    /**
     * @return nombre d'ingrédients manquants
     */
    public int ingredientsManquants() {
        return Math.max(stocksInitial - stocksFinDeJournee, 0);
    }

    /**
     * Enlève un nombre nbIngredientAEnlever d'ingrédient dans les stocks
     */
    public void enleverIngredient(int nbIngredientAEnlever) {
        stocks = stocks - nbIngredientAEnlever;
        stocksFinDeJournee = stocks;
    }

    /**
     * Ajoute un nombre nbIngredientARajouter d'ingrédient dans les stocks
     */
    public void rajouterIngredient(int nbIngredientARajouter) {
        stocks = stocks + nbIngredientARajouter;
        stocksFinDeJournee = stocks;
    }
}
