package modele.facade;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.enums.Actions;
import modele.elements.enums.ModesDeplacements;
import modele.exceptions.*;
import modele.elements.PionJoueur;

public interface FacadePandemic9 {

    // retourne l'id de la partie générer aléatoirement (afin de gérer plusieurs parties potentiel créer par un même joueur)
    // l'id retourné pourra être utiliser par un autre utiliseur pour rejoindre sa partie
    String creerPartie(String joueur) throws CasCouleurVilleIncorrectException;

    void rejoindrePartie(String codePartie, String joueur) throws Exception;

    void jouerTour(PionJoueur joueurActuel, IAction action) throws Exception;

    boolean estPartieTerminee(String joueur) throws Exception;

}
