package employee;

public abstract class Employee {
    public String nom;
    public String prenom;
    public int salaire;
    public String job;
    public int nbJoursConsecutif = 0;

    public Employee(String nom_, String prenom_, int salaire_, String job_) {
        nom = nom_;
        prenom = prenom_;
        salaire = salaire_;
        job = job_;
    }

    public abstract void preparerCommande();

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
}
