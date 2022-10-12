package programme;

import exceptions.CasCouleurVilleIncorrectException;
import facade.Facade;
import modele.Joueur;

public class Main {

    public static void main(String[] args) throws CasCouleurVilleIncorrectException {
       Facade facade = new Facade();
       facade.initialisation();
       facade.displaybullshit();
//       facade.jouerTour(new Joueur());
    }
}
