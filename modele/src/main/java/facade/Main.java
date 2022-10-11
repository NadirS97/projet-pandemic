package facade;

import modele.Joueur;

public class Main {

    public static void main(String[] args) {
       Facade facade = new Facade();
       facade.initialisation();
       facade.displaybullshit();
       facade.jouerTour(new Joueur());
    }
}
