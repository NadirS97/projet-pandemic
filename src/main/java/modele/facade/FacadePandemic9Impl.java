package modele.facade;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.cartes.CarteEvenement;
import modele.elements.cartes.CarteJoueur;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.util.List;

public class FacadePandemic9Impl implements FacadePandemic9 {


    Partie partie;

    public FacadePandemic9Impl(int nbJoueurs) throws Exception {
        partie = new Partie(nbJoueurs);
    }



    @Override
    public void jouerTour(List<IAction> listeAction) throws Exception, TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException {
        for (IAction action : listeAction){
            jouerAction(partie.getJoueurActuel(),action);
        }
            piocherCartes(partie.getJoueurActuel());
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
    public void piocherCartes(PionJoueur joueurActuel) throws TropDeCarteEnMainException, EchecDeLaPartiePlusDeCarteJoueurException {

            joueurActuel.piocherCartes();

    }

    public void defausserCartesJoueur(PionJoueur joueurActuel, List<CarteJoueur> cartesJoueursDefausser) throws CarteJoueurInexistanteDansDeckException {
        joueurActuel.defausserListeCarteJoueurEnTrop(cartesJoueursDefausser);
    }

    @Override
    public void propagation(PionJoueur joueurActuel) throws VilleDejaEclosException, NuitTranquilleException, NbCubesAAjouterInvalideException {
        joueurActuel.getPlateau().initialiserPropagation();
    }


    @Override
    public void repartiteurActionDeplacementAutrePion(PionJoueur joueurActuel, PionJoueur joueurCible, IAction action) throws Exception {
        joueurActuel.setAction(action);
        joueurActuel.repartiteurActionDeplacementAutrePion(joueurCible);
    }

    public void repartiteurDeplacementPion(PionJoueur joueurActuel,PionJoueur joueurCible, Ville villeDestination) throws AucunJoueurDansVilleDestinationException, AutorisationManquanteException {
        joueurActuel.repartiteurDeplacementPion(joueurCible,villeDestination);
    }
}