package modele.facade;

import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.Plateau;
import modele.elements.cartes.CarteEvenement;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    public void jouerEvent(PionJoueur joueurActuel, CarteEvenement carteEvenement) throws Exception {
        joueurActuel.jouerCarteEvenement(carteEvenement);
    }

    public void piocherCartes(PionJoueur joueur){
        joueur.piocherCartes();
    }

    public void propagation(PionJoueur joueur) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException {
        joueur.getPlateau().initialiserPropagation();
    }
}