package modele.facade;

import modele.elements.actions.IAction;
import modele.exceptions.*;
import modele.elements.PionJoueur;

public interface FacadePandemic9 {

    // retourne l'id de la partie générer aléatoirement (afin de gérer plusieurs parties potentiel créer par un même joueur)
    // l'id retourné pourra être utiliser par un autre utiliseur pour rejoindre sa partie


    void jouerAction(PionJoueur joueurActuel, IAction action) throws Exception;



}
