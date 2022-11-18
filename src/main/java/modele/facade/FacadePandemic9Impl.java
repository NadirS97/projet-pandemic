package modele.facade;

import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.cartes.CarteEvenement;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.util.List;

public class FacadePandemic9Impl implements FacadePandemic9 {


    Partie partie;

    public FacadePandemic9Impl(int nbJoueurs) throws Exception {
        partie = new Partie(nbJoueurs);
    }



    @Override
    public void jouerTour(List<IAction> listeAction) throws Exception, EchecDeLaPartieException {
        for (IAction action : listeAction){
            jouerAction(partie.getJoueurActuel(),action);
        }
        try {
            piocherCartes(partie.getJoueurActuel());
        }
        catch (PlusDeCarteJoueursException e){
            throw new EchecDeLaPartieException();
        }
        propagation(partie.getJoueurActuel());
        partie.joueurSuivant();

    }
    @Override
    public void jouerAction(PionJoueur joueurActuel, IAction action) throws Exception {
        joueurActuel.setAction(action);
        joueurActuel.executerAction();
    }

    @Override
    public void jouerEvent(PionJoueur joueur, CarteEvenement carteEvenement) throws Exception {
        joueur.jouerCarteEvenement(carteEvenement);
    }

    @Override
    public void piocherCartes(PionJoueur joueurActuel) throws PlusDeCarteJoueursException {
        joueurActuel.piocherCartes();
    }

    @Override
    public void propagation(PionJoueur joueurActuel) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException {
        joueurActuel.getPlateau().initialiserPropagation();
    }
}