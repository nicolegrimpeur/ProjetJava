package employee;

public abstract class Employee {
    public String nom;
    public String prenom;
    private String affichage = "";
    public int salaire;
    private String job = "";
    public int nbJoursConsecutifs = 0;

    public Employee(String nom_, String prenom_, int salaire_, String job_) {
        nom = nom_;
        prenom = prenom_;
        affichage = nom + " " + prenom;
        salaire = salaire_;
        job = job_;
    }

//    public abstract void preparerCommande();

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getSalaire() {
        return salaire;
    }

    public String getJob() {
        return job;
    }

    public String getAffichage() {
        return affichage;
    }
}
