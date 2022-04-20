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
    private int stockInitial;

    EnumIngredients(String name_) {
        name = name_;
        stockInitial = 10;
    }

    public String getName() {
        return name;
    }

    public int getStockInitial() {
        return stockInitial;
    }

    public void setStockInitial(int stockInitial_) {
        stockInitial = stockInitial_;
    }


}
