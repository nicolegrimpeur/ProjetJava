package employee;

public class Manager extends Employee {
    public Manager(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_, "Manager");
    }
    public Manager() {
        super("", "", 0, "");
    }
}
