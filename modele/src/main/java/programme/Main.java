package programme;

import modele.elements.Plateau;
import modele.exceptions.CasCouleurVilleIncorrectException;
import modele.exceptions.VilleInexistanteDansDeckJoueurException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VirusIntrouvableException;

import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws CasCouleurVilleIncorrectException, VilleInexistanteDansDeckJoueurException {
//       FacadePandemic9Impl facadePandemic9Impl = new FacadePandemic9Impl();
//       facadePandemic9Impl.initialisation();
//       facade.jouerTour(new Joueur());

        Plateau plateau = new Plateau();
        try {
            plateau.initialisationPlateau();
            plateau.getVilles().values().forEach(ville -> {
                System.out.println(ville);
            });
        } catch (FileNotFoundException | VilleIntrouvableException | VirusIntrouvableException e) {
            throw new RuntimeException(e);
        }


    }
}
