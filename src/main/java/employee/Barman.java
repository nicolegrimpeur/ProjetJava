package employee;

public class Barman extends Employee {
    public Barman(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_, "Barman");
    }
    // truc simultané poue cuisiner barman? je sais plus
    @Override
    public void preparerCommande() {

    }


}
