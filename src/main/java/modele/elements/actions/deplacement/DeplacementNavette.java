package modele.elements.actions.deplacement;

import modele.elements.PionJoueur;

import modele.elements.Ville;
import modele.elements.actions.IAction;
import modele.elements.enums.NomsRoles;
import modele.exceptions.NbActionsMaxTourAtteintException;
import modele.exceptions.VilleAvecAucuneStationDeRechercheException;
import modele.exceptions.VilleDestinationEstVilleActuelleException;

public class DeplacementNavette implements IAction {

    private Ville villeDestination;

    public DeplacementNavette(Ville villeStationDeRecherche) {
        this.villeDestination = villeStationDeRecherche;
    }

    @Override
    public void execAction(PionJoueur pionJoueur) throws Exception {
        if (pionJoueur.getVilleActuelle().equals(villeDestination))
            throw new VilleDestinationEstVilleActuelleException("Vous ne pouvez pas vous déplacer vers votre ville actuelle.");
        if (pionJoueur.getNbActions() <= 0)
            throw new NbActionsMaxTourAtteintException("Le nombre maximum d'actions autorisés par tour est atteint.");
        if (!pionJoueur.getVilleActuelle().isStationDeRechercheVille())
            throw new VilleAvecAucuneStationDeRechercheException("La ville actuelle: " + pionJoueur.getVilleActuelle().getNomVille() + " ne possède pas de station de recherche.");

        // ROLE EXPERT AUX OPERATIONS , pas besoin que la ville cible soit une station de recherche
        if (pionJoueur.getRoleJoueur().getNomRole().equals(NomsRoles.EXPERT_AUX_OPERATIONS))
            pionJoueur.setVilleActuelle(villeDestination);
        else {
            if (!pionJoueur.getPlateau().isVilleStationDeRecherche(villeDestination))
                throw new VilleAvecAucuneStationDeRechercheException("La ville de destination: " + villeDestination.getNomVille() + " ne possède pas de station de recherche.");
            pionJoueur.setVilleActuelle(villeDestination);
        }


    }
}
