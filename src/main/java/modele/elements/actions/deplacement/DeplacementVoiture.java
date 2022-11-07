package modele.elements.actions.deplacement;

import modele.elements.actions.IAction;
import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleDestinationEstVilleActuelleException;
import modele.exceptions.VilleIntrouvableException;
import modele.exceptions.VilleNonVoisineException;

public class DeplacementVoiture implements IAction {

    private Ville villeDestination;


    public DeplacementVoiture(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if (pionJoueur.getVilleActuelle().equals(villeDestination))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur.getPlateau().isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille() + " non trouvé");
        if (!pionJoueur.getPlateau().isVilleVoisine(pionJoueur.getVilleActuelle(), villeDestination))
            throw new VilleNonVoisineException();
        pionJoueur.setVilleActuelle(villeDestination);
    }
}
