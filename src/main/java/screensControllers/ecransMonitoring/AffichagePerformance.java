package screensControllers.ecransMonitoring;

public class AffichagePerformance {
    public String nom;
    public int nombre;

    public AffichagePerformance(String nom_, int nombre_) {
        nom = nom_;
        nombre = nombre_;
    }

    public String getNom() {
        return nom;
    }

    public int getNombre() {
        return nombre;
    }
}
