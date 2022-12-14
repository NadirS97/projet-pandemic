package modele.elements.actions.deplacement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.Deplacement;
import modele.elements.actions.IAction;
import modele.elements.enums.NomsRoles;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleDestinationEstVilleActuelleException;
import modele.exceptions.VilleIntrouvableException;

import java.io.Serializable;
@NoArgsConstructor
@Setter
@Getter
public class DeplacementNavette implements IAction , Deplacement, Serializable {

    private Ville villeDestination;

    public DeplacementNavette(Ville villeStationDeRecherche) {
        this.villeDestination = villeStationDeRecherche;
    }

    /**
     * Fonction permettant d'executer l'action DeplacementNavette :
     * déplacer le pion entre deux stations de recherche
     * @param pionJoueur
     * @throws VilleDestinationEstVilleActuelleException
     * @throws NbActionsMaxTourAtteintException
     * @throws VilleAvecAucuneStationDeRechercheException
     * @throws VilleIntrouvableException
     */
    @Override
    public void execAction(PionJoueur pionJoueur) throws VilleDestinationEstVilleActuelleException, NbActionsMaxTourAtteintException, VilleAvecAucuneStationDeRechercheException, VilleIntrouvableException {
        if (pionJoueur.getVilleActuelle().equals(villeDestination))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");

        // ROLE EXPERT AUX OPERATIONS, pas besoin que la ville cible soit une station de recherche
        if (!pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.EXPERT_AUX_OPERATIONS)){
            if (Boolean.FALSE.equals(pionJoueur.getPlateau().isVilleStationDeRecherche(villeDestination)))
                throw new VilleAvecAucuneStationDeRechercheException("La ville de destination: " + villeDestination.getNomVille() + " ne possède pas de station de recherche.");
        }

        if (pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.SPECIALISTE_EN_MISE_EN_QUARANTAINE))
            conditionRoleAffectantDeplacement(pionJoueur,pionJoueur.getVilleActuelle(),villeDestination);
        pionJoueur.setVilleActuelle(villeDestination);

        }


    @Override
    public void conditionRoleAffectantDeplacement(PionJoueur pionJoueur, Ville villeDepart, Ville villeArrive) throws VilleIntrouvableException {
        pionJoueur.getPlateau().specialisteQuarantainePartVille(villeDepart);
        pionJoueur.getPlateau().specialisteQuarantaineArriveVille(villeArrive);
    }
}
