package modele.facade;

import modele.elements.actions.IAction;
import modele.elements.cartes.CarteEvenement;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.util.List;

public interface FacadePandemic9 {

    // retourne l'id de la partie générer aléatoirement (afin de gérer plusieurs parties potentiel créer par un même joueur)
    // l'id retourné pourra être utiliser par un autre utiliseur pour rejoindre sa partie




    void jouerTour(List<IAction> listeAction) throws Exception, EchecDeLaPartieException, TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException;

    void jouerAction(PionJoueur joueurActuel, IAction action) throws Exception;


    void jouerEvent(PionJoueur joueurActuel, CarteEvenement carteEvenement) throws Exception;

    void piocherCartes(PionJoueur joueur) throws PlusDeCarteJoueursException, TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException;

    void propagation(PionJoueur joueur) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException;
}
