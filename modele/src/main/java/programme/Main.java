package programme;

import exceptions.CasCouleurVilleIncorrectException;
import facade.Facade;
import modele.Joueur;
import modele.cartes.CarteEpidemie;
import modele.cartes.CarteEvenement;
import modele.cartes.CarteJoueur;
import modele.cartes.CarteVille;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws CasCouleurVilleIncorrectException {
       Facade facade = new Facade();
       facade.initialisation();
//       facade.jouerTour(new Joueur());


    }
}
