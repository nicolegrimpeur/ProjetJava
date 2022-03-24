package employee;

import java.util.ArrayList;
import java.util.Scanner;

public class Serveur extends Employee {
    public int tableAttribue;
    public static ArrayList<Menu> listRepas = new ArrayList<Menu>();
    public static ArrayList<Boisson> listBoisson = new ArrayList<Boisson>();

    public Serveur(String nom_, String prenom_, int salaire_) {
        super(nom_, prenom_, salaire_);
    }

    @Override
    public void preparerCommande() {
        System.out.println("Combien de clients y a-t-il ?");
        Scanner scanner = new Scanner(System.in);
        int nbrCustomers = scanner.nextInt();
        int facture = 0;

        for (int i = 0; i < nbrCustomers; i++) {
            System.out.println("Plat du client n°" + (i + 1));
            System.out.println("1- Salade Tomates");
            System.out.println("2- Salade");
            System.out.println("3- Potage Oignon");
            System.out.println("4- Potage Tomate");
            System.out.println("5- Potage Champignons");
            System.out.println("6- Burger Salade Tomate Steak");
            System.out.println("7- Burger Salade Steak");
            System.out.println("8- Burger Steak");
            System.out.println("9- Pizza Nature");
            System.out.println("10- Pizza Champignon");
            System.out.println("11- Pizza Saucisse");
            Scanner scanner1 = new Scanner(System.in);

            switch (scanner1) {
                case 1:
                    listRepas.add();
                    break;
                case 2:
                    listRepas.add();
                    break;
                case 3:
                    listRepas.add();
                    break;
                case 4:
                    listRepas.add();
                    break;
                case 5:
                    listRepas.add();
                    break;
                case 6:
                    listRepas.add();
                    break;
                case 7:
                    listRepas.add();
                    break;
                case 8:
                    listRepas.add();
                    break;
                case 9:
                    listRepas.add();
                    break;
                case 10:
                    listRepas.add();
                    break;
                case 11:
                    listRepas.add();
                    break;

                default:
                    System.out.println("Commande inconnue");
            }

            for (int i = 0; i < nbrCustomers; i++) {
                System.out.println("Boisson du client n°" + (i + 1));
                System.out.println("1- Limonade");
                System.out.println("2- Cidre doux");
                System.out.println("3- Biere sans alcool");
                System.out.println("4- Jus de fruit");
                System.out.println("5- Verre d'eau");
                Scanner scanner2 = new Scanner(System.in);

                switch (scanner2) {
                    case 1:
                        listBoisson.add();
                        break;
                    case 2:
                        listBoisson.add();
                        break;
                    case 3:
                        listBoisson.add();
                        break;
                    case 4:
                        listBoisson.add();
                        break;
                    case 5:
                        listBoisson.add();
                        break;

                    default:
                        System.out.println("Commande inconnue");
                }
            }

            //CHECK INGREDIENTS AVANT PROPOSER MENU

        }
    }
}
