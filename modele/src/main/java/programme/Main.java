package programme;

import modele.elements.Plateau;
import modele.exceptions.*;


import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws CasCouleurVilleIncorrectException, VilleInexistanteDansDeckJoueurException {
//       FacadePandemic9Impl facadePandemic9Impl = new FacadePandemic9Impl();
//       facadePandemic9Impl.initialisation();
//       facadePandemic9Impl.JouerTourr("111",new DeplacementNAvette(new Ville()));
//       facadePandemic9Impl.JouerTourr("211",new PartageConnaissance());
//       facade.jouerTour(new Joueur());

//      ================================================================================================================
        Plateau plateau = new Plateau();
        try {
            plateau.initialisationPlateau("modele/src/main/resources/DonneesPlateau.json");
            plateau.getVilles().values().forEach(ville -> {
                System.out.println(ville);
            });
        } catch (FileNotFoundException | VilleIntrouvableException | VirusIntrouvableException e ) {
            throw new RuntimeException(e);
        }
//      ================================================================================================================


    }
}
