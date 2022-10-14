package programme;

import modele.exceptions.CasCouleurVilleIncorrectException;
import modele.facade.FacadePandemic9Impl;

public class Main {

    public static void main(String[] args) throws CasCouleurVilleIncorrectException {
       FacadePandemic9Impl facadePandemic9Impl = new FacadePandemic9Impl();
       facadePandemic9Impl.initialisation();
//       facade.jouerTour(new Joueur());


    }
}
