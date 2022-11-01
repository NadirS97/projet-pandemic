package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.exceptions.CarteVilleInexistanteDansDeckJoueurException;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleDestinationEstVilleActuelleException;
import modele.exceptions.VilleIntrouvableException;

public class DeplacementVolDirect implements IAction {

    private Ville villeDestination;

    public DeplacementVolDirect(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if(pionJoueur.getVilleActuelle().equals(villeDestination))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur.getPlateau().isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille()+" non trouvé");
        if (!pionJoueur.isVilleOfCarteVilleDeckJoueur(villeDestination))
            throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + villeDestination.getNomVille() + " n'est pas présente dans votre main.");
        pionJoueur.defausseCarteVilleDeDeckJoueur(villeDestination);
        pionJoueur.setVilleActuelle(villeDestination);
    }
}
