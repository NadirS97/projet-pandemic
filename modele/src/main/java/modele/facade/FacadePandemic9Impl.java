package modele.facade;

import modele.elements.actions.IAction;
import modele.elements.Partie;
import modele.elements.Plateau;
import modele.exceptions.*;
import modele.elements.PionJoueur;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FacadePandemic9Impl implements FacadePandemic9 {

    private Map<String, Partie> parties;

    public FacadePandemic9Impl() {
        this.parties = new HashMap<>();
    }

    @Override
    public String creerPartie(String pseudoJoueurPartie) throws CasCouleurVilleIncorrectException {
        Partie partie = new Partie(pseudoJoueurPartie);
        parties.put(partie.getCodePartie(), partie);
        return partie.getCodePartie();
    }

    @Override
    public void rejoindrePartie(String codePartie, String pseudoJoueurPartie) throws PseudoDejaExistantException, CodePartieInexistantException, DonneManquanteException {
        if (Objects.isNull(parties.get(codePartie)))
            throw new CodePartieInexistantException();
        if (parties.get(codePartie).isJoueurDejaDansPartie(pseudoJoueurPartie))
            throw new PseudoDejaExistantException();

        Plateau plateau = parties.get(codePartie).getPlateauPartie();
        PionJoueur nouveauPionJoueur = new PionJoueur(pseudoJoueurPartie, plateau,4);
        parties.get(codePartie).getJoueursPartie().put(pseudoJoueurPartie, nouveauPionJoueur);
    }


    @Override
    public void jouerAction(PionJoueur joueurActuel, IAction action) throws Exception {
        joueurActuel.setAction(action);
        joueurActuel.executerAction();
    }

    public void piocherCartes(PionJoueur joueur){
        joueur.piocherCartes();
    }

    @Override
    public boolean estPartieTerminee(String pseudoJoueurPartie) throws CodePartieInexistantException {
        return false;
    }
}