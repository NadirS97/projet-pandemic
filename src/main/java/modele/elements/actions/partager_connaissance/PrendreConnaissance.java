package modele.elements.actions.partager_connaissance;

import modele.elements.PionJoueur;
import modele.elements.actions.IAction;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.JoueursNonPresentMemeVilleException;
import modele.exceptions.NbActionsMaxTourAtteintException;


public class PrendreConnaissance implements IAction {

    PionJoueur pionJoueur2;

    public PrendreConnaissance(PionJoueur pionJoueur2) {
        this.pionJoueur2 = pionJoueur2;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        // Verif nb actions
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur2.getVilleActuelle().equals(pionJoueur.getVilleActuelle()))
            throw new JoueursNonPresentMemeVilleException();
        if (!pionJoueur2.isVilleOfCarteVilleDeckJoueur(pionJoueur2.getVilleActuelle()))
            throw new CarteVilleInexistanteDansDeckJoueurException("Le joueur ne possède pas la carte ville dans sa main");
        pionJoueur.getDeckJoueur().add(pionJoueur2.defausseCarteVilleDeDeckJoueur(pionJoueur2.getVilleActuelle()));


    }

}
