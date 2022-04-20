//package menuBoissons;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class BoissonsManager {
//    private static BoissonsManager instance = null;
//    public final Map<String, EnumBoissons> listNoms = new HashMap<>();
//
//    public BoissonsManager() {
//        for (EnumBoissons boisson: EnumBoissons.values()) {
//            listNoms.put(boisson.getName(), boisson);
//        }
//    }
//
//    public static BoissonsManager getInstance() {
//        if (instance == null) {
//            instance = new BoissonsManager();
//        }
//        return instance;
//    }
//}
