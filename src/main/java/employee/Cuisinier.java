package employee;

public class Cuisinier extends Employee {
    public Cuisinier(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_);
    }
// truc simultané poue cuisiner barman? je sais plus
    @Override
    public void preparerCommande() {

    }
}
