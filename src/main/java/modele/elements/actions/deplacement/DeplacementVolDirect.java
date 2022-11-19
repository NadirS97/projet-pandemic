package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.Deplacement;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.NomsRoles;
import modele.exceptions.*;

public class DeplacementVolDirect implements IAction, Deplacement {

    private Ville villeDestination;

    public DeplacementVolDirect(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws NbActionsMaxTourAtteintException, VilleIntrouvableException, CarteVilleInexistanteDansDeckJoueurException, VilleDestinationEstVilleActuelleException {
        if(pionJoueur.getVilleActuelle().equals(villeDestination))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur.getPlateau().isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille()+" non trouvé");
        if (!pionJoueur.isVilleOfCarteVilleDeckJoueur(villeDestination))
            throw new CarteVilleInexistanteDansDeckJoueurException("La carte ville correspondante à " + villeDestination.getNomVille() + " n'est pas présente dans votre main.");
        if (pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.SPECIALISTE_EN_MISE_EN_QUARANTAINE)) {
            conditionRoleAffectantDeplacement(pionJoueur, pionJoueur.getVilleActuelle(), villeDestination);
        }
        CarteJoueur carteVille = pionJoueur.defausseCarteVilleDeDeckJoueur(villeDestination);
        pionJoueur.getPlateau().defausserCarteJoueur(carteVille);
        pionJoueur.setVilleActuelle(villeDestination);
    }

    @Override
    public void conditionRoleAffectantDeplacement(PionJoueur pionJoueur, Ville villeDepart, Ville villeArrive) throws VilleIntrouvableException {
        pionJoueur.getPlateau().specialisteQuarantainePartVille(villeDepart);
        pionJoueur.getPlateau().specialisteQuarantaineArriveVille(villeArrive);
    }


}
