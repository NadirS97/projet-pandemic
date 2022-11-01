package programme;

import modele.elements.Plateau;
import modele.elements.actions.traiter_maladie.TraiterMaladie;
import modele.exceptions.*;


import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) {
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
            plateau.getToutesLesCartesRolesExistante().forEach(carteRole -> {
                System.out.println(carteRole);
            });
            plateau.getLesVirus().values().forEach(virus -> {
                System.out.println(virus);
            });
        } catch (FileNotFoundException | VilleIntrouvableException | VirusIntrouvableException |
                 RoleIntrouvableException | EvenementInnexistantException e){
            throw new RuntimeException(e);
        }
//      ================================================================================================================
    }
}
