package menuPlats;

import ingredients.EnumIngredients;

import java.util.EnumMap;
import java.util.Objects;

public enum EnumPlats {
    BURGERSTEAK("Burger avec steak", 15, new EnumIngredients[]{EnumIngredients.PAINBURGER, EnumIngredients.STEAK}),
    BURGERSTEAKSALADE("Burger avec steak et salade", 15, new EnumIngredients[]{EnumIngredients.PAINBURGER, EnumIngredients.STEAK, EnumIngredients.SALADE}),
    BURGERSTEAKSALADETOMATE("Burger avec steak, salade et tomate", 15, new EnumIngredients[]{EnumIngredients.PAINBURGER, EnumIngredients.STEAK, EnumIngredients.SALADE, EnumIngredients.TOMATE}),
    PIZZAEMMENTALTOMATE("Pizza avec emmental et tomate", 12, new EnumIngredients[]{EnumIngredients.MOZZARELLA, EnumIngredients.EMMENTAL, EnumIngredients.TOMATE}),
    PIZZAEMMENTALTOMATECHAMPIGNON("Pizza avec emmental, tomate et champignons", 12, new EnumIngredients[]{EnumIngredients.MOZZARELLA, EnumIngredients.EMMENTAL, EnumIngredients.CHAMPIGNON}),
    PIZZAEMMENTALTOMATESAUCISSE("Pizza avec emmental, tomate et saucisse", 12, new EnumIngredients[]{EnumIngredients.MOZZARELLA, EnumIngredients.EMMENTAL, EnumIngredients.TOMATE, EnumIngredients.SAUCISSE}),
    SALADE("Salade", 9, new EnumIngredients[]{EnumIngredients.SALADE}),
    SALADETOMATE("Salade avec tomates", 9, new EnumIngredients[]{EnumIngredients.SALADE, EnumIngredients.TOMATE}),
    SOUPECHAMPIGNON("Soupe aux champignons", 8, new EnumIngredients[]{EnumIngredients.CHAMPIGNON, EnumIngredients.CHAMPIGNON, EnumIngredients.CHAMPIGNON}),
    SOUPEOIGNON("Soupe aux oignons", 8, new EnumIngredients[]{EnumIngredients.OIGNON, EnumIngredients.OIGNON, EnumIngredients.OIGNON}),
    SOUPETOMATE("Soupe à la tomate", 8, new EnumIngredients[]{EnumIngredients.TOMATE, EnumIngredients.TOMATE, EnumIngredients.TOMATE}),
    FAJITASPOULET("Fajitas au poulet", 11, new EnumIngredients[]{EnumIngredients.GALETTE, EnumIngredients.RIZ, EnumIngredients.POULET}),
    FAJITASSTEAK("Fajitas au steak", 11, new EnumIngredients[]{EnumIngredients.GALETTE, EnumIngredients.RIZ, EnumIngredients.STEAK});

    private final String name;
    private final int prix;
    private final EnumMap<EnumIngredients, Integer> listeIngredients = new EnumMap<>(EnumIngredients.class);

    EnumPlats(String name_, int prix_, EnumIngredients[] args) {
        name = name_;
        prix = prix_;

        for (EnumIngredients ingredient : args) {
            listeIngredients.put(ingredient, listeIngredients.containsKey(ingredient) ? listeIngredients.get(ingredient) + 1 : 1);
        }
    }

    public String getName() {
        return name;
    }

    public int getPrix() {
        return prix;
    }

    public EnumMap<EnumIngredients, Integer> getListeIngredients() {
        return listeIngredients;
    }

    public static EnumPlats rechercheParNom(String nom) {
        for (EnumPlats plat: values())
            if (Objects.equals(plat.getName(), nom))
                return plat;
        return null;
    }
}
