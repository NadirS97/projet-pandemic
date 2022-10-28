package programme;

import modele.elements.Plateau;
import modele.exceptions.*;
import modele.facade.FacadePandemic9Impl;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws CasCouleurVilleIncorrectException, VilleInexistanteDansDeckJoueurException {
       FacadePandemic9Impl facadePandemic9Impl = new FacadePandemic9Impl();
//       facadePandemic9Impl.initialisation();
//       facadePandemic9Impl.JouerTourr("111",new DeplacementNAvette(new Ville()));
//       facadePandemic9Impl.JouerTourr("211",new PartageConnaissance());
//       facade.jouerTour(new Joueur());

//      ================================================================================================================
        Plateau plateau = new Plateau();
        try {
            plateau.initialisationPlateau();
            plateau.getVilles().values().forEach(ville -> {
                System.out.println(ville);
            });
            plateau.getListeCartesRole().forEach(carteRole -> {
                System.out.println(carteRole);
            });
        } catch (FileNotFoundException | VilleIntrouvableException | VirusIntrouvableException |
                 CouleurPionIntrouvableException | EffetIntrouvableException | TypeCarteIntrouvableException e) {
            throw new RuntimeException(e);
        }
//      ================================================================================================================


    }
}
