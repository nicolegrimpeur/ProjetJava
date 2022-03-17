package employee;

public abstract class Employee {
    public String nom;
    public String prenom;
    public int salaire;
    public int nbJoursConsecutif = 0;

    public Employee(String nom_, String prenom_, int salaire_) {
        nom = nom_;
        prenom = prenom_;
        salaire = salaire_;
    }

    public abstract void preparerCommande();
}
