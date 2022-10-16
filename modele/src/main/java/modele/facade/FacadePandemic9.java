package modele.facade;

import modele.elements.Partie;
import modele.elements.Plateau;
import modele.elements.Ville;
import modele.elements.enums.Actions;
import modele.elements.enums.ModesDeplacements;
import modele.exceptions.*;
import modele.elements.Joueur;

import java.util.Map;

public interface FacadePandemic9 {



    // retourne l'id de la partie générer aléatoirement (afin de gérer plusieurs parties potentiel créer par un même joueur)
    // l'id retourné pourra être utiliser par un autre utiliseur pour rejoindre sa partie
    String creerPartie(String joueur) throws CasCouleurVilleIncorrectException;

    void rejoindrePartie(String codePartie, String joueur)
            throws CodePartieInexistantException,
            DonneManquanteException,
            PseudoDejaExistantException;





    void jouerTour(String codePartie, Actions action, ModesDeplacements modeDeplacementChoisis, Ville villeDestination) throws CodePartieInexistantException, VilleAvecAucuneStationDeRechercheException, VilleNonVoisineException, PseudoInexistantDansLaPartieException, VilleInexistanteDansDeckJoueurException;

    void jouerActionDeplacement(Joueur joueurPartie, ModesDeplacements modeDeplacementChoisis, Ville villeDestination) throws VilleAvecAucuneStationDeRechercheException, VilleNonVoisineException, VilleInexistanteDansDeckJoueurException;

    boolean estPartieTerminee(String joueur) throws CodePartieInexistantException;


}
