package employee;

public class Serveur extends Employee {
    public int tableAttribue;

    public Serveur(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_);
    }

    @Override
    public void preparerCommande() {

    }
}
