package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleIntrouvableException;

public class DeplacementVolCharter implements IAction {

    /*
     * Défausser la carte ville correspondant à la ville où se trouve le pion pour atteindre n’importe quelle autre ville du plateau
     * Pour cela on vérifie que le Joueur possède dans sa main/son deck la carteVille correspondante à la villeActuelle
     *
     */

    private Ville villeDestination;

    public DeplacementVolCharter(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {

        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur.getPlateau().isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille() + " non trouvé");
        if (!pionJoueur.isVilleOfCarteVilleDeckJoueur(pionJoueur.getVilleActuelle()))
            throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + pionJoueur.getVilleActuelle().getNomVille() + " n'est pas présente dans votre deck.");
        pionJoueur.defausseCarteVilleDeDeckJoueur(pionJoueur.getVilleActuelle());
        pionJoueur.setVilleActuelle(villeDestination);

    }

}

