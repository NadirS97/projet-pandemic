package modele.facade;

import modele.elements.Partie;
import modele.elements.Plateau;
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

    void jouerUnTour(String codePartie, String joueur )
        throws CodePartieInexistantException,
            PseudoInexistantDansLaPartieException;


    boolean estPartieTerminee(String joueur) throws CodePartieInexistantException;


}
