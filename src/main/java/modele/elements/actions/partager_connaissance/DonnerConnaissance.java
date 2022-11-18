package modele.elements.actions.partager_connaissance;

import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.JoueursNonPresentMemeVilleException;
import modele.exceptions.NbActionsMaxTourAtteintException;


public class DonnerConnaissance implements IAction {
    PionJoueur pionJoueur2;

    public DonnerConnaissance(PionJoueur pionJoueur2) {
        this.pionJoueur2 = pionJoueur2;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        // Verif nb actions
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur2.getVilleActuelle().equals(pionJoueur.getVilleActuelle()))
            throw new JoueursNonPresentMemeVilleException();
        if (!pionJoueur.isVilleOfCarteVilleDeckJoueur(pionJoueur.getVilleActuelle()))
            throw new CarteVilleInexistanteDansDeckJoueurException("Le joueur ne possède pas la carte ville dans sa main");
        pionJoueur2.getDeckJoueur().add(pionJoueur.defausseCarteVilleDeDeckJoueur(pionJoueur.getVilleActuelle()));


    }
}
