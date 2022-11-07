//package programme;
//
//import modele.elements.PionJoueur;
//import modele.elements.Plateau;
//import modele.elements.Ville;
//import modele.elements.cartes.effets.evenements.EffetTypePontAerienImpl;
//import modele.elements.cartes.evenements.PontAerien;
//import modele.exceptions.EffetManquantException;
//import modele.exceptions.VirusIntrouvableException;
//import modele.utils.DonneesVariablesStatiques;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import java.util.Optional;
//
//public class Test {
//
//    public static void main(String[] args) {
//        try {
//            Plateau plateau = new Plateau("modele/src/main/resources/DonneesPlateau.json");
//            PontAerien pontAerien = new PontAerien(plateau);
//            PionJoueur pionJoueur = new PionJoueur("nadir", plateau, DonneesVariablesStatiques.nbActionsMaxParTour);
//            Ville ville = new Ville("Test");
//            pontAerien.effet(Optional.of(new EffetTypePontAerienImpl(true, pionJoueur, ville)));
//            //Optional.empty() pour le cas d'un effet sans parametres
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
