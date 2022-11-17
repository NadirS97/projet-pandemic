package modele.facade;

import modele.elements.Partie;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteEvenement;
import modele.exceptions.*;
import modele.elements.PionJoueur;

public interface FacadePandemic9 {

    // retourne l'id de la partie générer aléatoirement (afin de gérer plusieurs parties potentiel créer par un même joueur)
    // l'id retourné pourra être utiliser par un autre utiliseur pour rejoindre sa partie



    void jouerAction(PionJoueur joueurActuel, IAction action) throws Exception;


    void jouerEvent(PionJoueur joueurActuel, CarteEvenement carteEvenement) throws Exception;

    void piocherCartes(PionJoueur joueur);

    void propagation(PionJoueur joueur) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException;
}
