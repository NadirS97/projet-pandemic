package modele.elements.actions.deplacement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.Deplacement;
import modele.elements.actions.IAction;
import modele.elements.cartes.CarteJoueur;
import modele.elements.enums.NomsRoles;
import modele.exceptions.*;

import java.io.Serializable;
@NoArgsConstructor
@Getter
@Setter
public class DeplacementVolDirect implements IAction, Deplacement, Serializable {

    private Ville villeDestination;

    public DeplacementVolDirect(Ville villeDestination) {
        this.villeDestination = villeDestination;
    }

    /**
     * Fonction permettant d'executer l'action DeplacementVolDirect :
     * défausser une carte ville pour déplacer le pion sur la ville de la carte défaussée
     * @param pionJoueur
     * @throws NbActionsMaxTourAtteintException
     * @throws VilleIntrouvableException
     * @throws CarteVilleInexistanteDansDeckJoueurException
     * @throws VilleDestinationEstVilleActuelleException
     */
    @Override
    public void execAction(PionJoueur pionJoueur) throws NbActionsMaxTourAtteintException, VilleIntrouvableException, CarteVilleInexistanteDansDeckJoueurException, VilleDestinationEstVilleActuelleException {
        if(pionJoueur.getVilleActuelle().equals(villeDestination))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur.getPlateau().isVille(villeDestination.getNomVille()))
            throw new VilleIntrouvableException(villeDestination.getNomVille()+" non trouvé");
        if (!pionJoueur.estVilleOfCarteVilleDeckJoueur(villeDestination))
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
