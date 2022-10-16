package programme;

import modele.elements.Joueur;
import modele.elements.Ville;
import modele.elements.Virus;
import modele.elements.cartes.CarteJoueur;
import modele.elements.cartes.CarteVille;
import modele.elements.enums.Couleurs;
import modele.exceptions.CasCouleurVilleIncorrectException;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;
import modele.facade.FacadePandemic9Impl;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws CasCouleurVilleIncorrectException, VilleInexistanteDansDeckJoueurException {
       FacadePandemic9Impl facadePandemic9Impl = new FacadePandemic9Impl();
//       facadePandemic9Impl.initialisation();
//       facade.jouerTour(new Joueur());


    }
}
