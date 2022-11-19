package modele.elements.actions.deplacement;

import modele.elements.actions.Deplacement;
import modele.elements.actions.IAction;
import modele.elements.PionJoueur;
import modele.elements.Ville;
import modele.elements.enums.NomsRoles;
import modele.exceptions.*;

public class DeplacementVoiture implements IAction, Deplacement {

    private Ville villeDestination;


    public DeplacementVoiture(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws VilleDestinationEstVilleActuelleException, NbActionsMaxTourAtteintException, VilleIntrouvableException, VilleNonVoisineException {
        if (pionJoueur.getVilleActuelle().equals(villeDestination))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur.getPlateau().isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille() + " non trouvé");
        if (!pionJoueur.getPlateau().isVilleVoisine(pionJoueur.getVilleActuelle(), villeDestination))
            throw new VilleNonVoisineException();
         if (pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.SPECIALISTE_EN_MISE_EN_QUARANTAINE)) {
             conditionRoleAffectantDeplacement(pionJoueur, pionJoueur.getVilleActuelle(), villeDestination);
         }
        pionJoueur.setVilleActuelle(villeDestination);
    }

    @Override
    public void conditionRoleAffectantDeplacement(PionJoueur pionJoueur, Ville villeDepart, Ville villeArrive) throws VilleIntrouvableException {
        pionJoueur.getPlateau().specialisteQuarantainePartVille(villeDepart);
        pionJoueur.getPlateau().specialisteQuarantaineArriveVille(villeArrive);
    }
}
