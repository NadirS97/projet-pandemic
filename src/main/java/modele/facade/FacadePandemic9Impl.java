package modele.facade;

import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.cartes.CarteEvenement;
import modele.exceptions.*;
import modele.elements.PionJoueur;

public class FacadePandemic9Impl implements FacadePandemic9 {


    Partie partie;

    public FacadePandemic9Impl(int nbJoueurs) throws Exception {
        partie = new Partie(nbJoueurs);
    }



    @Override
    public void jouerAction(PionJoueur joueurActuel, IAction action) throws Exception {
        joueurActuel.setAction(action);
        joueurActuel.executerAction();
    }

    @Override
    public void jouerEvent(PionJoueur joueurActuel, CarteEvenement carteEvenement) throws Exception {
        joueurActuel.jouerCarteEvenement(carteEvenement);
    }

    @Override
    public void piocherCartes(PionJoueur joueur){
        joueur.piocherCartes();
    }

    @Override
    public void propagation(PionJoueur joueur) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException {
        joueur.getPlateau().initialiserPropagation();
    }
}